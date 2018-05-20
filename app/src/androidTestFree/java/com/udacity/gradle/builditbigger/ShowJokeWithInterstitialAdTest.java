package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShowJokeWithInterstitialAdTest {

    private AdViewIdlingResource mAdResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mAdResource = new AdViewIdlingResource(mActivityTestRule.getActivity().getAdView());
       // Espresso.registerIdlingResources(mAdResource);
        IdlingRegistry.getInstance().register(mAdResource);
    }

    @Test
    public void clickOnTellJokeButton_checkJokeIsNotEmpty() {
        // Wait for ad to load
        mAdResource.setIsLoadingAd(true);

        // Confirm that banner ad appears
        onView(withId(R.id.adView)).check(matches(isDisplayed()));

        // Click show interstitial button
        ViewInteraction showInterstitialButton = onView(
                allOf(withId(R.id.btn_tell_joke),
                        withText(R.string.button_text),
                        isDisplayed()));
        showInterstitialButton.perform(click());

        // Click close interstitial button
        ViewInteraction closeInterstitialButton = onView(
                allOf(withContentDescription("Interstitial close button"), isDisplayed()));
        closeInterstitialButton.perform(click());

        //  Check if a joke is displayed
        onView(withId(R.id.tv_joke)).check(matches(not(withText(""))));
    }

    @After
    public void tearDown() {
        //  Espresso.unregisterIdlingResources(mAdResource);
        if (mAdResource != null) {
            IdlingRegistry.getInstance().register(mAdResource);
        }
    }

}