package com.example.customtab.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.customtab.data.Food
import com.example.customtab.data.Repository
import kotlinx.coroutines.flow.Flow

class ViewModelMain(private val repository: Repository) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Food>> = repository.getFood()
        .cachedIn(viewModelScope)

    class MainFactory(private val repository: Repository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ViewModelMain(repository) as T
        }
    }

}
