package com.cloverr.clovertestapp.ui.transactions.adapter

import androidx.recyclerview.widget.DiffUtil
import com.cloverr.clovertestapp.data.ModifiedItem

object ModifiedItemDiffCallback : DiffUtil.ItemCallback<ModifiedItem>() {

    override fun areItemsTheSame(oldItem: ModifiedItem, newItem: ModifiedItem): Boolean =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: ModifiedItem, newItem: ModifiedItem): Boolean =
        oldItem == newItem
}