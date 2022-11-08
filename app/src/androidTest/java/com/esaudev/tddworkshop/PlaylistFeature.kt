package com.esaudev.tddworkshop

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.esaudev.tddworkshop.ui.MainActivity
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule

class PlaylistFeature: BaseUITest() {

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlaylists() {

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

    @Test
    fun displayLoaderWhileFetchingPlaylists() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoaderWhenPlaylistsReady() {

        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailScreen() {
        onView(
            allOf(
                withId(R.id.playlistImage), isDescendantOfA(nChildOf(withId(R.id.playlistList), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.playlistDetailContainer)
    }
}