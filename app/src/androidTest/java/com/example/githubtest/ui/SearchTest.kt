package com.example.githubtest.ui


import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import com.example.githubtest.R
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testStart() {
        onView(withId(R.id.tvEmpty)).check(matches(isDisplayed()))
    }

    @Test
    fun testAddingToResentRepositories() {
        try {
            onView(withId(R.id.btnLogin)).perform(click())
        } catch (_: NoMatchingViewException) {
        }
        onView(withId(R.id.etSearch)).perform(replaceText(SEARCH_TEXT), closeSoftKeyboard())

        Thread.sleep(5000L)

        onView(withId(R.id.rvRepositories))
            .perform(RecyclerViewActions.scrollTo<ViewHolder>(hasDescendant(withText(REPOSITORY))))
        onView(withText(REPOSITORY)).perform(click())

        UiDevice.getInstance(getInstrumentation()).pressBack()

        onView(withId(R.id.fbHistory)).perform(click())

        onView(withText(REPOSITORY)).check(matches(withText(REPOSITORY)))
    }

    companion object {
        private const val SEARCH_TEXT = "android"
        private const val REPOSITORY = "bumptech/glide"
    }
}
