package com.github.lion4ik.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams
import com.github.lion4ik.billing.error.BillingException
import com.github.lion4ik.billing.error.BillingExceptionFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BillingManager(private val appContext: Context) : PurchasesUpdatedListener, CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val billingClient: BillingClient by lazy {
        BillingClient.newBuilder(appContext).setListener(this).build()
    }
    @ExperimentalCoroutinesApi
    private val channel: ConflatedBroadcastChannel<List<Purchase>> = ConflatedBroadcastChannel()

    @ExperimentalCoroutinesApi
    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        launch {
            purchases?.run { channel.send(toList()) }
        }
    }

    @ExperimentalCoroutinesApi
    fun destroy() {
        if (billingClient.isReady) {
            billingClient.endConnection()
        }
        channel.close()
        job.cancel()
    }

    private suspend fun connectService() = suspendCoroutine<Int> { continuation ->
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                continuation.resumeWithException(BillingException(BillingClient.BillingResponse.SERVICE_DISCONNECTED))
            }

            override fun onBillingSetupFinished(responseCode: Int) {
                continuation.resume(responseCode)
            }
        })
    }

    private suspend fun connectServiceIfNeed() {
        if (!billingClient.isReady) {
            connectService()
        }
    }

    @ExperimentalCoroutinesApi
    fun getPurchasesUpdatesChannel(): ReceiveChannel<List<Purchase>> = channel.openSubscription()

    suspend fun queryPurchasesHistory(@BillingClient.SkuType skuType: String): List<Purchase> {
        connectServiceIfNeed()
        return suspendCoroutine { continuation ->
            billingClient.queryPurchaseHistoryAsync(skuType) { responseCode, purchasesList ->
                if (responseCode == BillingClient.BillingResponse.OK) {
                    continuation.resume(purchasesList)
                } else {
                    continuation.resumeWithException(BillingExceptionFactory.createException(responseCode))
                }
            }
        }
    }

    suspend fun queryPurchases(@BillingClient.SkuType skuType: String): List<Purchase> {
        connectServiceIfNeed()
        val purchaseResult = billingClient.queryPurchases(skuType)
        if (purchaseResult.responseCode != BillingClient.BillingResponse.OK) {
            throw BillingExceptionFactory.createException(purchaseResult.responseCode)
        }
        return purchaseResult.purchasesList
    }

    suspend fun querySkuDetailsAsync(@BillingClient.SkuType itemType: String,
                                     skuList: List<String>): List<SkuDetails> {
        connectServiceIfNeed()
        val params = SkuDetailsParams
                .newBuilder()
                .setType(itemType)
                .setSkusList(skuList)
                .build()

        return suspendCoroutine { continuation ->
            billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
                if (responseCode == BillingClient.BillingResponse.OK) {
                    continuation.resume(skuDetailsList)
                } else {
                    continuation.resumeWithException(BillingExceptionFactory.createException(responseCode))
                }
            }
        }
    }

    suspend fun launchPurchaseFlow(activity: Activity, skuId: String, oldSkus: ArrayList<String>?,
                                   @BillingClient.SkuType billingType: String) {
        connectServiceIfNeed()
        val purchaseParams = BillingFlowParams.newBuilder()
                .setSku(skuId)
                .setType(billingType)
                .setOldSkus(oldSkus)
                .build()
        val responseCode = billingClient.launchBillingFlow(activity, purchaseParams)
        if (responseCode != BillingClient.BillingResponse.OK) {
            throw BillingExceptionFactory.createException(responseCode)
        }
    }

    suspend fun launchPurchaseFlow(activity: Activity, skuDetails: SkuDetails, oldSku: String?) {
        connectServiceIfNeed()
        val purchaseParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .setOldSku(oldSku)
                .build()
        val responseCode = billingClient.launchBillingFlow(activity, purchaseParams)
        if (responseCode != BillingClient.BillingResponse.OK) {
            throw BillingExceptionFactory.createException(responseCode)
        }
    }
}