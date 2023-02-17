package com.cloverr.clovertestapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cloverr.clovertestapp.R
import com.cloverr.clovertestapp.models.PriceChanger
import com.cloverr.clovertestapp.bus.BroadcastBus
import com.cloverr.clovertestapp.models.repository.ModifiedItemRepository
import com.cloverr.clovertestapp.ui.percentage.PercentageSettingScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel (
    private val broadcastBus: BroadcastBus,
    private val priceChanger: PriceChanger,
    private val repository: ModifiedItemRepository
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val broadcastBus: BroadcastBus,
        private val priceChanger: PriceChanger,
        private val repository: ModifiedItemRepository
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(broadcastBus, priceChanger, repository) as T
        }
    }

    init {
        priceChanger.onAttach()

        viewModelScope.launch {
            broadcastBus.actionInfoFlow.collect { info ->
                priceChanger.onLineItemAdded(priceCoefficient, info)
            }
        }

        viewModelScope.launch {
            priceChanger.modifiedItemFlow.collect { item ->
                repository.insertModifiedItem(item)
            }
        }
    }

    private var priceCoefficient = 1L

    private val percentageSettingScreenStateMutableFlow = MutableStateFlow(
        PercentageSettingScreenState(
            isEditingEnabled = true,
            inputLayoutError = null
        )
    )
    val mainScreenStateFlow = percentageSettingScreenStateMutableFlow.asStateFlow()

    fun setPercentage(input: String) {
        if (input.isEmpty()) {
            percentageSettingScreenStateMutableFlow.tryEmit(
                PercentageSettingScreenState(
                    isEditingEnabled = true,
                    inputLayoutError = R.string.empty_input
                )
            )
        }

        try {
            val percents = input.toInt()
            if (percents < 5 || percents > 25) {
                percentageSettingScreenStateMutableFlow.tryEmit(
                    PercentageSettingScreenState(
                        isEditingEnabled = true,
                        inputLayoutError = R.string.ineligible_input
                    )
                )
            } else {
                setPriceCoefficient(percents)
                percentageSettingScreenStateMutableFlow.tryEmit(
                    PercentageSettingScreenState(
                        isEditingEnabled = false,
                        inputLayoutError = null
                    )
                )
            }
        } catch (e: java.lang.NumberFormatException) {
            percentageSettingScreenStateMutableFlow.tryEmit(
                PercentageSettingScreenState(
                    isEditingEnabled = true,
                    inputLayoutError = R.string.ineligible_input
                )
            )
        }
    }

    private fun setPriceCoefficient(percents: Int) {
        priceCoefficient = percents.toLong()
    }

    override fun onCleared() {
        super.onCleared()
        priceChanger.onCleared()
    }
}