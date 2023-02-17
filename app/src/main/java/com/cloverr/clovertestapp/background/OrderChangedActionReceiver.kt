package com.cloverr.clovertestapp.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.clover.sdk.v1.Intents.*
import com.cloverr.clovertestapp.App
import com.cloverr.clovertestapp.bus.BroadcastBus
import javax.inject.Inject

class OrderChangedActionReceiver : BroadcastReceiver() {

    @Inject lateinit var broadcastBus: BroadcastBus

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_LINE_ITEM_ADDED) {
            val actionInfo = LineItemAddedActionInfo(
                lineItemId = intent.getStringExtra(EXTRA_CLOVER_LINE_ITEM_ID),
                itemId = intent.getStringExtra(EXTRA_CLOVER_LINE_ITEM_ID),
                orderId = intent.getStringExtra(EXTRA_CLOVER_ORDER_ID)
            )
            broadcastBus.sendInfo(actionInfo)
        }
    }
}