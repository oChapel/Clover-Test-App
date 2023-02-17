package com.cloverr.clovertestapp.ui.transactions.adapter

import androidx.recyclerview.widget.RecyclerView
import com.cloverr.clovertestapp.R
import com.cloverr.clovertestapp.models.dto.ModifiedItem
import com.cloverr.clovertestapp.databinding.ItemModifiedItemBinding
import com.cloverr.clovertestapp.utils.StringUtils

class ModifiedItemViewHolder(private val binding: ItemModifiedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ModifiedItem) {
        with(binding.root.context) {
            binding.itemDate.text = getString(R.string.item_date, StringUtils.getDateStringFromLong(item.date))
            binding.itemOldPrice.text = getString(R.string.item_old_price, item.oldPrice)
            binding.itemNewPrice.text = getString(R.string.item_new_price, item.newPrice)
            binding.itemOrderId.text = getString(R.string.item_order_id, item.orderId)
            binding.itemItemId.text = getString(R.string.item_item_id, item.itemId)
        }
    }
}