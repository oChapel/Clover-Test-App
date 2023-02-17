package com.cloverr.clovertestapp.data.repository

import com.cloverr.clovertestapp.data.ModifiedItem
import kotlinx.coroutines.flow.Flow

interface ModifiedItemRepository {
    suspend fun insertModifiedItem(item: ModifiedItem)
    fun getAllItems(): Flow<List<ModifiedItem>>
}