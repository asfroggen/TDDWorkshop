package com.esaudev.tddworkshop

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import org.hamcrest.Matchers
import org.junit.Test

class PlaylistDetailFeature: BaseUITest() {

    @Test
    fun displayPlaylistNameAndDetails() {
        navigateToPlaylistDetails()

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displayLoaderWhenFetchingDetails() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        Thread.sleep(2000)
        navigateToPlaylistDetails()

        assertDisplayed(R.id.detailsLoader)
    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetails()

        assertNotDisplayed(R.id.detailsLoader)
    }

    @Test
    fun displayErrorMessageWhenNetworkFails() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlistImage),
                ViewMatchers.isDescendantOfA(nChildOf(ViewMatchers.withId(R.id.playlistList), 1))
            )
        ).perform(click())

        assertDisplayed(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails() {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.playlistImage),
                ViewMatchers.isDescendantOfA(nChildOf(ViewMatchers.withId(R.id.playlistList), 0))
            )
        ).perform(click())
    }
}