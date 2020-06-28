# Sleep Tracker

-- 00 Introduction

-- 01 Creating the SleepNight Entity

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/database/SleepNight.kt

```kt
19-36+
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1
)
```

-- 02 DAO - SleepDatabaseDao

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/database/SleepDatabaseDao.kt

```kt
19-22+
/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
19-20+
import androidx.room.Dao

25-interface SleepDatabaseDao
25-28+
interface SleepDatabaseDao {
    @Insert
    fun insert(night: SleepNight)
}
20+import androidx.room.Insert
29-37+

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    fun update(night: SleepNight)
21+import androidx.room.Update
39-46+

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key: Long): SleepNight?
21+import androidx.room.Query
48-55+

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()
56-63+

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
19+import androidx.lifecycle.LiveData
65-70+

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
```

-- 03 Creating a Room Database

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/database/SleepDatabase.kt

```kt
19-29+
/**
 * A database that stores SleepNight information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

}
19-21+
import androidx.room.Database
import androidx.room.RoomDatabase

32-36+
    /**
     * Connects the database to the DAO.
     */
    abstract val sleepDatabaseDao: SleepDatabaseDao

37-45+
    /**
     * Define a companion object, this allows us to add functions on the SleepDatabase class.
     *
     * For example, clients can call `SleepDatabase.getInstance(context)` to instantiate
     * a new SleepDatabase.
     */
    companion object {
        
    }
44-55+
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: SleepDatabase? = null

56-75+
        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         * To learn more about Singleton read the wikipedia article:
         * https://en.wikipedia.org/wiki/Singleton_pattern
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): SleepDatabase {
        }
19+import android.content.Context
75-79+
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
            }
79-81+
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
82-83+
                // Return instance; smart cast to be non-null.
                return instance
82-97+
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    )
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this lesson. You can learn more about
                            // migration with Room in this blog post:
                            // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                            .fallbackToDestructiveMigration()
                            .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
21+import androidx.room.Room
```

> - SleepTracker/app/src/androidTest/java/com/example/android/trackmysleepquality/SleepDatabaseTest.kt

```kt
package com.example.android.trackmysleepquality

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }
}
```

-- 04 Adding a ViewModel

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
47-50+
        val application = requireNotNull(this.activity).application

		// Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
26+import com.example.android.trackmysleepquality.database.SleepDatabase
52-57+
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        val sleepTrackerViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)

25+import androidx.lifecycle.ViewModelProviders
59-60+
        binding.setLifecycleOwner(this)

```

> - SleepTracker/app/src/main/res/layout/fragment_sleep_tracker.xml

```xml
26-
26-28+
        <variable
            name="sleepTrackerViewModel"
            type="com.example.android.trackmysleepquality.sleeptracker.SleepTrackerViewModel" />

```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
59-60+
        binding.sleepTrackerViewModel = sleepTrackerViewModel

```

-- 05 Coroutines for Long-running Operations

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerViewModel.kt

```kt
29-33+

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()
22+import kotlinx.coroutines.Job
35-46+

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this uiScope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
22-23+
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
49-50+

    private var tonight = MutableLiveData<SleepNight?>()
21+import androidx.lifecycle.MutableLiveData
23+import com.example.android.trackmysleepquality.database.SleepNight
53-64+

    private val nights = database.getAllNights()

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }
27+import kotlinx.coroutines.launch
66-75+

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getTonightFromDatabase(): SleepNight? {
    }
75-76+
        return withContext(Dispatchers.IO) {
        }
24-27-
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
24+import kotlinx.coroutines.*
73-77+
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
80-85+

    /**
     * Executes when the START button is clicked.
     */
    fun onStartTracking() {
    }
85-86+
        uiScope.launch {
        }
86-92+
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newNight = SleepNight()

            insert(newNight)

            tonight.value = getTonightFromDatabase()
81-86+
    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

101-118+

    /**
     * Executes when the STOP button is clicked.
     */
    fun onStopTracking() {
        uiScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)
        }
    }
81-86+
    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

125-137+

    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        uiScope.launch {
            // Clear the database table.
            clear()

            // And clear tonight since it's no longer in the database
            tonight.value = null
        }
    }
81-86+
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

144-154+

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
```

> - SleepTracker/app/src/main/res/layout/fragment_sleep_tracker.xml

```xml
75+            android:onClick="@{() -> sleepTrackerViewModel.onStartTracking()}"
89+            android:onClick="@{() -> sleepTrackerViewModel.onStopTracking()}"
103+            android:onClick="@{() -> sleepTrackerViewModel.onClear()}"
```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerViewModel.kt

```kt
54-60+
    /**
     * Converted nights to Spanned for displaying.
     */
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

22+import androidx.lifecycle.Transformations
24+import com.example.android.trackmysleepquality.formatNights
```

> - SleepTracker/app/src/main/res/layout/fragment_sleep_tracker.xml

```xml
61-                android:text="@string/how_was_hour_sleep" />
61+                android:text="@{sleepTrackerViewModel.nightsString}" />
```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/Util.kt

```kt
72-100/-
fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
                append(resources.getString(R.string.hours_slept))
                // Hours
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
                // Seconds
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
24-25+
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.trackmysleepquality.database.SleepNight
```

-- 06 Recording Sleep Quality

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerViewModel.kt

```kt
63-85+
    /**
     * Variable that tells the Fragment to navigate to a specific [SleepQualityFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    /**
     * If this is non-null, immediately navigate to [SleepQualityFragment] and call [doneNavigating]
     */
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

21+import androidx.lifecycle.LiveData
162-164+

            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
63-81+
        // Add an Observer on the state variable for Navigating when STOP button is pressed.
        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {
                // We need to get the navController from this, because button is not ready, and it
                // just has to be a view. For some reason, this only matters if we hit stop again
                // after using the back button, not if we hit stop and choose a quality.
                // Also, in the Navigation Editor, for Quality -> Tracker, check "Inclusive" for
                // popping the stack to get the correct behavior if we press stop multiple times
                // followed by back.
                // Also: https://stackoverflow.com/questions/28929637/difference-and-uses-of-oncreate-oncreateview-and-onactivitycreated-in-fra
                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepTrackerViewModel.doneNavigating()
            }
        })

25+import androidx.lifecycle.Observer
27+import androidx.navigation.fragment.findNavController
```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleepquality/SleepQualityViewModel.kt

```kt
19-28+
/**
 * ViewModel for SleepQualityFragment.
 *
 * @param sleepNightKey The key of the current night we are working on.
 */
class SleepQualityViewModel(
        private val sleepNightKey: Long = 0L,
        val database: SleepDatabaseDao) : ViewModel() {
}
19-21+
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

31-36+
    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
21+import kotlinx.coroutines.Job
28-49+

    /**
     * A [CoroutineScope] keeps track of all coroutines started by this ViewModel.
     *
     * Because we pass it [viewModelJob], any coroutine started in this scope can be cancelled
     * by calling `viewModelJob.cancel()`
     *
     * By default, all coroutines started in uiScope will launch in [Dispatchers.Main] which is
     * the main thread on Android. This is a sensible default because most coroutines started by
     * a [ViewModel] update the UI after performing some processing.
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
21-22+
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
52-61+

    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
53-60+
    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

19+import androidx.lifecycle.MutableLiveData
62-67+
    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

19+import androidx.lifecycle.LiveData
78-84+

    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }
85-104+

    /**
     * Sets the sleep quality and updates the database.
     *
     * Then navigates back to the SleepTrackerFragment.
     */
    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleepTracker.value = true
        }
    }
23-25-
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
23+import kotlinx.coroutines.*
```

>- SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleepquality/SleepQualityViewModelFactory.kt

```kt
19-34+
/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class SleepQualityViewModelFactory(
        private val sleepNightKey: Long,
        private val dataSource: SleepDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
19-22+
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.database.SleepDatabaseDao

```

> - SleepTracker/app/src/main/java/com/example/android/trackmysleepquality/sleepquality/SleepQualityFragment.kt

```kt
50-54+
        val arguments = SleepQualityFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

26+import com.example.android.trackmysleepquality.database.SleepDatabase
55-60+
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepQualityViewModel =
                ViewModelProviders.of(
                        this, viewModelFactory).get(SleepQualityViewModel::class.java)
25+import androidx.lifecycle.ViewModelProviders
63-77+
        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.sleepQualityViewModel = sleepQualityViewModel

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepQualityViewModel.doneNavigating()
            }
        })

25+import androidx.lifecycle.Observer
27+import androidx.navigation.fragment.findNavController
```

> - SleepTracker/app/src/main/res/layout/fragment_sleep_quality.xml

```xml
26-
26-28+
        <variable
            name="sleepQualityViewModel"
            type="com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModel" />
58+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(0)}"
69+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(1)}"
81+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(2)}"
95+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(3)}"
108+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(4)}"
121+            android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(5)}"
```