package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will check that joke is displayed on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class ShowJokeTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, true);


    /*
    Registers resource that needs to be synchronized with Espresso before the test is run.
     */
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mMainActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);

    }


    /**
     * Check that a joke is displayed after click on the button
     */
    @Test
    public void clickOnTellJokeButton_checkJokeIsNotEmpty() {

        // Click the "Tell Joke" button
        onView(withId(R.id.btn_tell_joke)).perform(click());

        // Check if a joke is displayed
        onView(withId(R.id.tv_joke)).check(matches(not(withText(""))));

    }


    /**
     * Check that a progress bar is gone
     * */
    @Test
    public void checkProgressBarIsGone() {

       // Check if a joke is displayed
        onView(withId(R.id.pb_wait_joke)).check(matches(not(isDisplayed())));

    }

    /*
     Unregister resources when not needed to avoid malfunction.
     */
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().register(mIdlingResource);
        }
    }
}
