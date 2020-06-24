# Guess It

-- 00 Tour of the App

-- 01 Create the GameViewModel

> - GuessIt/app/build.gradle

```
62-64+

    // Lifecycles
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

/**
 * ViewModel containing all the logic needed to run the game
 */
class GameViewModel : ViewModel() {

    init {
        Log.i("GameViewModel", "GameViewModel vreated!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }
}
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
34-35+
    private lateinit var viewModel: GameViewModel

58-60+
        // Get the viewmodel
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

25+import androidx.lifecycle.ViewModelProviders
60+        Log.i("GameFragment", "Called ViewModelProviders.of")
20+import android.util.Log
```