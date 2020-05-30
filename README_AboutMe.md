# About Me

-- 00 delete Layout file

MainActivity.kt

9-        setContentView(R.layout.activity_main)

-- 01 create Layout file

activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

</LinearLayout>

-- 02 Add a TextView, ImageView, and Styling

activity_main.xml

7+    <TextView
8+        android:id="@+id/textView"
9+        android:layout_width="match_parent"
10+        android:layout_height="wrap_content"
11+        android:text="Zdravko Šplajt" />


string.xml

3+    <string name="name">Zdravko Šplajt</string>


activity_main.xml

11-        android:text="Zdravko Šplajt" />
11+        android:text="@string/name" />

11-        android:text="@string/name" />
11+        android:text="@string/name"
12+        android:textAlignment="center" />

dimens.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="text_size">20sp</dimen>
    <dimen name="small_padding">8dp</dimen>
    <dimen name="layout_margin">16dp</dimen>
</resources>