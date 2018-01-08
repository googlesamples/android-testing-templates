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

package com.example.android.testing.blueprint.androidlibrarymodule

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The Android library module's own tests, independent from the main app.
 */
@RunWith(AndroidJUnit4::class)
class AndroidLibraryModuleTest {

    private lateinit var context: Context

    @Before fun initTargetContext() {
        context = InstrumentationRegistry.getTargetContext()
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