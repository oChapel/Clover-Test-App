package com.cloverr.clovertestapp.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cloverr.clovertestapp.models.dto.ModifiedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ModifiedItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ModifiedItem)

    @Query("SELECT * FROM modified_item_table ORDER BY date DESC")
    fun getAllItems(): Flow<List<ModifiedItem>>
}