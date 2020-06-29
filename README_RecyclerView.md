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

-- 09 Add Binding Adapters

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/BindingUtils.kt

```kt
package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?) {
    item?.let {
        setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}

@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
50-62-
            val res = itemView.context.resources

            binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
            binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
            binding.qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
50-51+
            binding.sleep = item
            binding.executePendingBindings()
27-29-
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
20-import android.view.View
21-22-
import android.widget.ImageView
import android.widget.TextView
```

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/list_item_sleep_night.xml

```xml
28+            app:sleepImage="@{sleep}"
41+            app:sleepDurationFormatted="@{sleep}"
53+            app:sleepQualityString="@{sleep}"
```

-- 10 Change LinearLayout to GridLayout

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/fragment_sleep_tracker.xml

```xml
45-46-
            app:layout_constraintTop_toBottomOf="@+id/stop_button"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
45+            app:layout_constraintTop_toBottomOf="@+id/stop_button" />
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
100-102+
        val manager = GridLayoutManager(activity, 3)
        binding.sleepList.layoutManager = manager

28+import androidx.recyclerview.widget.GridLayoutManager
```

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/list_item_sleep_night.xml

```xml
31-43-
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
            app:sleepDurationFormatted="@{sleep}"
            tools:text="Wednesday" />

34-            android:layout_height="20dp"
34+            android:layout_height="wrap_content"
36-41-
            app:layout_constraintEnd_toEndOf="@+id/sleep_length"
            app:layout_constraintHorizontal_bias="0.0"
            app:layout_constraintStart_toStartOf="@+id/sleep_length"
            app:layout_constraintTop_toBottomOf="@+id/sleep_length"
            app:sleepQualityString="@{sleep}"
            tools:text="Excellent!!!" />
36-39+
            app:layout_constraintEnd_toEndOf="@+id/quality_image"
            app:layout_constraintStart_toStartOf="@+id/quality_image"
            app:layout_constraintTop_toBottomOf="@+id/quality_image"
            app:sleepQualityString="@{sleep}" />
```

-- 11 Implement a Click Listener

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
74-77+

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}
```

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/list_item_sleep_night.xml

```xml
11-14+

        <variable
            name="clickListener"
            type="com.example.android.trackmysleepquality.sleeptracker.SleepNightListener" />
19+        android:onClick="@{() -> clickListener.onClick(sleep)}"
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
27-class SleepNightAdapter : ListAdapter<SleepNight,
27+class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight,
33-        holder.bind(item)
33+        holder.bind(clickListener,item)
//holder.bind(clickListener,getItem(position)!!)
43-        fun bind(item: SleepNight) {
43+        fun bind(clickListener: SleepNightListener, item: SleepNight) {
45+            binding.clickListener = clickListener
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
104-        val adapter = SleepNightAdapter()
104-106+
        val adapter = SleepNightAdapter(SleepNightListener { nightId ->
            Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
        })
23+import android.widget.Toast
```

-- 12 Navigate on Click

> - SleepTracker-with-RecyclerView/app/src/main/res/layout/fragment_sleep_detail.xml

```xml
19-65/-
        <ImageView
            android:id="@+id/quality_image"
            android:layout_width="@dimen/icon_size"
            android:layout_height="@dimen/icon_size"
            android:layout_marginStart="8dp"
            android:layout_marginTop="56dp"
            android:layout_marginEnd="8dp"
            app:sleepImage="@{sleepDetailViewModel.night}"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/quality_string"
            android:layout_width="wrap_content"
            android:layout_height="20dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="100dp"
            android:layout_marginEnd="8dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/quality_image"
            app:sleepQualityString="@{sleepDetailViewModel.night}" />

        <TextView
            android:id="@+id/sleep_length"
            android:layout_width="wrap_content"
            android:layout_height="20dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="8dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/quality_string"
            app:sleepDurationFormatted="@{sleepDetailViewModel.night}" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:layout_marginEnd="8dp"
            android:layout_marginBottom="32dp"
            android:onClick="@{() -> sleepDetailViewModel.onClose()}"
            android:text="@string/close"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent" />
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
106-            Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
106+            sleepTrackerViewModel.onSleepNightClicked(nightId)
23-import android.widget.Toast
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerViewModel.kt

```kt
131-142+
    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
101-110+
        sleepTrackerViewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner, Observer { night ->
            night?.let {

                this.findNavController().navigate(
                        SleepTrackerFragmentDirections
                                .actionSleepTrackerFragmentToSleepDetailFragment(night))
                sleepTrackerViewModel.onSleepDataQualityNavigated()
            }
        })

```

-- 13 Add a List Header

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepNightAdapter.kt

```kt
79-90+

sealed class DataItem {
    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}
40-49+
    class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

20+import android.view.View
25+import com.example.android.trackmysleepquality.R
29-30-
class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight,
        SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
29-30+
class SleepNightAdapter(val clickListener: SleepNightListener) : ListAdapter<DataItem,
        RecyclerView.ViewHolder>(SleepNightDiffCallback()) {
29-31+
private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

41-42-
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
41-46+
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
35-38-
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener,item)
35-41+
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(clickListener, nightItem.sleepNight)
            }
        }
62-68+
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

95-97-
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
95-97+
class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
100-    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
100+    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
35-36+
    private val adapterScope = CoroutineScope(Dispatchers.Default)

28-29+
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
39-50+
    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

30-31+
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
```

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
121-                adapter.submitList(it)
121+                adapter.addHeaderAndSubmitList(it)
```

-- 14 Add a Header to the GridLayout

> - SleepTracker-with-RecyclerView/app/src/main/java/com/example/android/trackmysleepquality/sleeptracker/SleepTrackerFragment.kt

```kt
114-120+
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 3
                else -> 1
            }
        }

```