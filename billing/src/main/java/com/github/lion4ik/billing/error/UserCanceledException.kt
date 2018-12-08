package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class UserCanceledException : BillingException(BillingClient.BillingResponse.USER_CANCELED)