package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class ServiceDisconnectedException : BillingException(BillingClient.BillingResponse.SERVICE_DISCONNECTED)