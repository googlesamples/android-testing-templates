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

package com.example.android.testing.blueprint.ui.espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.android.testing.blueprint.HelloTestingBlueprintActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * A test class that is only run against flavor2.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTestForFlavor2 {

  // Literal string instead of R.id.app_name because we want this test to only pass with flavor2.
  public static final String APP_NAME_FLAVOR2 = "AndroidTestingBlueprint Flavor2";

    @Rule
    public ActivityTestRule<HelloTestingBlueprintActivity> mActivityRule =
            new ActivityTestRule<>(HelloTestingBlueprintActivity.class);

    @Test
    public void findViewPerformActionAndCheckAssertion() {
        // Find Button and Click on it
      onView(withText(APP_NAME_FLAVOR2)).check(matches(isDisplayed()));
    }
}
