package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class FatalErrorException : BillingException(BillingClient.BillingResponse.ERROR)