package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient

class DeveloperErrorException : BillingException(BillingClient.BillingResponse.DEVELOPER_ERROR)