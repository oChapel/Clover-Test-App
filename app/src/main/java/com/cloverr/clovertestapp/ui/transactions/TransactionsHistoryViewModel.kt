package com.cloverr.clovertestapp.ui.transactions

import androidx.lifecycle.ViewModel
import com.cloverr.clovertestapp.data.ModifiedItem
import com.cloverr.clovertestapp.data.repository.ModifiedItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsHistoryViewModel @Inject constructor(
    private val repository: ModifiedItemRepository
) : ViewModel() {

    fun getModifiedItems(): Flow<List<ModifiedItem>> = repository.getAllItems()
}