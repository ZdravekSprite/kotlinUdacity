# Kotlin Udacity

- Dice Roller

-- 00 Hello Android

activity_main.xml

12-android:text="Hello World!"
12+android:text="Hello Android!"


-- 01 LinearLayout

activity_main.xml

2-<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
2+<LinearLayout
3+    xmlns:android="http://schemas.android.com/apk/res/android"
18-</androidx.constraintlayout.widget.ConstraintLayout>
19+</LinearLayout>

13-    android:text="Hello Android!"
14-    app:layout_constraintBottom_toBottomOf="parent"
15-    app:layout_constraintLeft_toLeftOf="parent"
16-    app:layout_constraintRight_toRightOf="parent"
17-    app:layout_constraintTop_toTopOf="parent" />
13+    android:text=""
14+    tools:text="1" />

4-    xmlns:app="http://schemas.android.com/apk/res-auto"
7-    android:layout_height="match_parent"
6+    android:layout_height="wrap_content"


-- 02 Add Button

activity_main.xml

15+    <Button
16+        android:layout_width="wrap_content"
17+        android:layout_height="wrap_content"
18+        android:layout_gravity="center_horizontal"
19+        android:text="Roll" />
