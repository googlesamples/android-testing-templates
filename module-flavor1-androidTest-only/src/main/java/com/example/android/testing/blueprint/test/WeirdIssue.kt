package com.example.android.testing.blueprint.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

/**
 * https://issuetracker.google.com/issues/129421689
 *
 * this is because AppCompatDrawableManager decodes a VectorDrawable
 * to verify VectorDrawableCompat is setup correctly. That check fails,
 * and the reason is that decoding the test resource (androidx.appcompat.R.drawable.abc_vector_test)
 * results in a *completely* different resource being loaded - abc_textfield_search_material,
 * a StateListDrawable
 */
@RunWith(AndroidJUnit4::class)
class WeirdIssue {

    @Test
    fun testError() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val d = context.getDrawable(androidx.appcompat.R.drawable.abc_vector_test)
        assertFalse(d == null)
        // wrong resource is decoded, a NinePatchDrawable is returned
        assertEquals("android.graphics.drawable.VectorDrawable", d!!.javaClass.name)
    }
}
