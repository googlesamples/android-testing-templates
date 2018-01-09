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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import com.example.android.testing.blueprint.androidlibrarymodule.AndroidLibraryModuleClass
import com.example.android.testing.blueprint.plainkotlinmodule.MyPlainKotlinClass

/**
 * A simple [AppCompatActivity] that shows a text when a button is pressed.
 *
 * Also, it has an instance of a class from a different module.
 */
class HelloTestingBlueprintActivity : AppCompatActivity() {

    private lateinit var androidTestingRocksTextView: TextView

    // Instance of a class in a different Java module.
    private lateinit var plainKotlinClassInstance: MyPlainKotlinClass

    // Instance of a class in an android library module.
    private lateinit var androidLibraryClassInstance: AndroidLibraryModuleClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_testing_blueprint)
        androidTestingRocksTextView = findViewById(R.id.text_view_rocks)

        plainKotlinClassInstance = MyPlainKotlinClass()
        androidLibraryClassInstance = AndroidLibraryModuleClass()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onClick(view: View) {
        if (plainKotlinClassInstance.isReady() && androidLibraryClassInstance.isReady()) {
            androidTestingRocksTextView.text = getString(R.string.android_testing_rocks)
        }
    }
}
