package com.example.android.testing.blueprint.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.android.testing.blueprint.HelloTestingBlueprintActivity
import com.example.android.testing.blueprint.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * java.lang.IllegalStateException: This app has been built with an incorrect configuration.
 * Please configure your build for VectorDrawableCompat.
 */
@RunWith(AndroidJUnit4::class)
class AndroidTestOnlyDirectTest {

    @Suppress("MemberVisibilityCanPrivate") // ActivityTestRule needs to be public
    @get:Rule
    var activityRule = ActivityTestRule(HelloTestingBlueprintActivity::class.java)
    val activity by lazy { activityRule.activity }

    @Test
    fun verifyItsWorking() {
        onView(withId(R.id.btn_hello_android_testing)).perform(click())
        onView(withId(R.id.text_view_rocks))
            .check(matches(withText(activity.getString(R.string.android_testing_rocks))))
    }
}
