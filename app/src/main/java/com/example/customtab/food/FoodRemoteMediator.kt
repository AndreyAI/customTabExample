package com.example.customtab.food

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.customtab.data.Food
import com.example.customtab.data.RemoteKey
import com.example.customtab.data.db.FoodDatabase
import com.example.customtab.network.Api
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class FoodRemoteMediator(
    private val database: FoodDatabase,
    private val api: Api
) : RemoteMediator<Int, Food>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Food>): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    Timber.d("REFRESH")
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: START_PAGE
                }
                LoadType.PREPEND -> {
                    Timber.d("PREPEND")
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    Timber.d("APPEND")
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val foods = api.getFood(page).search
            val endOfPaginationReached = foods.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().clearRemoteKeys()
                    database.foodDao().clearFoods()
                }
                val prevKey = if (page == START_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = foods.map {
                    RemoteKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeyDao().insertAll(keys)
                database.foodDao().insertAll(foods)
            }
            Timber.d("AFTER")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Food>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { food ->
                database.remoteKeyDao().remoteKeyById(food.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Food>): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { food ->
                database.remoteKeyDao().remoteKeyById(food.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Food>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeyDao().remoteKeyById(id)
            }
        }
    }

    companion object {
        private const val START_PAGE = 1
    }
}