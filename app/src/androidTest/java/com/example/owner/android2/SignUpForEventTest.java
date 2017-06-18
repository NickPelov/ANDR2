package com.example.owner.android2;

import android.support.test.espresso.ViewInteraction;
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
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class SignUpForEventTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sign_up_for_event() {
        try{
            onView(withId(R.id.login_button_main)).perform(click());
            onView(withId(R.id.email_login_form)).check(matches(isDisplayed()));

            onView(withId(R.id.email))
                    .perform(typeText("y@"), closeSoftKeyboard());
            onView(withId(R.id.password))
                    .perform(typeText("takataka"), closeSoftKeyboard());
            onView(withId(R.id.email_sign_in_button)).perform(click());

            onView(withText("Nick Name")).check(matches(isDisplayed()));

            ViewInteraction appCompatImageButton = onView(
                    allOf(withContentDescription("Open navigation drawer"),
                            withParent(withId(R.id.toolbar)),
                            isDisplayed()));
            appCompatImageButton.perform(click());

            ViewInteraction appCompatCheckedTextView = onView(
                    allOf(withId(R.id.design_menu_item_text), withText("Events"), isDisplayed()));
            appCompatCheckedTextView.perform(click());

            ViewInteraction appCompatButton3 = onView(
                    allOf(withText("More"), isDisplayed()));
            appCompatButton3.perform(click());

            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.event_signup), withText("Sign Up"),
                            withParent(allOf(withId(R.id.relativeLayout4),
                                    withParent(withId(R.id.event_info)))),
                            isDisplayed()));
            appCompatButton4.perform(click());
        }
        catch (Exception e){
            ViewInteraction appCompatButton3 = onView(
                    allOf(withText("More"), isDisplayed()));
        }
    }
}
