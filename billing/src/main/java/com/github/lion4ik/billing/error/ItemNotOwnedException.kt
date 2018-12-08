package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class ItemNotOwnedException : BillingException(BillingClient.BillingResponse.ITEM_NOT_OWNED)