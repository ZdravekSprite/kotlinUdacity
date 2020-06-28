# Sleep Tracker with RecyclerView

-- 01 Add a RecyclerView

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/fragment_sleep_tracker.xml

```xml
38-63-
        <!-- Simplest way of displaying scrollable text and data. There is a
             better and more efficient way to do this, and you will learn about
             RecyclerView in a later lesson. -->

        <ScrollView
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintBottom_toTopOf="@+id/clear_button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/stop_button">

            <!-- In the TextView, we can access the nightsString LiveData,
                 which keeps it displayed and updated in the TextView
                 whenever it changes. -->

            <TextView
                android:id="@+id/textview"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="@dimen/margin"
                android:layout_marginTop="@dimen/margin"
                android:layout_marginEnd="@dimen/margin"
                android:text="@{sleepTrackerViewModel.nightsString}" />
        </ScrollView>
38-46+
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/sleep_list"
            android:layout_width="0dp"
            android:layout_height="0dp"
            app:layout_constraintBottom_toTopOf="@+id/clear_button"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/stop_button"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
19-21+
class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    
}
19-21+
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.TextItemViewHolder

23+    var data = listOf<SleepNight>()
21+import com.example.android.trackmysleepquality.database.SleepNight
25-35+

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
19+import android.view.ViewGroup
```

-- 02 Display SleepQuality Data

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
35-        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
38-39+
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
19+import android.view.LayoutInflater
21+import android.widget.TextView
23+import com.example.android.trackmysleepquality.R
29-32
        set(value) {
            field = value
            notifyDataSetChanged()
        }
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
100-108+
        val adapter = SleepNightAdapter()
        binding.sleepList.adapter = adapter

        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

```