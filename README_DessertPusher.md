# Dessert Pusher

-- 00 open starter code

-- 00 Introduction to Logging

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