package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class BillingUnavailableException : BillingException(BillingClient.BillingResponse.BILLING_UNAVAILABLE)