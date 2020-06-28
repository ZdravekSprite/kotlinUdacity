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

-- 03 Recycling ViewHolders

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
39-45+

        if (item.sleepQuality <= 1) {
            holder.textView.setTextColor(Color.RED) // red
        } else {
            // reset
            holder.textView.setTextColor(Color.BLACK) // black
        }
19+import android.graphics.Color
```

-- 04 Display the SleepQuality List

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/list_item_sleep_night.xml

```xml
7-19+

    <ImageView
        android:id="@+id/quality_image"
        android:layout_width="@dimen/icon_size"
        android:layout_height="60dp"
        android:layout_marginStart="16dp"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:srcCompat="@drawable/ic_sleep_5" />

4-5+
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
22-44+
    <TextView
        android:id="@+id/sleep_length"
        android:layout_width="0dp"
        android:layout_height="20dp"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="16dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/quality_image"
        app:layout_constraintTop_toTopOf="@+id/quality_image"
        tools:text="Wednesday" />

    <TextView
        android:id="@+id/quality_string"
        android:layout_width="0dp"
        android:layout_height="20dp"
        android:layout_marginTop="8dp"
        app:layout_constraintEnd_toEndOf="@+id/sleep_length"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="@+id/sleep_length"
        app:layout_constraintTop_toBottomOf="@+id/sleep_length"
        tools:text="Excellent!!!" />

```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
56-58+

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
21+import android.view.View
59-61+
        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
23+import android.widget.ImageView
30-class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
30+class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
51-    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
51+    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
54-                .inflate(R.layout.text_item_view, parent, false) as TextView
54+                .inflate(R.layout.list_item_sleep_night, parent, false)
56-        return TextItemViewHolder(view)
56+        return ViewHolder(view)
39-    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
39+    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
41-48-
        holder.textView.text = item.sleepQuality.toString()

        if (item.sleepQuality <= 1) {
            holder.textView.setTextColor(Color.RED) // red
        } else {
            // reset
            holder.textView.setTextColor(Color.BLACK) // black
        }
41-51+
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
27-import com.example.android.trackmysleepquality.TextItemViewHolder
27-28+
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
19-import android.graphics.Color
```