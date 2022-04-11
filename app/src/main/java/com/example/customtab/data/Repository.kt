package com.example.customtab.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.customtab.data.db.Database
import com.example.customtab.food.FoodRemoteMediator
import com.example.customtab.network.Networking
import kotlinx.coroutines.flow.Flow

class Repository {

    //Without cache in db, just network api
    /*fun getFood(): Flow<PagingData<Food>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { FoodPagingSource(Networking.api) }
        ).flow
    }*/

    //Used remote mediator. Results cached in db
    @OptIn(ExperimentalPagingApi::class)
    fun getFood(): Flow<PagingData<Food>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = FoodRemoteMediator(Database.instance, Networking.api)
        ) {
            Database.instance.foodDao().pagingSource()
        }.flow
    }

    fun getDetailInfo(id: String): FoodDetail =
        Networking.api.getDetailInfo(id).execute().body()!!

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}