# Android Trivia

-- 00 open starter code

-- 01 Creating and Adding a Fragment

TitleFragment.kt

package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false)
        return binding.root
    }
}

activity_main.xml

24+            <fragment
25+                android:id="@+id/titleFragment"
26+                android:name="com.example.android.navigation.TitleFragment"
27+                android:layout_width="match_parent"
28+                android:layout_height="match_parent" />

-- 02 Let’s Navigate Already

build.gradle (Project)

//25-        version_navigation = "1.0.0"
//25+        version_navigation = "2.3.0"


app/build.gradle

60+
61+    // Navigation
62+    implementation "android.arch.navigation:navigation-fragment-ktx:$version_navigation"
63+    implementation "android.arch.navigation:navigation-ui-ktx:$version_navigation"

navigation.xml

<?xml version="1.0" encoding="utf-8"?>
<navigation
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_root"
    app:startDestination="@+id/titleFragment">
    <fragment
        android:id="@+id/titleFragment"
        android:name="com.example.android.navigation.TitleFragment"
        android:label="@string/android_trivia"
        tools:layout="@layout/fragment_title">
        <action
            android:id="@+id/action_titleFragment_to_gameFragment"
            app:destination="@id/gameFragment" />
    </fragment>
    <fragment
        android:id="@+id/gameFragment"
        android:name="com.example.android.navigation.GameFragment"
        android:label="@string/android_trivia"
        tools:layout="@layout/fragment_game">
    </fragment>
</navigation>

yctivity_main.xml

25-                android:id="@+id/titleFragment"
26-                android:name="com.example.android.navigation.TitleFragment"
27-                android:layout_width="match_parent"
28-                android:layout_height="match_parent" />
25+                android:id="@+id/myNavHostFragment"
26+                android:name="androidx.navigation.fragment.NavHostFragment"
27+                android:layout_width="match_parent"
28+                android:layout_height="match_parent"
29+                app:defaultNavHost="true"
30+                app:navGraph="@navigation/navigation" />


TitleFragment.kt

16+        binding.playButton.setOnClickListener (
17+                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))
9+import androidx.navigation.Navigation


-- 03 Conditional Navigation

app/src/main/res/navigation/navigation.xml

22+        <action
23+            android:id="@+id/action_gameFragment_to_gameOverFragment"
24+            app:destination="@id/gameOverFragment" />
26+    <fragment
27+        android:id="@+id/gameOverFragment"
28+        android:name="com.example.android.navigation.GameOverFragment"
29+        android:label="@string/android_trivia"
30+        tools:layout="@layout/fragment_game_over">
31+    </fragment>

app/src/main/java/com/example/android/navigation/GameFragment.kt

78-        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
79-        { view: View ->
78+        binding.submitButton.setOnClickListener { view: View ->
102+                    view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
26+import androidx.navigation.findNavController


app/src/main/res/navigation/navigation.xml

25+        <action
26+            android:id="@+id/action_gameFragment_to_gameWonFragment"
27+            app:destination="@id/gameWonFragment" />
29+    <fragment
30+        android:id="@+id/gameWonFragment"
31+        android:name="com.example.android.navigation.GameWonFragment"
32+        android:label="@string/android_trivia"
33+        tools:layout="@layout/fragment_game_won">
34+    </fragment>


app/src/main/java/com/example/android/navigation/GameFragment.kt

100+                        view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)


-- 04 Back Stack Manipulation

.idea/navEditor.xml

46..75+
                        <option name="myPositions">
                          <map>
                            <entry key="action_gameOverFragment_to_gameFragment">
                              <value>
                                <LayoutPositions />
                              </value>
                            </entry>
                          </map>
                        </option>
                      </LayoutPositions>
                    </value>
                  </entry>
                  <entry key="gameWonFragment">
                    <value>
                      <LayoutPositions>
                        <option name="myPosition">
                          <Point>
                            <option name="x" value="12" />
                            <option name="y" value="12" />
                          </Point>
                        </option>
                        <option name="myPositions">
                          <map>
                            <entry key="action_gameWonFragment_to_gameFragment">
                              <value>
                                <LayoutPositions />
                              </value>
                            </entry>
                          </map>
                        </option>

app/src/main/res/navigation/navigation.xml

24-            app:destination="@id/gameOverFragment" />
24+            app:destination="@id/gameOverFragment"
25+            app:popUpTo="@+id/gameFragment"
26+            app:popUpToInclusive="true" />

29-            app:destination="@id/gameWonFragment" />
29+            app:destination="@id/gameWonFragment"
30+            app:popUpTo="@+id/gameFragment"
31+            app:popUpToInclusive="true" />

44+        <action
45+            android:id="@+id/action_gameOverFragment_to_gameFragment"
46+            app:destination="@id/gameFragment"
47+            app:popUpTo="@+id/titleFragment" />

38+        <action
39+            android:id="@+id/action_gameWonFragment_to_gameFragment"
40+            app:destination="@id/gameFragment"
41+            app:popUpTo="@+id/titleFragment"/>


app/src/main/java/com/example/android/navigation/GameOverFragment.kt

33+        binding.tryAgainButton.setOnClickListener { view: View ->
34+            view.findNavController().navigate(R.id.action_gameOverFragment_to_gameFragment)
35+        }
25+import androidx.navigation.findNavController


app/src/main/java/com/example/android/navigation/GameWonFragment.kt

34+        binding.nextMatchButton.setOnClickListener { view: View ->
35+            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
36+        }
25+import androidx.navigation.findNavController


-- 05 Adding Support for the Up Button

app/src/main/java/com/example/android/navigation/MainActivity.kt

29+        val navController = this.findNavController(R.id.myNavHostFragment)
22+import androidx.navigation.findNavController

31+        NavigationUI.setupActionBarWithNavController(this, navController)
23+import androidx.navigation.ui.NavigationUI

34+
35+    override fun onSupportNavigateUp(): Boolean {
36+        val navController = this.findNavController(R.id.myNavHostFragment)
37+        return navController.navigateUp()
38+    }


-- 06 Adding a Menu

app/src/main/res/navigation/navigation.xml

53+    <fragment
54+        android:id="@+id/aboutFragment"
55+        android:name="com.example.android.navigation.AboutFragment"
56+        android:label="@string/title_about_trivia"
57+        tools:layout="@layout/fragment_about" />


app/src/main/res/menu/overflow_menu.xml

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/aboutFragment"
        android:title="@string/about" />
</menu>


app/src/main/java/com/example/android/navigation/TitleFragment.kt 

19+        setHasOptionsMenu(true)

22+
23+    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
24+        super.onCreateOptionsMenu(menu, inflater)
25+        inflater?.inflate(R.menu.overflow_menu, menu)
26+    }
4+import android.view.*

25+
26+    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
27+        return NavigationUI.onNavDestinationSelected(item!!,
28+                view!!.findNavController())
29+                || super.onOptionsItemSelected(item)
30+    }
8+import androidx.navigation.findNavController
9+import androidx.navigation.ui.NavigationUI


-- 07 Adding Safe Arguments

build.gradle

34+        classpath "android.arch.navigation:navigation-safe-args-gradle-plugin:$version_navigation"


app/build.gradle

23+ apply plugin: 'androidx.navigation.safeargs'
24+
