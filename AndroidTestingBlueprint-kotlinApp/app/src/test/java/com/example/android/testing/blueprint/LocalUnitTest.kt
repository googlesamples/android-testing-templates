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

package com.example.android.testing.blueprint

import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import java.io.File
import org.hamcrest.CoreMatchers.`is` as matchIs
import org.mockito.Mockito.`when` as mockWhen

/**
 * A test class with unit tests that mock classes from the Android framework.
 */
class LocalUnitTest {

    @Test fun mockFinalMethod() {
        val activity = mock(Activity::class.java)
        val app = mock(Application::class.java)
        mockWhen(activity.application).thenReturn(app)

        assertEquals(app, activity.application)

        verify(activity).application
        verifyNoMoreInteractions(activity)
    }

    @Test fun mockFinalClass() {
        val adapter = mock(BluetoothAdapter::class.java)
        mockWhen(adapter.isEnabled).thenReturn(true)

        assertTrue(adapter.isEnabled)

        verify(adapter).isEnabled
        verifyNoMoreInteractions(adapter)
    }

    @Test fun loadTestDataFromResources() {
        val helloBlueprintJson = File(javaClass.getResource("/helloBlueprint.json").path)
        assertThat(helloBlueprintJson, notNullValue())
    }

}
