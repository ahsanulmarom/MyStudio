package com.user.mystudio;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {
    @Rule
    public ActivityTestRule<Login> loginActivityActivityTestRule = new ActivityTestRule<>(Login.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void testLoginNoEmail() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.login_email)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testLoginNoPassword() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.login_password)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testLoginFailed() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("testFailed@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("asdfghjkl"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(2500);
        onView(withText("Sorry, Your Email or Password is Incorrect. Please try again!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }

    @Test
    public void testLogin() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(2500);
        onView(withText("You are logged in as ahsanulmarom@gmail.com"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
    }
}