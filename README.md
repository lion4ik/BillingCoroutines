# BillingCoroutines
Wrapper on top of Google Billing library based on coroutines [ ![Download](https://api.bintray.com/packages/lion4ik/maven/BillingCoroutines/images/download.svg) ](https://bintray.com/lion4ik/maven/BillingCoroutines/_latestVersion)

## Usage

#### Add to project

Add specific maven repository to repositories closure. For example, you should add it to root
of `build.gradle`:

```groovy
allprojects {
  repositories {
    maven { url "https://dl.bintray.com/lion4ik/maven" }
  }
}
```

Add dependency:

```groovy
dependencies {
   implementation "com.github.lion4ik:BillingCoroutines:$version"
}
```

where recommended `$version` is the latest from Download badge [ ![Download](https://api.bintray.com/packages/lion4ik/maven/BillingCoroutines/images/download.svg) ](https://bintray.com/lion4ik/maven/BillingCoroutines/_latestVersion)

#### How to use
You can call any suspend function from BillingManager from coroutines builder. For example:

```kotlin
class SampleFragment: Fragment, CoroutineScope {
   private billingManager: BillingManager = BillingManager(activity.application)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        launch {
            val purchases = billingManager.queryPurchases(BillingClient.SkuType.INAPP)
            // Process purchases
            
            // Also you can subscrube on purchases updates channel
            billingManager.getPurchasesUpdatesChannel().consumeEach { purchases -> 
              // react on changes
            }
        }
    }
}
```

For instantiation BillingManager I would recommend to use any DI frameworks like Dagger2, Toothpick or something else.
