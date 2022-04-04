package com.example.customtab.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.customtab.food.FoodPagingSource
import com.hammersys.customtab.network.Networking
import kotlinx.coroutines.flow.Flow

class Repository {

    fun getFood(): Flow<PagingData<Food>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { FoodPagingSource(Networking.api) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

}