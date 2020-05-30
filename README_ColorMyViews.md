# Color My Views

-- 00 Create ColorMyViews Project and One Box

add res/font/roboto.ttf

activity_main.xml

1-<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
1+<androidx.constraintlayout.widget.ConstraintLayout
2+    xmlns:android="http://schemas.android.com/apk/res/android"
6+    android:id="@+id/constraint_layout"

12+        android:id="@+id/box_one_text"


add res/values/dimens.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="margin_wide">16dp</dimen>
    <dimen name="margin_half">8dp</dimen>
    <dimen name="box_text_size">24sp</dimen>
</resources>


activity_main.xml

13-        android:layout_width="wrap_content"
13+        android:layout_width="0dp"
15+        android:layout_marginStart="@dimen/margin_wide"
16+        android:layout_marginTop="@dimen/margin_wide"
17+        android:layout_marginEnd="@dimen/margin_wide"


res/values/strings.xml

3+    <string name="box_one">Box One</string>


activity_main.xml

18-        android:text="Hello World!"
18+        android:text="@string/box_one"

19-        app:layout_constraintBottom_toBottomOf="parent"
20-        app:layout_constraintLeft_toLeftOf="parent"
21-        app:layout_constraintRight_toRightOf="parent"
19+        app:layout_constraintEnd_toEndOf="parent"
20+        app:layout_constraintStart_toStartOf="parent"

13+        style="@style/whiteBox"


res/values/styles.xml

9+
10+    <style name="whiteBox">
11+        <item name="android:background">@android:color/holo_green_light</item>
12+        <item name="android:textAlignment">center</item>
13+        <item name="android:textSize">@dimen/box_text_size</item>
14+        <item name="android:textStyle">bold</item>
15+        <item name="android:textColor">@android:color/white</item>
16+        <item name="android:fontFamily">@font/roboto</item>
17+    </style>


-- 01 Add Aligned Boxes with Click Handlers

res/values/strings.xml

4+    <string name="box_two">Box Two</string>

activity_main.xml

24+    <TextView
25+        android:id="@+id/box_two_text"
26+        android:layout_width="130dp"
27+        android:layout_height="130dp"
28+        android:layout_marginStart="@dimen/margin_wide"
29+        android:layout_marginTop="@dimen/margin_wide"
30+        android:layout_marginBottom="@dimen/margin_half"
31+        android:text="@string/box_two"
32+        app:layout_constraintStart_toStartOf="parent"
33+        app:layout_constraintTop_toBottomOf="@+id/box_one_text"
34+        app:layout_constraintVertical_bias="0.0" />
35+


res/values/strings.xml

5+    <string name="box_three">Box Three</string>

activity_main.xml

36+    <TextView
37+        android:id="@+id/box_three_text"
38+        style="@style/whiteBox"
39+        android:layout_width="0dp"
40+        android:layout_height="wrap_content"
41+        android:layout_marginStart="@dimen/margin_wide"
42+        android:layout_marginEnd="@dimen/margin_wide"
43+        android:text="@string/box_three"
44+        app:layout_constraintEnd_toEndOf="parent"
45+        app:layout_constraintStart_toEndOf="@+id/box_two_text"
46+        app:layout_constraintTop_toTopOf="@+id/box_two_text" />
47+


res/values/strings.xml

6+    <string name="box_four">Box Four</string>

activity_main.xml

44+        app:layout_constraintBottom_toTopOf="@+id/box_four_text"
49+    <TextView
50+        android:id="@+id/box_four_text"
51+        style="@style/whiteBox"
52+        android:layout_width="0dp"
53+        android:layout_height="wrap_content"
54+        android:layout_marginStart="@dimen/margin_wide"
55+        android:layout_marginTop="@dimen/margin_wide"
56+        android:layout_marginEnd="@dimen/margin_wide"
57+        android:layout_marginBottom="@dimen/margin_wide"
58+        android:text="@string/box_four"
59+        app:layout_constraintEnd_toEndOf="parent"
60+        app:layout_constraintStart_toEndOf="@+id/box_two_text"
61+        app:layout_constraintTop_toBottomOf="@+id/box_three_text"
62+        app:layout_constraintVertical_chainStyle="spread_inside" />
63+


res/values/strings.xml

7+    <string name="box_five">Box Five</string>

activity_main.xml

59+        app:layout_constraintBottom_toTopOf="@+id/box_five_text"
65+    <TextView
66+        android:id="@+id/box_five_text"
67+        style="@style/whiteBox"
68+        android:layout_width="0dp"
69+        android:layout_height="wrap_content"
70+        android:layout_marginStart="@dimen/margin_wide"
71+        android:layout_marginEnd="@dimen/margin_wide"
72+        android:text="@string/box_five"
73+        app:layout_constraintBottom_toBottomOf="@+id/box_two_text"
74+        app:layout_constraintEnd_toEndOf="parent"
75+        app:layout_constraintStart_toEndOf="@+id/box_two_text"
76+        app:layout_constraintTop_toBottomOf="@+id/box_four_text" />
77+


MainActivity.kt

11+
12+        setListeners()

13+
14+    private fun setListeners() {
15+        val clickableViews: List<View> =
16+            listOf(box_one_text, box_two_text, box_three_text,
17+                box_four_text, box_five_text)
18+
19+        for (item in clickableViews) {
20+            item.setOnClickListener { makeColored(it) }
21+        }
22+    }

5+import android.view.View
6+import kotlinx.android.synthetic.main.activity_main.*

25+
26+    private fun makeColored(view: View) {
27+        when (view.id) {
28+
29+            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
30+            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)
31+
32+            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
33+            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
34+            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)
35+
36+            else -> view.setBackgroundColor(Color.LTGRAY)
37+        }
38+    }

3+import android.graphics.Color
