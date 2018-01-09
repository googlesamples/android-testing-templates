/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.blueprint.ui.uiautomator

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SdkSuppress
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import com.example.android.testing.blueprint.R
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

/**
 * The UiAutomator library provides a convenient API for writing system-level UI tests.
 *
 * With UiAutomator you can easily interact with any visible element regardless of its process
 * You can look up elements with convenient descriptors such as text value,
 * content description, class, or a variety of other attributes.
 *
 * Note, that UiAutomator tests are only supported on an api level > 18, hence they are suppressed
 * on lower API levels.
 */
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UiAutomatorTest {

    private val LAUNCH_TIMEOUT = 5000
    private val TARGET_PACKAGE = InstrumentationRegistry.getTargetContext().packageName
    private lateinit var device: UiDevice

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.
     */
    private fun getLauncherPackageName(): String {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = InstrumentationRegistry.getContext().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }

    /**
     * When using UiAutomator you can interact with ui elements outside of your own process.
     * You can start your application by pressing the home button of the device and launch the
     * target app through the android launcher.
     *
     * If you only want to launch and test a single [Activity] you can use [InstrumentationRegistry]
     * directly to only launch the target [Activity]
     */
    @Before fun startBlueprintActivityFromHomeScreen() {
        // Initialize UiDevice instance and start from the home screen
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).apply {
            pressHome()
        }

        // Wait for launcher
        val launcherPackage = getLauncherPackageName()
        assertThat(launcherPackage, notNullValue())
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT.toLong())

        // Launch the blueprint app
        val context = InstrumentationRegistry.getContext()
        val intent = context.packageManager.getLaunchIntentForPackage(TARGET_PACKAGE).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)    // Clear out any previous instances
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(TARGET_PACKAGE).depth(0)), LAUNCH_TIMEOUT.toLong())
    }

    @Test fun checkPreconditions() = assertThat<UiDevice>(device, notNullValue())

    @Test fun findViewPerformActionAndCheckAssertion() {
        // Click on Button with content description
        val btnContentDescription = InstrumentationRegistry.getTargetContext()
                .getString(R.string.content_desc_hello_android_testing)
        device.findObject(By.desc(btnContentDescription)).click()

        // Verify that correct text is displayed
        val textViewResId = "text_view_rocks"
        val androidRocksTextView = device.wait(Until.findObject(
                By.res(TARGET_PACKAGE, textViewResId)), TimeUnit.SECONDS.toMillis(0.5.toLong()))
        assertThat(androidRocksTextView, notNullValue())

        val androidTestingRocksText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.android_testing_rocks)
        assertEquals(androidRocksTextView.text, androidTestingRocksText)
    }

}
