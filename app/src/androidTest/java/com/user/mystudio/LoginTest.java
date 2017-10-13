package com.user.mystudio;

import android.content.Context;
import android.net.ConnectivityManager;
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

import java.lang.reflect.Method;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
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

    private void enableData(Context context, boolean enable) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Method m = cm.getClass().getDeclaredMethod("setMobileDataEnabled", boolean.class);
            m.invoke(cm, enable);
        } catch (Exception ignored) {
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

/*    @Test
    public void testNoConnection() {
        enableData(loginActivityActivityTestRule.getActivity().getApplicationContext(), false);
        pauseTestFor(5000);
        loginActivityActivityTestRule.launchActivity(null);
        pauseTestFor(2000);
        onView(withText("You are not connected internet. Please check your connection!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
        enableData(loginActivityActivityTestRule.getActivity().getApplicationContext(), true);
        pauseTestFor(5000);
    }

    @Test
    public void testLoginNoConnection() {
        enableData(loginActivityActivityTestRule.getActivity().getApplicationContext(), false);
        pauseTestFor(5000);
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(2000);
        onView(withText("You are not connected internet. Please check your connection!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
        enableData(loginActivityActivityTestRule.getActivity().getApplicationContext(), true);
        pauseTestFor(5000);
    }*/

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
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
    }

    @Test
    public void testLogin() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withText("You are logged in as ahsanulmarom@gmail.com"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
        pauseTestFor(7000);
        intended(hasComponent(MenuAct.class.getName()));
        pauseTestFor(4000);
    }

    @Test
    public void testLoginNoVerify() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsan@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withText("You are logged in as ahsanulmarom@gmail.com"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
    }
}