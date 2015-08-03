/*
 * Copyright 2015, The Android Open Source Project
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

package com.example.android.testing.blueprint.ui.uiautomator;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.android.testing.blueprint.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@LargeTest
public class UiAutomatorTest {

    /**
     * The target app package.
     */
    private static final String TARGET_PACKAGE =
            InstrumentationRegistry.getTargetContext().getPackageName();

    /**
     * The timeout to start the target app.
     */
    private static final int LAUNCH_TIMEOUT = 5000;

    private UiDevice mDevice;

    /**
     * When using UiAutomator you can interact with ui elements outside of your own process.
     * You can start your application by pressing the home button of the device and launch the
     * target app through the android launcher.
     *
     * <p>
     * If you only want to launch and test a single {@link Activity} you can use {@link
     * Instrumentation} directly to only launch the target {@link Activity}
     * </p>
     */
    @Before
    public void startBlueprintActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(TARGET_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(TARGET_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mDevice, notNullValue());
    }

    @Test
    public void findViewPerformActionAndCheckAssertion() {
        // Click on Button with content description
        final String btnContentDescription = InstrumentationRegistry.getTargetContext()
                .getString(R.string.content_desc_hello_android_testing);
        mDevice.findObject(By.desc(btnContentDescription)).click();

        // Verify that correct text is displayed
        final String textViewResId = "text_view_rocks";
        UiObject2 androidRocksTextView = mDevice
                .wait(Until.findObject(By.res(TARGET_PACKAGE, textViewResId)),
                        500 /* wait 500ms */);
        assertThat(androidRocksTextView, notNullValue());

        final String androidTestingRocksText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.android_testing_rocks);
        assertThat(androidRocksTextView.getText(), is(equalTo(androidTestingRocksText)));
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

}
