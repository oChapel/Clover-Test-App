package com.cloverr.clovertestapp.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cloverr.clovertestapp.models.dto.ModifiedItem
import com.cloverr.clovertestapp.models.repository.ModifiedItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsHistoryViewModel(private val repository: ModifiedItemRepository): ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val repository: ModifiedItemRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TransactionsHistoryViewModel(repository) as T
        }
    }

    fun getModifiedItems(): Flow<List<ModifiedItem>> = repository.getAllItems()
}