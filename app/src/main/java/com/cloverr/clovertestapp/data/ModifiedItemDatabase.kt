package com.cloverr.clovertestapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ModifiedItem::class], version = 1)
abstract class ModifiedItemDatabase : RoomDatabase() {
    abstract fun modifiedItemDao(): ModifiedItemDao

    companion object {
        private var instance: ModifiedItemDatabase? = null
        private const val DATABASE_NAME = "modified_item_database"

        fun getInstance(context: Context): ModifiedItemDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ModifiedItemDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return instance!!
        }
    }
}