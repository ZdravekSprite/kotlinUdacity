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

-- 02 Populate the GameViewModel

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
38-46-
    // The current word
    private var word = ""

    // The current score
    private var score = 0

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

66-95-
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

74-99-
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            gameFinished()
        } else {
            word = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        score--
        nextWord()
    }

    fun onCorrect() {
        score++
        nextWord()
    }

55-57-
        resetList()
        nextWord()


52-        Log.i("GameFragment", "Called ViewModelProviders.of")
20-import android.util.Log
53-        binding.correctButton.setOnClickListener { onCorrect() }
53-57+
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
            updateScoreText()
            updateWordText()
        }
58-        binding.skipButton.setOnClickListener { onSkip() }
58-62+
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
            updateScoreText()
            updateWordText()
        }
72-73-
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(score)
72-73+
    fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score)
80-81-
        binding.wordText.text = word

80+        binding.wordText.text = viewModel.word
84-        binding.scoreText.text = score.toString()
84+        binding.scoreText.text = viewModel.score.toString()
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
11-19+
    // The current word
    var word = ""

    // The current score
    var score = 0

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

24-53+
    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

54-77+
    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            // gameFinished()
        } else {
            word = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    private fun onSkip() {
        score--
        nextWord()
    }

    private fun onCorrect() {
        score++
        nextWord()
    }

78-81-
    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

21-        Log.i("GameViewModel", "GameViewModel vreated!")
21-22+
        resetList()
        nextWord()
3-import android.util.Log
```

-- 03 Add LiveData to GameViewModel

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
11-    var word = ""
11+    val word = MutableLiveData<String>()
3+import androidx.lifecycle.MutableLiveData
63-            word = wordList.removeAt(0)
63+            word.value = wordList.removeAt(0)
15-    var score = 0
15+    val score = MutableLiveData<Int>()
70-        score--
70+        score.value = (score.value)?.minus(1)
75-        score++
75+        score.value = (score.value)?.plus(1)
23+        score.value = 0
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
65-73+
        /** Setting up LiveData observation relationship **/
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

25+import androidx.lifecycle.Observer
86-95-

    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }
56-57-
61-62-
            updateScoreText()
            updateWordText()
60-61-
        updateScoreText()
        updateWordText()
77-        val action = GameFragmentDirections.actionGameToScore(viewModel.score)
77-78+
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
```

-- 04 Add LiveData Encapsulation to GameViewModel

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
12-    val word = MutableLiveData<String>()
12-14+
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
3+import androidx.lifecycle.LiveData
67-            word.value = wordList.removeAt(0)
67+            _word.value = wordList.removeAt(0)
18-    val score = MutableLiveData<Int>()
18-20+
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
28-        score.value = 0
28+        _score.value = 0
76-        score.value = (score.value)?.minus(1)
76+        _score.value = (_score.value)?.minus(1)
81-        score.value = (score.value)?.plus(1)
81+        _score.value = (_score.value)?.plus(1)
```

-- 05 Add End Game Event

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
25-29+
    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

90-95+
    /** Methods for completed events **/

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

72-            // gameFinished()
72+            _eventGameFinish.value = true
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
69-78+
        // Sets up event listening to navigate the player when the game is finished
        viewModel.eventGameFinish.observe(this, Observer { isFinished ->
            if (isFinished) {
                val currentScore = viewModel.score.value ?: 0
                val action = GameFragmentDirections.actionGameToScore(currentScore)
                findNavController(this).navigate(action)
                viewModel.onGameFinishComplete()
            }
        })

83-90-
    /**
     * Called when the game is finished
     */
    fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController(this).navigate(action)
    }
```