package com.github.lion4ik.billing.error

import android.annotation.SuppressLint
import com.android.billingclient.api.BillingClient.BillingResponse

object BillingExceptionFactory {

    @SuppressLint("SwitchIntDef")
    fun createException(@BillingResponse errorCode: Int) = when (errorCode) {
        BillingResponse.BILLING_UNAVAILABLE -> BillingUnavailableException()
        BillingResponse.DEVELOPER_ERROR -> DeveloperErrorException()
        BillingResponse.FEATURE_NOT_SUPPORTED -> FeatureNotSupportedException()
        BillingResponse.ERROR -> FatalErrorException()
        BillingResponse.ITEM_ALREADY_OWNED -> ItemAlreadyOwnedException()
        BillingResponse.ITEM_NOT_OWNED -> ItemNotOwnedException()
        BillingResponse.ITEM_UNAVAILABLE -> ItemUnavailableException()
        BillingResponse.USER_CANCELED -> UserCanceledException()
        BillingResponse.SERVICE_DISCONNECTED -> BillingServiceDisconnectedException()
        BillingResponse.SERVICE_UNAVAILABLE -> ServiceUnavailableException()
        else -> BillingOk()
    }
}