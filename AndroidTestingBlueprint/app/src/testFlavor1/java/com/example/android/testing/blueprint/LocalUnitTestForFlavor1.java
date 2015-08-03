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
package com.example.android.testing.blueprint;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * A test class with unit tests that are only executed against flavor1.
 */
public class LocalUnitTestForFlavor1 {

    @Test
    public void onlyExecutedWithFlavor1() {
        // Create an instance of a class only available in flavor1.
        Flavor1Class flavor1ClassInstance = new Flavor1Class();

        assertThat(flavor1ClassInstance.isReady(), is(true));
    }
}
