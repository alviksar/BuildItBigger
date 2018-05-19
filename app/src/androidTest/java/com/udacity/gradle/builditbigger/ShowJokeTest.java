package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Intent;
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
 *
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
//        mIdlingResource
//                = new CountingIdlingResource(MainActivity.MAIN_ACTIVITY_IDLING_RESOURCE_NAME);
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);

    }


    /*
     Check that an ingredients view is displayed after click on the recipe list
     */
    @Test
    public void clickOnTellJokeButton_checkJokeIsNotEmpty() {

        // Scroll to the position 1 and click on it.
        onView(withId(R.id.btn_tell_joke)).perform(click());

        // Check if next activity displayed
        onView(withId(R.id.tv_joke)).check(matches(not(withText(""))));

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
