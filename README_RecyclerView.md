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

-- 05 Refactor onBindViewHolder

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
42-52-
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
42+        holder.bind(item)
57-70+

        fun bind(item: SleepNight) {
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
        }
41-        val res = holder.itemView.context.resources
41+
59-60+
            val res = itemView.context.resources

54+
```

-- 06 Refactor onCreateViewHolder

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
46-50-
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.list_item_sleep_night, parent, false)

        return ViewHolder(view)
46+        return ViewHolder.from(parent)
70-79+

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night, parent, false)

                return ViewHolder(view)
            }
        }
49-    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
49+    class ViewHolder private constructor (itemView: View) : RecyclerView.ViewHolder(itemView){
```

-- 07 Refresh Data with DiffUtil

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
82-91+

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
}
24+import androidx.recyclerview.widget.DiffUtil
91-97+
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }
31-class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
31-32+
class SleepNightAdapter : ListAdapter<SleepNight,
        SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
25+import androidx.recyclerview.widget.ListAdapter
37-40-
    var data = listOf<SleepNight>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
36-        val item = data[position]
36+        val item = getItem(position)
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
105-                adapter.data = it
105+                adapter.submitList(it)
68-        sleepTrackerViewModel.showSnackBarEvent.observe(this, Observer {
68+        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
71-                        activity!!.findViewById(android.R.id.content),
71+                        requireActivity().findViewById(android.R.id.content),
82-        sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer { night ->
82+        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night ->
```

-- 08 Add DataBinding to the Adapter

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/list_item_sleep_night.xml

```xml
2-4-
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
2-13+
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="sleep"
            type="com.example.android.trackmysleepquality.database.SleepNight" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
54+</layout>
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
70-73-
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night, parent, false)
70+                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
31+import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
73-                return ViewHolder(view)
73+                return ViewHolder(binding)
46-50-
    class ViewHolder private constructor (itemView: View) : RecyclerView.ViewHolder(itemView){

        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)
46-47+
    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root) {
52-54-
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            quality.text = convertNumericQualityToString(item.sleepQuality, res)
            qualityImage.setImageResource(when (item.sleepQuality) {
52-54+
            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(when (item.sleepQuality) {
```