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

package com.example.android.testing.blueprint.integration

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.runner.AndroidJUnit4
import com.example.android.testing.blueprint.R
import com.example.android.testing.blueprint.androidlibrarymodule.AndroidLibraryModuleClass
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Android test to showcase the usage of an Android Library from the main app module.
 */
@RunWith(AndroidJUnit4::class)
class AndroidLibraryModuleIntegrationTest {

    private lateinit var context: Context

    /**
     * This test class showcases passing arguments from the command line to the instrumentation.
     *
     * Every @Test will fail if the argument "argument1" has the value "make_test_fail". See README
     * for more information.
     */
    @Before fun checkCustomArgument() {
        // Get the arguments bundle.
        val arguments = InstrumentationRegistry.getArguments()

        // Get the value if "argument1" exists
        val argument1 = arguments.getString("argument1")

        // Do something with the value. In this example it will make the test fail but it can be
        // used to modify a value in SharedPreferences or set the hostname of a backend server,
        // for example.
        assertThat(argument1, not(equalTo("make_test_fail")))
    }

    @Before fun initTargetContext() {
        context = getTargetContext()
    }

    @Test fun verifyResourceFromLibrary() {
        assertEquals(context.getString(R.string.library_module_hello_world),
                "Hello from an Android library module!")
    }

    @Test fun verifyClassFromLibrary() {
        val libraryModuleInstance = AndroidLibraryModuleClass()
        assertTrue(libraryModuleInstance.isReady())
    }
}