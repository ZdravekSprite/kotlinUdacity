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


string.xml

5+    <string name="bio">Hi, my name is Zdravko.
6+\n\nI love fish.
7+\n\nThe kind that is alive and swims around in an aquarium or river, or a lake, and definitely the ocean.
8+\nFun fact is that I have several aquariums and also a river.
9+\n\nI like eating fish, too. Raw fish. Grilled fish. Smoked fish. Poached fish - not so much.
10+\nAnd sometimes I even go fishing.
11+\nAnd even less sometimes, I actually catch something.
12+\n\nOnce, when I was camping in Canada, and very hungry, I even caught a large salmon with my hands.
13+\n\nI\'ll be happy to teach you how to make your own aquarium.
14+\nYou should ask someone else about fishing, though.\n\n
15+    </string>


activity_main.xml

29+        <TextView
30+            android:id="@+id/bio_text"
31+            android:layout_width="match_parent"
32+            android:layout_height="wrap_content"
33+            android:text="@string/bio" />

33-            android:text="@string/bio" />
33+            android:text="@string/bio"
34+            style="@style/NameStyle" />


dimens.xml

6+    <dimen name="padding">16dp</dimen>

activity_main.xml

7-    android:layout_height="match_parent">
7+    android:layout_height="match_parent"
8+    android:paddingStart="@dimen/padding"
9+    android:paddingEnd="@dimen/padding">


-- 04 Add EditText, Done Button, ClickHandler

activity_main.xml

19+    <EditText
20+        android:id="@+id/nickname_edit"
21+        android:layout_width="match_parent"
22+        android:layout_height="wrap_content"
23+        android:ems="10"
24+        android:inputType="textPersonName"
25+        style="@style/NameStyle" />

string.xml

16+    <string name="what_is_your_nickname">What is your nickname</string>


activity_main.xml

24+        android:hint="@string/what_is_your_nickname"

26+        android:textAlignment="center"


string.xml

17+    <string name="done">Done</string>


activity_main.xml

29+    <Button
30+        android:id="@+id/done_button"
31+        android:layout_width="wrap_content"
32+        android:layout_height="wrap_content"
33+        android:text="@string/done" />

33-        android:text="@string/done" />
33+        android:layout_gravity="center_horizontal"
34+        android:layout_marginTop="@dimen/layout_margin"
35+        android:fontFamily="@font/roboto"
36+        android:text="@string/done"
37+        style="@style/Widget.AppCompat.Button.Colored" />

39+    <TextView
40+        android:id="@+id/nickname_text"
41+        android:layout_width="match_parent"
42+        android:layout_height="wrap_content" />

42-        android:layout_height="wrap_content" />
42+        android:layout_height="wrap_content"
43+        android:textAlignment="center"
44+        android:visibility="gone"
45+        style="@style/NameStyle" />


MainActivity.kt

9+        setContentView(R.layout.activity_main)

10+
11+       findViewById<Button>(R.id.done_button).setOnClickListener {
12+            addNickname(it)
13+        }

5+import android.widget.Button

16+
17+    private fun addNickname(view: View?) {
18+
19+    }

5+import android.view.View

19        val editText = findViewById<EditText>(R.id.nickname_edit)
20        val nicknameTextView = findViewById<TextView>(R.id.nickname_text)

7+import android.widget.EditText
8+import android.widget.TextView

24+        nicknameTextView.text = editText.text
25+        editText.visibility = View.GONE
26+        view.visibility = View.GONE
27+        nicknameTextView.visibility = View.VISIBLE

28+
29+        // Hide the keyboard.
30+        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
31+        imm.hideSoftInputFromWindow(view.windowToken, 0)

3+import android.content.Context
7+import android.view.inputmethod.InputMethodManager

28-        view.visibility = View.GONE
28+        if (view != null) {
29+            view.visibility = View.GONE
30+        }
35-        imm.hideSoftInputFromWindow(view.windowToken, 0)
35+        if (view != null) {
36+            imm.hideSoftInputFromWindow(view.windowToken, 0)
37+        }

-- 05 Implement Data Binding

app/build.gradle

24+
25+    dataBinding {
26+        enabled = true
27+    }
