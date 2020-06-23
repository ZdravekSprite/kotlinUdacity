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

activity_main.xml

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

```
24-    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
24+    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
```

25+
26+    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
27+        return NavigationUI.onNavDestinationSelected(item!!,
28+                view!!.findNavController())
29+                || super.onOptionsItemSelected(item)
30+    }
8+import androidx.navigation.findNavController
9+import androidx.navigation.ui.NavigationUI

```
28-    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
28+    override fun onOptionsItemSelected(item: MenuItem): Boolean {
```

-- 07 Adding Safe Arguments

build.gradle

34+        classpath "android.arch.navigation:navigation-safe-args-gradle-plugin:$version_navigation"


app/build.gradle

23+ apply plugin: 'androidx.navigation.safeargs'
24+


app/src/main/java/com/example/android/navigation/GameFragment.kt

100-                        view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
100+                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment())

104-                    view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
104+                    view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())


app/src/main/res/navigation/navigation.xml

42+        <argument
43+            android:name="numQuestions"
44+            app:argType="integer" />

45+        <argument
46+            android:name="numCorrect"
47+            app:argType="integer" />

app/src/main/java/com/example/android/navigation/GameFragment.kt 

100-                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment())
100+                        view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex))


app/src/main/java/com/example/android/navigation/GameWonFragment.kt

38+        var args = GameWonFragmentArgs.fromBundle(arguments!!)
39+        Toast.makeText(context,
40+                "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
41+                Toast.LENGTH_LONG).show()
23+import android.widget.Toast


app/src/main/java/com/example/android/navigation/GameOverFragment.kt

35-            view.findNavController().navigate(R.id.action_gameOverFragment_to_gameFragment)
35+            view.findNavController().navigate(GameOverFragmentDirections.actionGameOverFragmentToGameFragment())


app/src/main/java/com/example/android/navigation/GameWonFragment.kt

37-            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
37+            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())


app/src/main/java/com/example/android/navigation/TitleFragment.kt

17-        binding.playButton.setOnClickListener (
18-                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))
17+        binding.playButton.setOnClickListener { v: View ->
18+            v.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
19+        }


-- 08 Adding Sharing with an Intent

app/src/main/java/com/example/android/navigation/GameWonFragment.kt

39-        var args = GameWonFragmentArgs.fromBundle(arguments!!)
40-        Toast.makeText(context,
41-                "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
42-                Toast.LENGTH_LONG).show()
23-import android.widget.Toast
38+        setHasOptionsMenu(true)

41+
42+    private fun getShareIntent() : Intent {
43+        val args = GameWonFragmentArgs.fromBundle(arguments!!)
44+        return ShareCompat.IntentBuilder.from(activity)
45+                .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
46+                .setType("text/plain")
47+                .intent
48+    }
19+import android.content.Intent
24+import androidx.core.app.ShareCompat

```
44-        return ShareCompat.IntentBuilder.from(activity)
44+        return ShareCompat.IntentBuilder.from(activity!!)
```
51+
52+    private fun shareSuccess() {
53+        startActivity(getShareIntent())
54+    }

55+
56+    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
57+        super.onCreateOptionsMenu(menu, inflater)
58+        inflater?.inflate(R.menu.winner_menu, menu)
59+        // check if the activity resolves
60+        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
61+            // hide the menu item if it doesn't resolve
62+            menu?.findItem(R.id.share)?.setVisible(false)
63+        }
64+    }
21-import android.view.LayoutInflater
22-import android.view.View
23-import android.view.ViewGroup
21+import android.view.*

```
54-    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
54+    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
56-        inflater?.inflate(R.menu.winner_menu, menu)
56+        inflater.inflate(R.menu.winner_menu, menu)
60-            menu?.findItem(R.id.share)?.setVisible(false)
60+            menu.findItem(R.id.share)?.setVisible(false)
```

63+
64+    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
65+        when (item!!.itemId) {
66+            R.id.share -> shareSuccess()
67+        }
68+        return super.onOptionsItemSelected(item)
69+    }

```
64-65-
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
64-65+
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
```

-- 09 Adding the Navigation Drawer

app/src/main/res/navigation/navigation.xml

59+    <fragment
60+        android:id="@+id/rulesFragment"
61+        android:name="com.example.android.navigation.RulesFragment"
62+        android:label="@string/title_trivia_rules"
63+        tools:layout="@layout/fragment_rules" />


app/src/main/res/menu/navdrawer_menu.xml

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/rulesFragment"
        android:icon="@drawable/rules"
        android:title="@string/rules" />
    <item
        android:id="@+id/aboutFragment"
        android:icon="@drawable/android"
        android:title="@string/about" />
</menu>


app/src/main/res/layout/activity_main.xml

20+    <androidx.drawerlayout.widget.DrawerLayout
21+        android:id="@+id/drawerLayout"
22+        android:layout_width="match_parent"
23+        android:layout_height="match_parent">
24+        
37+    </androidx.drawerlayout.widget.DrawerLayout>

37+
38+        <com.google.android.material.navigation.NavigationView
39+            android:id="@+id/navView"
40+            android:layout_width="wrap_content"
41+            android:layout_height="match_parent"
42+            android:layout_gravity="start"
43+            app:headerLayout="@layout/nav_header"
44+            app:menu="@menu/navdrawer_menu" />
45+        


app/src/main/java/com/example/android/navigation/MainActivity.kt

27+    private lateinit var drawerLayout: DrawerLayout
28+    private lateinit var appBarConfiguration : AppBarConfiguration
22+import androidx.drawerlayout.widget.DrawerLayout
24+import androidx.navigation.ui.AppBarConfiguration

33-        @Suppress("UNUSED_VARIABLE")
34+        drawerLayout = binding.drawerLayout

36-        NavigationUI.setupActionBarWithNavController(this, navController)
36+        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

37+        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

38+        NavigationUI.setupWithNavController(binding.navView, navController)

43-        return navController.navigateUp()
43+        return NavigationUI.navigateUp(navController, appBarConfiguration)


-- 10 Using Navigation Listeners

app/src/main/java/com/example/android/navigation/MainActivity.kt

38+        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
39+        }
23+import androidx.navigation.NavController
24+import androidx.navigation.NavDestination

41+            if (nd.id == nc.graph.startDestination) {
42+                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
43+            } else {
44+                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
45+            }


-- 11 Animation with Navigation

app/src/main/res/anim/fade_in.xml

<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha
        android:duration="@android:integer/config_mediumAnimTime"
        android:fromAlpha="0.0"
        android:toAlpha="1.0" />
</set>


app/src/main/res/anim/slide_in_left.xml

<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    
    <translate
        android:duration="@android:integer/config_shortAnimTime"
        android:fromXDelta="-100%"
        android:fromYDelta="0%"
        android:toXDelta="0%"
        android:toYDelta="0%" />
</set>


app/src/main/res/navigation/navigation.xml

15-            app:destination="@id/gameFragment" />
15+            app:destination="@id/gameFragment"
16+            app:enterAnim="@anim/slide_in_right"
17+            app:exitAnim="@anim/slide_out_left"
18+            app:popEnterAnim="@anim/slide_in_left"
19+            app:popExitAnim="@anim/slide_out_right" />

34+            app:enterAnim="@anim/slide_in_left"
35+            app:exitAnim="@anim/slide_out_right"
36+            app:popEnterAnim="@anim/slide_in_right"
37+            app:popExitAnim="@anim/slide_out_left"

46-        <action
47-            android:id="@+id/action_gameWonFragment_to_gameFragment"
48-            app:destination="@id/gameFragment"
49-            app:popUpTo="@+id/titleFragment"/>
52+        <action
53+            android:id="@+id/action_gameWonFragment_to_gameFragment"
54+            app:destination="@id/gameFragment"
55+            app:enterAnim="@anim/slide_in_right"
56+            app:exitAnim="@anim/slide_out_left"
57+            app:popEnterAnim="@anim/slide_in_left"
58+            app:popExitAnim="@anim/slide_out_right"
59+            app:popUpTo="@+id/titleFragment"/>

69+            app:enterAnim="@anim/slide_in_left"
70+            app:exitAnim="@anim/slide_out_right"
71+            app:popEnterAnim="@anim/slide_in_left"
72+            app:popExitAnim="@anim/slide_out_right"

29+            app:enterAnim="@anim/fade_in"
30+            app:exitAnim="@anim/fade_out"
31+            app:popEnterAnim="@anim/slide_in_left"
32+            app:popExitAnim="@anim/slide_out_right"
