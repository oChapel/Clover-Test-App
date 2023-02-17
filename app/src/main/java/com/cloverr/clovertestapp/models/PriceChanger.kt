package com.cloverr.clovertestapp.models

import android.accounts.Account
import android.content.Context
import com.clover.sdk.v3.inventory.*
import com.clover.sdk.v3.order.OrderConnector
import com.cloverr.clovertestapp.background.LineItemAddedActionInfo
import com.cloverr.clovertestapp.models.dto.ModifiedItem
import com.cloverr.clovertestapp.utils.dispatchers.DispatchersHolder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PriceChanger @Inject constructor(
    appContext: Context,
    cloverAccount: Account,
    private val dispatchers: DispatchersHolder
) {

    private val orderConnector = OrderConnector(appContext, cloverAccount, null)
    private val inventoryConnector = InventoryConnector(appContext, cloverAccount, null)

    private val modifiedItemMutableFlow = MutableSharedFlow<ModifiedItem>(extraBufferCapacity = 1)
    val modifiedItemFlow = modifiedItemMutableFlow.asSharedFlow()

    fun onAttach() {
        // Connect to connectors
        orderConnector.connect()
        inventoryConnector.connect()
    }

    suspend fun onLineItemAdded(
        priceCoefficient: Long,
        actionInfo: LineItemAddedActionInfo
    ) {
        withContext(dispatchers.getIO()) {
            sendModifiedItem(priceCoefficient, actionInfo)
            val modifier = getModifier(priceCoefficient)
            addLineItemModification(modifier, actionInfo)
        }
    }

    private fun sendModifiedItem(priceCoefficient: Long, actionInfo: LineItemAddedActionInfo) {
        val lineItem = orderConnector.getOrder(actionInfo.orderId).lineItems.find { it.id == actionInfo.lineItemId }
        val modifiedItem = ModifiedItem(
            date = System.currentTimeMillis(),
            oldPrice = lineItem?.price ?: 0,
            newPrice = lineItem?.price?.times(priceCoefficient)?.div(100) ?: 0,
            orderId = actionInfo.orderId.toString(),
            itemId = actionInfo.itemId.toString()
        )
        modifiedItemMutableFlow.tryEmit(modifiedItem)
    }

    private fun getModifier(priceCoefficient: Long): Modifier {
        val modifierGroup = inventoryConnector.createModifierGroup(ModifierGroup())
        return inventoryConnector.createModifier(
            modifierGroup.id,
            Modifier().apply { price = price.times(priceCoefficient).div(100) }
        )
    }

    private fun addLineItemModification(modifier: Modifier, actionInfo: LineItemAddedActionInfo) {
        val merchantItem = inventoryConnector.getItem(actionInfo.itemId)
        with(merchantItem.priceType) {
            if (this != PriceType.FIXED && this != PriceType.PER_UNIT) {
                orderConnector.addLineItemModification(
                    actionInfo.orderId,
                    actionInfo.lineItemId,
                    modifier
                )
            }
        }
    }

    fun onCleared() {
        // Disconnect from connectors
        inventoryConnector.disconnect()
        orderConnector.disconnect()
    }
}