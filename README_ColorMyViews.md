﻿# Color My Views

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