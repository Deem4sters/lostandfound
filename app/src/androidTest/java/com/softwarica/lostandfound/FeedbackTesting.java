package com.softwarica.lostandfound;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

public class FeedbackTesting {

    @Rule
    public ActivityTestRule<FeedbackActivity> testRule = new ActivityTestRule<>(FeedbackActivity.class);


    @Test
    public  void testContactTrip(){

        onView(withId(R.id.etfeedback)).perform(typeText("Testing"));

        onView(withId(R.id.btnsubmit)).perform(click());
        onView(withText("Error"))
                .inRoot(withDecorView(not(testRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}
