package com.example.customtab.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.customtab.data.Food
import com.example.customtab.data.Repository
import kotlinx.coroutines.flow.Flow

class ViewModelMain : ViewModel() {

    private val repository = Repository()
    val pagingDataFlow: Flow<PagingData<Food>> = repository.getFood()
        .cachedIn(viewModelScope)
}