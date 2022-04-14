package com.example.customtab.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.customtab.data.FoodDetail
import com.example.customtab.data.Repository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ViewModelFoodDetailTest {

    private val mockRepository = mock(Repository::class.java)

    @AfterEach
    fun afterEach() {
        reset(mockRepository)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @BeforeEach
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    @Test
    fun `when bind called it should get FoodDetailInfo`() {
        val resultRepository: FoodDetail = mock()
        val testParams = "id"

        `when`(mockRepository.getDetailInfo(testParams)).thenReturn(resultRepository)

        val foodDetailViewModel = ViewModelFoodDetail(mockRepository)
        foodDetailViewModel.bind(testParams)

        val actual = foodDetailViewModel.infoFood.getOrAwaitValue { }
        val expected = resultRepository

        verify(mockRepository, times(1)).getDetailInfo(testParams)
        Assertions.assertEquals(expected, actual)
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}