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

package com.example.android.testing.blueprint.test;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.example.android.testing.blueprint.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A simple integration test which checks the validity of a string loaded from resources.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class AndroidTestOnlyModuleTest {

    private Context mContext;

    @Before
    public void initTargetContext() {
        // Obtain the target context from InstrumentationRegistry
        mContext = getTargetContext();
        assertThat(mContext, notNullValue());
    }

    @Test
    public void verifyResourceString() {
        assertThat(mContext.getString(R.string.hello_from_the_test_only_module),
                is(equalTo("Hello from the test only module!")));
    }

}
