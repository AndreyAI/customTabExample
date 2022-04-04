package com.example.customtab.food

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.customtab.data.Food
import com.hammersys.customtab.network.Api
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class FoodPagingSource(
    private val api: Api
) : PagingSource<Int, Food>() {

    override fun getRefreshKey(state: PagingState<Int, Food>): Int? {
        Timber.d("REFRESH_KEY $state")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Food> {
        val position = params.key ?: START_PAGE
        Timber.d("LOAD $position")
        return try {
            val food = api.getFood(position).search
            Timber.d(food.toString())
            val nextKey = if (food.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = food,
                prevKey = if (position == START_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val START_PAGE = 1
    }
}