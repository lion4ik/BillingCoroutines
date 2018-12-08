package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient.BillingResponse

class ServiceUnavailableException : BillingException(BillingResponse.SERVICE_UNAVAILABLE)