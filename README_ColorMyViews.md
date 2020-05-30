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
25+
