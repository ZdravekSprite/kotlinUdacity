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
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { isFinished ->
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

-- 06 Add CountDownTimer

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
12-18+
    companion object {
        // These represent different important times in the game, such as game length.

    }

    private val timer: CountDownTimer

3+import android.os.CountDownTimer
20-23+
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

14-18+
        // This is when the game is over
        private const val DONE = 0L
        
19-24+
        // This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        // This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L

56-69+
        // Creates a timer which triggers the end of the game when it finishes
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()
108-110-
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
108+            resetList()
110+        _word.value = wordList.removeAt(0)
131-134+
    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
69-73+
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        })

20+import android.text.format.DateUtils
```

-- 07 Add a ViewModelFactory

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreViewModel.kt

```kt
package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel

/**
 * ViewModel for the final screen showing the score
 */
class ScoreViewModel(finalScore: Int) : ViewModel() {

    init {
    }
}
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreViewModelFactory.kt

```kt
package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreFragment.kt

```kt
35-37+
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

54-58+

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ScoreViewModel::class.java)

25+import androidx.lifecycle.ViewModelProviders
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreViewModel.kt

```kt
10-17+
    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

3-4+
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
21+        _score.value = finalScore
23-30+

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreFragment.kt

```kt
60-64+
        // Add observer for score
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

25+import androidx.lifecycle.Observer
66-67-
        binding.scoreText.text = scoreFragmentArgs.score.toString()
        binding.playAgainButton.setOnClickListener { onPlayAgain() }
66+        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }
68-75+
        // Navigates back to title when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                viewModel.onPlayAgainComplete()
            }
        })

```

-- 08 Add ViewModel to Data Binding

> - GuessIt/app/src/main/res/layout/game_fragment.xml

```xml
21-26+
    <data>
        <variable
            name="gameViewModel"
            type="com.example.android.guesstheword.screens.game.GameViewModel" />
    </data>

104+            android:onClick="@{() -> gameViewModel.onSkip()}"
126+            android:onClick="@{() -> gameViewModel.onCorrect()}"
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
55-60-
        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }
55-58+
        // Set the viewmodel for databinding - this allows the bound layout access to all of the
        // data in the VieWModel
        binding.gameViewModel = viewModel

```

> - GuessIt/app/src/main/res/layout/score_fragment.xml

```xml
21-26+
    <data>
        <variable
            name="scoreViewModel"
            type="com.example.android.guesstheword.screens.score.ScoreViewModel" />
    </data>

73+            android:onClick="@{() -> scoreViewModel.onPlayAgain()}"
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreFragment.kt

```kt
61-62+
        binding.scoreViewModel = viewModel

68-69-
        binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

```

-- 09 Add LiveData Data Binding

> - GuessIt/app/src/main/res/layout/game_fragment.xml

```xml
57+            android:text="@{@string/quote_format(gameViewModel.word)}"
92+            android:text="@{@string/score_format(gameViewModel.score)}"
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
59-62+
        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.setLifecycleOwner(this)

64-71-
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

```

> - GuessIt/app/src/main/res/layout/score_fragment.xml

```xml
57+            android:text="@{String.valueOf(scoreViewModel.score)}"
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/score/ScoreFragment.kt

```kt
63-65+
        // Specify the current activity as the lifecycle owner of the binding. This is used so that
        // the binding can observe LiveData updates
        binding.setLifecycleOwner(this)
66-69-
        // Add observer for score
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
```

-- 10 LiveData Map Transformation

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
33-37+
    // The String version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

6+import androidx.lifecycle.Transformations
4+import android.text.format.DateUtils
```

> - GuessIt/app/src/main/res/layout/game_fragment.xml

```xml
77+            android:text="@{gameViewModel.currentTimeString}"
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
63-68-
        /** Setting up LiveData observation relationship **/
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)

        })

```

-- 11 Adding the Buzzer

> - GuessIt/app/src/main/AndroidManifest.xml

```xml
21-23+
    <!-- Permission needed for haptic feedback -->
    <uses-permission android:name="android.permission.VIBRATE" />

36-        <activity android:name=".MainActivity">
36-39+
        <activity
            android:name=".MainActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:screenOrientation="landscape">
```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameViewModel.kt

```kt
10-14+
private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

20-28+
    // These are the three different types of buzzing in the game. Buzz pattern is the number of
    // milliseconds each interval of buzzing and non-buzzing takes.
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

35-37+
        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

75-79+
    // Event that triggers the phone to buzz using different patterns, determined by BuzzType
    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

90-92+
                if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
97+                _eventBuzz.value = BuzzType.GAME_OVER
155+        _eventBuzz.value = BuzzType.CORRECT
165-168+
    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

```

> - GuessIt/app/src/main/java/com/example/android/guesstheword/screens/game/GameFragment.kt

```kt
77-91+
    /**
     * Given a pattern, this method makes sure the device buzzes
     */
    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }
20+import android.os.Vibrator
19+import android.os.Build
21+import android.os.VibrationEffect
26+import androidx.core.content.getSystemService
23-import android.text.format.DateUtils
76-83+
        // Buzzes when triggered with different buzz events
        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })

```

> - GuessIt/app/build.gradle

```
44-51+
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
```