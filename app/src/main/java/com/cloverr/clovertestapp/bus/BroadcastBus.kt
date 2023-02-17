package com.cloverr.clovertestapp.bus

import com.cloverr.clovertestapp.background.LineItemAddedActionInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BroadcastBus @Inject constructor() {

    private val actionInfoMutableFlow = MutableSharedFlow<LineItemAddedActionInfo>(extraBufferCapacity = 1)
    val actionInfoFlow = actionInfoMutableFlow.asSharedFlow()

    fun sendInfo(actionInfo: LineItemAddedActionInfo) {
        actionInfoMutableFlow.tryEmit(actionInfo)
    }
}