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
public class SignUpTest {
    @Rule
    public ActivityTestRule<SignUp> signUpActivityTestRule = new ActivityTestRule<>(SignUp.class, true, false);

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
        enableData(signUpActivityTestRule.getActivity().getApplicationContext(), false);
        pauseTestFor(5000);
        signUpActivityTestRule.launchActivity(null);
        pauseTestFor(2000);
        onView(withText("You are not connected internet. Please check your connection!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))));
        enableData(signUpActivityTestRule.getActivity().getApplicationContext(), true);
        pauseTestFor(5000);
    }

    @Test
    public void testSignUpNoConnection() {
        enableData(signUpActivityTestRule.getActivity().getApplicationContext(), false);
        pauseTestFor(5000);
        String email = "espressoTest" + (int) (Math.random()*1000) +"@gmail.com";
        signUpActivityTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.signup_username)).perform(typeText("Espresso Testing"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(2000);
        onView(withText("You are not connected internet. Please check your connection!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))));
        enableData(signUpActivityTestRule.getActivity().getApplicationContext(), true);
        pauseTestFor(5000);
    }*/

    @Test
    public void testSignUp() {
        String email = "espressoTest" + (int) (Math.random()*1000) +"@gmail.com";
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Espresso Testing"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(5000);
        onView(withText("Sign up Successfully. Please check email to verify account!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))));
        pauseTestFor(3000);
        intended(hasComponent(Login.class.getName()));
        pauseTestFor(3000);
    }

    @Test
    public void testSignUpEmailRegistered() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Buat Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(2500);
        onView(withText("Failed to sign up. Email has been registered."))
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))));
        pauseTestFor(3000);
    }

    @Test
    public void testSignUpNoUsername() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_username)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testSignUpNoUsername5char() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("abc"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_username)).check(matches(hasErrorText("Name must be at least 5 characters")));
    }

    @Test
    public void testSignUpNoEmail() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_email)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testSignUpNoPassword() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_password)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testSignUpNoPassword5char() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_password)).check(matches(hasErrorText("Password must be at least 5 characters")));
    }

    @Test
    public void testSignUpWrongRepassword_1() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("asdfghjkl"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_repassword)).check(matches(hasErrorText("Please check your password!")));
    }

    @Test
    public void testSignUpWrongRepassword_2() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("testingEspresso@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123467890"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.signup_repassword)).check(matches(hasErrorText("Please check your password!")));
    }
}