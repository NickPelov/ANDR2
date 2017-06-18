package com.example.owner.android2;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.owner.android2.Activities.LoginActivity;
import com.example.owner.android2.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LogInTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void log_in() {
        //filling the email, password and pressing the button for logging in
        onView(withId(R.id.login_button_main)).perform(click());
        onView(withId(R.id.email_login_form)).check(matches(isDisplayed()));

        onView(withId(R.id.email))
                .perform(typeText("admin@"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("ad"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withText("Nick Name")).check(matches(isDisplayed()));
    }
}
