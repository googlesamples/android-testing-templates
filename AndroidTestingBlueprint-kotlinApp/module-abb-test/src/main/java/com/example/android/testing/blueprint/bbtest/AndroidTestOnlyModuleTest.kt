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

package com.example.android.testing.blueprint.bbtest

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A simple integration test which checks the validity of a string loaded from resources.
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestOnlyModuleTest {

    companion object {
        const val APP_PACKAGE = "com.example.android.testing.blueprint"
    }

    @Test fun verifyItsWorking() {
        val intent = Intent(Intent.ACTION_VIEW)
            .setClassName(
                ApplicationProvider.getApplicationContext<Context>(),
                "$APP_PACKAGE.HelloTestingBlueprintActivity"
            )

        launchActivity<Activity>(intent).onActivity { activity ->
            onView(withText("Android Testing!")).perform(click())
            onView(withId(activity.resources.getIdentifier("text_view_rocks", "id", activity.packageName)))
                .check(ViewAssertions.matches(withText("Rocks")))
        }
    }
}
