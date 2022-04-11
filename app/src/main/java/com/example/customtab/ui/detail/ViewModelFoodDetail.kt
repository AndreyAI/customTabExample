package com.example.customtab.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.customtab.data.FoodDetail
import com.example.customtab.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelFoodDetail : ViewModel() {

    private val repository = Repository()

    private val stateLiveData = MutableLiveData<ScreenState>(ScreenState.LoadingState)
    val state: LiveData<ScreenState>
        get() = stateLiveData

    private val infoFoodLiveData = MutableLiveData<FoodDetail>()
    val infoFood: LiveData<FoodDetail>
        get() = infoFoodLiveData

    fun bind(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val detailInfo = repository.getDetailInfo(id)
                infoFoodLiveData.postValue(detailInfo)
                stateLiveData.postValue(ScreenState.DefaultState)
            } catch (t: Throwable) {
                Timber.e(t)
                stateLiveData.postValue(ScreenState.ErrorState)
            }
        }
    }
}