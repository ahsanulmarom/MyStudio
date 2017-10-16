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
public class InjectionTest {
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


    @Test
    public void testInjection1() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("2 or 1=1"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.login_password)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testInjection2() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText(""or""=""), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(""or""=""), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(2500);
        onView(withText("Sorry, Your Email or Password is Incorrect. Please try again!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(loginActivityActivityTestRule.getActivity().getWindow().getDecorView()))));
    }

   @Test
    public void testInjection3() {
        loginActivityActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("2; INSERT TABLE Injection"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.login_password)).check(matches(hasErrorText("This Field is Required")));
    }
    
}
