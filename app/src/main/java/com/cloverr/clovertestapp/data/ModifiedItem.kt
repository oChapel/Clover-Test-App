package com.cloverr.clovertestapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "modified_item_table")
data class ModifiedItem(
    @PrimaryKey
    val date: Long,
    val oldPrice: Long,
    val newPrice: Long,
    val orderId: String,
    val itemId: String
)