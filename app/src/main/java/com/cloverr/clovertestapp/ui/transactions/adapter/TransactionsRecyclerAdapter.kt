package com.cloverr.clovertestapp.ui.transactions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.cloverr.clovertestapp.data.ModifiedItem
import com.cloverr.clovertestapp.databinding.ItemModifiedItemBinding

class TransactionsRecyclerAdapter :
    ListAdapter<ModifiedItem, ModifiedItemViewHolder>(ModifiedItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifiedItemViewHolder =
        ModifiedItemViewHolder(
            ItemModifiedItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ModifiedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}