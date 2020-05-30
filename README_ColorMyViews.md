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

