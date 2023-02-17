package com.cloverr.clovertestapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ModifiedItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: ModifiedItem)

    @Query("SELECT * FROM modified_item_table ORDER BY date DESC")
    fun getAllItems(): Flow<List<ModifiedItem>>
}