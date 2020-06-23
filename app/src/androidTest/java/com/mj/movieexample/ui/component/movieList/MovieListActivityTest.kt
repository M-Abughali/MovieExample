package com.mj.movieexample.ui.component.movieList

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.mj.movieexample.R
import org.junit.Rule
import org.junit.Test

class MovieListActivityTest {

    @JvmField @Rule
    var activityScenarioRule= ActivityTestRule(
        MovieListActivity::class.java
    )

    @Test
    fun checkRecyclerViewVisibility() {
        Espresso.onView(withId(R.id.rvMovies))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickOnItem() {
        Espresso.onView(withId(R.id.rvMovies))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    15,
                    ViewActions.click()
                )
            )
        pauseTestFor(2000)
    }

    @Test
    fun scrollToNextPage() {
        val recyclerView: RecyclerView =
            activityScenarioRule.activity.findViewById(R.id.rvMovies)
        val itemCount = recyclerView.adapter!!.itemCount
        Espresso.onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount))
        pauseTestFor(4000)
    }

    @Test
    fun checkSearch() {
//        Espresso.onView(withId(R.id.txtSearch))
//            .perform(ViewActions.clearText(), ViewActions.typeText("Love"))
//        pauseTestFor(2000)
    }

    private fun pauseTestFor(milliseconds: Long) {
        try {
            Thread.sleep(milliseconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
