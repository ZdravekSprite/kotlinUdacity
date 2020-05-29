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

12+        android:textSize="30sp"

12+        android:layout_gravity="center_horizontal"


-- 03 LinearLayout

activity_main.xml

7+    android:orientation="vertical"

7+    android:layout_gravity="center_vertical"


-- 04 Button

activity_main.xml

23-        android:text="Roll" />
23+        android:text="@string/roll" />


res/values/strings.xml

3+    <string name="roll">Roll</string>


activity_main.xml

20+        android:id="@+id/roll_button"


-- 05 findViewById or View Binding

TODO return to do View Binding

-- 05a findViewById

MainActivity.kt

10+
11+    val rollButton: Button = findViewById(R.id.roll_button)

5+    import android.widget.Button

13+    rollButton.text = "Let's Roll"


-- 06a setOnClickListener

MainActivity.kt

13-        rollButton.text = "Let's Roll"
13+        rollButton.setOnClickListener {
14+        }

14+            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()

6+import android.widget.Toast

-- 07a Change the Text

activity_main.xml

12+        android:id="@+id/result_text"


MainActivity.kt

6-import android.widget.Toast
15-            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
15+            rollDice()

17+
18+    private fun rollDice() {
19+    }

19+        val randomInt = Random().nextInt(6) + 1

6+import java.util.*

21+
22+        val resultText: TextView = findViewById(R.id.result_text)

6+import android.widget.TextView

24+        resultText.text = randomInt.toString()

-- 08a Text to Images