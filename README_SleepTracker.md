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
