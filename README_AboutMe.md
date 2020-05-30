# About Me

-- 00 delete Layout file

MainActivity.kt

9-        setContentView(R.layout.activity_main)

-- 01 create Layout file

activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
	android:layout_width="match_parent"
    android:layout_height="match_parent">

</LinearLayout>

-- 02 Add a TextView, ImageView, and Styling

activity_main.xml

8+    <TextView
9+        android:id="@+id/textView"
10+        android:layout_width="match_parent"
11+        android:layout_height="wrap_content"
12+        android:text="Zdravko Šplajt" />


string.xml

3+    <string name="name">Zdravko Šplajt</string>


activity_main.xml

12-        android:text="Zdravko Šplajt" />
12+        android:text="@string/name" />

12-        android:text="@string/name" />
12+        android:text="@string/name"
13+        android:textAlignment="center" />

dimens.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="text_size">20sp</dimen>
    <dimen name="small_padding">8dp</dimen>
    <dimen name="layout_margin">16dp</dimen>
</resources>

font/roboto.ttf

styles.xml

10+    <style name="NameStyle">
11+        <item name="android:layout_marginTop">@dimen/layout_margin</item>
12+        <item name="android:fontFamily">@font/roboto</item>
13+        <item name="android:paddingTop">@dimen/small_padding</item>
14+        <item name="android:textColor">@android:color/black</item>
15+        <item name="android:textSize">@dimen/text_size</item>
16+    </style>

activity_main.xml

13-        android:textAlignment="center" />
13+        android:textAlignment="center"
14+        style="@style/NameStyle" />

4+    xmlns:app="http://schemas.android.com/apk/res-auto"
16+    <ImageView
17+        android:id="@+id/star_image"
18+        android:layout_width="match_parent"
19+        android:layout_height="wrap_content"
20+        app:srcCompat="@android:drawable/btn_star_big_on" />

string.xml

4+    <string name="yellow_star">Yellow star</string>

activity_main.xml

21+        android:contentDescription="@string/yellow_star"

21+        android:layout_marginTop="@dimen/layout_margin"


--03 Add a ScrollView


activity_main.xml

24+
25+    <ScrollView
26+        android:layout_width="match_parent"
27+        android:layout_height="match_parent">
28+
29+    </ScrollView>
