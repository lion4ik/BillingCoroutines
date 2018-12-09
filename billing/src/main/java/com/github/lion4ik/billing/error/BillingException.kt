package com.github.lion4ik.billing.error

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponse

sealed class BillingException(@BillingResponse val errorCode: Int) : RuntimeException()

class BillingServiceDisconnectedException : BillingException(BillingResponse.SERVICE_DISCONNECTED)
class DeveloperErrorException : BillingException(BillingClient.BillingResponse.DEVELOPER_ERROR)
class BillingUnavailableException : BillingException(BillingClient.BillingResponse.BILLING_UNAVAILABLE)
class FatalErrorException : BillingException(BillingClient.BillingResponse.ERROR)
class FeatureNotSupportedException : BillingException(BillingResponse.FEATURE_NOT_SUPPORTED)
class ItemAlreadyOwnedException : BillingException(BillingClient.BillingResponse.ITEM_ALREADY_OWNED)
class ItemNotOwnedException : BillingException(BillingClient.BillingResponse.ITEM_NOT_OWNED)
class ItemUnavailableException : BillingException(BillingClient.BillingResponse.ITEM_UNAVAILABLE)
class ServiceUnavailableException : BillingException(BillingResponse.SERVICE_UNAVAILABLE)
class UserCanceledException : BillingException(BillingClient.BillingResponse.USER_CANCELED)
class BillingOk: BillingException(BillingResponse.OK)