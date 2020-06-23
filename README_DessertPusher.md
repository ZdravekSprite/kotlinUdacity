# Dessert Pusher

-- 00 open starter code

-- 01 Introduction to Logging

> - app/src/main/java/com/example/android/dessertpusher/MainActivity.kt

```ts
21+import android.util.Log
68+        Log.i("MainActivity", "onCreate Called")
151-157+

    /** Lifecycle Methods **/

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart Called")
    }
```

-- 02 The Application Class and Timber

> - DessertPusher/app/build.gradle

```ts
48+    implementation 'com.jakewharton.timber:timber:4.7.1'
```

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/PusherApplication.kt

```kt
package com.example.android.dessertpusher

import android.app.Application
import timber.log.Timber

class PusherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
```

> - DessertPusher/app/src/main/AndroidManifest.xml

```xml
21+        android:name=".PusherApplication"
```

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/MainActivity.kt

```kt
68-        Log.i("MainActivity", "onCreate Called")
68+        Timber.i("onCreate Called")
156-        Log.i("MainActivity", "onStart Called")
156+        Timber.i("onStart Called")
21-import android.util.Log
29+import timber.log.Timber
158-182+

    override fun onResume() {
        super.onResume()
        Timber.i("onResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart Called")
    }
```