package com.esaudev.tddworkshop

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.esaudev.tddworkshop.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlaylists() {
        Thread.sleep(4000)

        assertRecyclerViewItemCount(R.id.playlistList, 10)

        onView(
            allOf(
                withId(R.id.playlistName), isDescendantOfA(nChildOf(withId(R.id.playlistList), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlistCategory), isDescendantOfA(nChildOf(withId(R.id.playlistList), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlistImage), isDescendantOfA(nChildOf(withId(R.id.playlistList), 0))
            )
        )
            .check(matches(withDrawable(R.drawable.asset_playlist_default)))
            .check(matches(isDisplayed()))
    }

    /**
        Passing a parent view and then we are accessing the selected child position
     */
    private fun nChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}