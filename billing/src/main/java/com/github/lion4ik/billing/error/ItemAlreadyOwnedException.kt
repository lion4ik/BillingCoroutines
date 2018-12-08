package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class ItemAlreadyOwnedException : BillingException(BillingClient.BillingResponse.ITEM_ALREADY_OWNED)