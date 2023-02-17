package com.cloverr.clovertestapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloverr.clovertestapp.R
import com.cloverr.clovertestapp.background.PriceChanger
import com.cloverr.clovertestapp.bus.BroadcastBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val broadcastBus: BroadcastBus,
    private val priceChanger: PriceChanger
) : ViewModel() {

    init {
        viewModelScope.launch {
            priceChanger.onAttach()
            broadcastBus.actionInfoFlow.collect { info ->
                priceChanger.onLineItemAdded(priceCoefficient, info)
            }
        }
    }

    private var priceCoefficient = 1L

    private val mainScreenStateMutableFlow = MutableStateFlow(
        MainScreenState(
            isEditingEnabled = true,
            inputLayoutError = null
        )
    )
    val mainScreenStateFlow = mainScreenStateMutableFlow.asStateFlow()

    fun setPercentage(input: String) {
        if (input.isEmpty()) {
            mainScreenStateMutableFlow.tryEmit(
                MainScreenState(
                    isEditingEnabled = true,
                    inputLayoutError = R.string.empty_input
                )
            )
        }

        try {
            val percents = input.toInt()
            if (percents < 5 || percents > 25) {
                mainScreenStateMutableFlow.tryEmit(
                    MainScreenState(
                        isEditingEnabled = true,
                        inputLayoutError = R.string.ineligible_input
                    )
                )
            } else {
                setPriceCoefficient(percents)
                mainScreenStateMutableFlow.tryEmit(
                    MainScreenState(
                        isEditingEnabled = false,
                        inputLayoutError = null
                    )
                )
            }
        } catch (e: java.lang.NumberFormatException) {
            mainScreenStateMutableFlow.tryEmit(
                MainScreenState(
                    isEditingEnabled = true,
                    inputLayoutError = R.string.ineligible_input
                )
            )
        }
    }

    private fun setPriceCoefficient(percents: Int) {
        priceCoefficient = 1L + percents.toLong().div(100)
    }

    override fun onCleared() {
        super.onCleared()
        priceChanger.onCleared()
    }
}