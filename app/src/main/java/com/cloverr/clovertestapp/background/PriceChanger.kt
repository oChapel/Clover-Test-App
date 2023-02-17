package com.cloverr.clovertestapp.background

import android.accounts.Account
import android.content.Context
import com.clover.sdk.v3.inventory.*
import com.clover.sdk.v3.order.OrderConnector
import com.cloverr.clovertestapp.utils.dispatchers.DispatchersHolder
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PriceChanger @Inject constructor(
    appContext: Context,
    cloverAccount: Account,
    private val dispatchers: DispatchersHolder
) {

    private val orderConnector = OrderConnector(appContext, cloverAccount, null)
    private val inventoryConnector = InventoryConnector(appContext, cloverAccount, null)

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
            val modifierGroup = inventoryConnector.createModifierGroup(ModifierGroup())
            val modifier = inventoryConnector.createModifier(
                modifierGroup.id,
                Modifier().apply { price *= priceCoefficient }
            )

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
    }

    fun onCleared() {
        // Disconnect from connectors
        inventoryConnector.disconnect()
        orderConnector.disconnect()
    }
}