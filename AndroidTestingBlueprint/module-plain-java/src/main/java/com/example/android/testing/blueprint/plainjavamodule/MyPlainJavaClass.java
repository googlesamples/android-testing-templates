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
package com.example.android.testing.blueprint.plainjavamodule;

/**
 * This module has no dependencies with Android so we can test it in isolation and reuse it easily.
 */
public class MyPlainJavaClass {

  /**
   * A sample method.
   *
   * @return true no matter what
   */
  public boolean isReady() {
    return true;
  }
}
