package com.jerryhanks.fairmoneytest.viewAssertion

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

class RecyclerViewItemCountAssertion : ViewAssertion {
    private val matcher: Matcher<Int>

    constructor(expectedCount: Int) {
        matcher = CoreMatchers.`is`(expectedCount)
    }

    constructor(matcher: Matcher<Int>) {
        this.matcher = matcher
    }

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter!!
        ViewMatchers.assertThat(adapter.itemCount, matcher)
    }
}