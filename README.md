# Theta Plugin Kotlin Sample

## 1.Overview

This is a sample project of Theta plugin software implemented in **Kotlin**. 

## 2. Development Environment

- RICOH THETA V (Developer Mode)
- Android Studio 3.6.1

## 3. How To Use

1. Open this project on Android studio.
2. Build and Download to Theta V to start plugin application
3. When you press the camera button, Rec-LED will blink start and end
4. You can see main window showing the button press counter by using Vysor

##  4. How to create this project, Step by Step

### 4.1 Create an Empty Project on Android Studio

- Open you android studio and select "Start a new Android Project"

- Chose "Empty Activity" and press "Next"
- Configure Your Project as example..
  - **Name:** Any
  - **Package Name:** Any
  - **Save location**: Any
  - **Language:**  Kotlin
  - **Minimum SDK:** API 25: Android 7.1.1 (Nougat)
  - **Use legacy android.support libraries:** NO CHECK

### 4.2 Build.gradle (Project)

Add the theta-plugin-library repository to 'allprojects/repositories' section.

```gradle
allprojects {
    repositories {
		...

        maven { url 'https://github.com/ricohapi/theta-plugin-library/raw/master/repository' }
    }
}
```

### 4.4 Build.grade (Module: app)

Add download 'com.theta.pluginlibrary' to 'dependencies' section.

 ```gradle
dependencies {
	...

    implementation 'com.theta360:pluginlibrary:2.1.0'
}
 ```



### 4.3 AndroidManifest.xml

- Add user-feature (3 lines) to use theta hardware
- Add users-permission (6 lines) to request permission
- change 'android:allowBackup' to "false" 

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="io.github.otama75.thetaKotlin">

    <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-feature android:glEsVersion="0x00020000" android:required="true" />

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />

    <application
        android:allowBackup="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```



### 4.5 MainActivity.kt

MainActivity class is written in Kotlin derived from PluginActivity (JAVA) class

```kotlin
package io.github.otama75.thetaKotlin

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.theta360.pluginlibrary.activity.PluginActivity
import com.theta360.pluginlibrary.callback.KeyCallback
import com.theta360.pluginlibrary.receiver.KeyReceiver
import com.theta360.pluginlibrary.values.LedColor
import com.theta360.pluginlibrary.values.LedTarget
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PluginActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Theta Plugin Initialization
        notificationCameraClose()

        // Start Camera LED blinking
        notificationLedBlink(LedTarget.LED7, LedColor.BLUE, 300)

        // Setting Key Callback
        class KotlinKeyCallback : KeyCallback {
            override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
                if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                    if (count % 2 == 0) {
                        notificationLedBlink(LedTarget.LED7, LedColor.BLUE, 300)
                    } else {
                        notificationLedHide(LedTarget.LED7)
                    }
                    val message = "Camera button pushed ${count++} times"
                    tvCounter.text = message
                    Log.d(TAG, message)
                }
            }
            override fun onKeyUp(p0: Int, p1: KeyEvent?) {}
            override fun onKeyLongPress(p0: Int, p1: KeyEvent?) {}
        }
        setKeyCallback(KotlinKeyCallback())
    }
}
```



### 4.6 Activity_main.xml

Just put 'android:id' to TextView (line 11)

You can access textView 'tvCounter' from MainActivity without writing findViewById().

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

