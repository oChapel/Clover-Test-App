package com.cloverr.clovertestapp.models.repository

import com.cloverr.clovertestapp.models.dto.ModifiedItem
import kotlinx.coroutines.flow.Flow

interface ModifiedItemRepository {
    suspend fun insertModifiedItem(item: ModifiedItem)
    fun getAllItems(): Flow<List<ModifiedItem>>
}