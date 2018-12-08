package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient.BillingResponse

open class BillingException(@BillingResponse val errorCode: Int): RuntimeException()