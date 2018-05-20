package com.udacity.gradle.builditbigger;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;


/**
 * Checks if progress bar is not displayed on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class CheckProgressBarTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Check that a progress bar is gone
     */
    @Test
    public void checkProgressBarIsGone() {
        // Check if a joke is displayed
        onView(withId(R.id.pb_wait_joke)).check(matches(not(isDisplayed())));
    }

}
