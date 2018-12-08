package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class ItemUnavailableException : BillingException(BillingClient.BillingResponse.ITEM_UNAVAILABLE)