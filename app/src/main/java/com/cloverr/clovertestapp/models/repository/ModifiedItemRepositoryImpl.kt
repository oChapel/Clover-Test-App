package com.cloverr.clovertestapp.models.repository

import android.content.Context
import com.cloverr.clovertestapp.models.dto.ModifiedItem
import com.cloverr.clovertestapp.models.dao.ModifiedItemDao
import com.cloverr.clovertestapp.models.dao.ModifiedItemDatabase
import com.cloverr.clovertestapp.utils.dispatchers.DispatchersHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModifiedItemRepositoryImpl @Inject constructor(
    appContext: Context,
    private val dispatchers: DispatchersHolder
) : ModifiedItemRepository {

    private val modifiedItemDao: ModifiedItemDao

    init {
        val database = ModifiedItemDatabase.getInstance(appContext)
        modifiedItemDao = database.modifiedItemDao()
    }

    override suspend fun insertModifiedItem(item: ModifiedItem) {
        withContext(dispatchers.getIO()) {
            modifiedItemDao.insert(item)
        }
    }

    override fun getAllItems(): Flow<List<ModifiedItem>> = modifiedItemDao.getAllItems()
}