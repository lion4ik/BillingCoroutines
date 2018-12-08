package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient.BillingResponse

class FeatureNotSupportedException : BillingException(BillingResponse.FEATURE_NOT_SUPPORTED)