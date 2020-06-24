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

-- 03 Setup and Teardown

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/DessertTimer.kt

```kt
/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.dessertpusher

import android.os.Handler
import timber.log.Timber

/**
 * This is a class representing a timer that you can start or stop. The secondsCount outputs a count of
 * how many seconds since it started, every one second.
 *
 * -----
 *
 * Handler and Runnable are beyond the scope of this lesson. This is in part because they deal with
 * threading, which is a complex topic that will be covered in a later lesson.
 *
 * If you want to learn more now, you can take a look on the Android Developer documentation on
 * threading:
 *
 * https://developer.android.com/guide/components/processes-and-threads
 *
 */
class DessertTimer {

    // The number of seconds counted since the timer started
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     */
    private var handler = Handler()
    private lateinit var runnable: Runnable


    fun startTimer() {
        // Create the runnable action, which prints out a log and increments the seconds counter
        runnable = Runnable {
            secondsCount++
            Timber.i("Timer is at : $secondsCount")
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}
```

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/MainActivity.kt

```kt
35+    private lateinit var dessertTimer: DessertTimer
77-79+

        // Setup dessertTimer
        dessertTimer = DessertTimer()
161+        dessertTimer.startTimer()
177+        dessertTimer.stopTimer()
```

-- 04 Lifecycle Observation

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/DessertTimer.kt

```kt
37-class DessertTimer {
37+class DessertTimer(lifecycle: Lifecycle) : LifecycleObserver {
20-21+
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
51-55+
    init {
        // Add this as a lifecycle Observer, which allows for the class to react to changes in this
        // activity's lifecycle state
        lifecycle.addObserver(this)
    }
57+    @OnLifecycleEvent(Lifecycle.Event.ON_START)
22+import androidx.lifecycle.OnLifecycleEvent
77+    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
```

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/MainActivity.kt

```kt
78-79-
        // Setup dessertTimer
        dessertTimer = DessertTimer()
78-79+
        // Setup dessertTimer, passing in the lifecycle
        dessertTimer = DessertTimer(this.lifecycle)
161-        dessertTimer.startTimer()
176-        dessertTimer.stopTimer()
```

-- 05 onSaveInstanceState

> - DessertPusher/app/src/main/java/com/example/android/dessertpusher/MainActivity.kt

```kt
155-166+

    /**
     * Called when the user navigates away from the app but might come back
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsSold)
        outState.putInt(KEY_TIMER_SECONDS, dessertTimer.secondsCount)
        Timber.i("onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }

31-35+
/** onSaveInstanceState Bundle Keys **/
const val KEY_REVENUE = "revenue_key"
const val KEY_DESSERT_SOLD = "dessert_sold_key"
const val KEY_TIMER_SECONDS = "timer_seconds_key"

86-96+
        // If there is a savedInstanceState bundle, then you're "restarting" the activity
        // If there isn't a bundle, then it's a "fresh" start
        if (savedInstanceState != null) {
            // Get all the game state information from the bundle, set it
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertsSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
            showCurrentDessert()

        }
        
```