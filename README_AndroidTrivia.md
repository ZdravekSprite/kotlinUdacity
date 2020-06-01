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
