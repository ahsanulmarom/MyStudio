package com.user.mystudio;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
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

    @Test
    public void testSignUp() {
        signUpActivityTestRule.launchActivity(null);
        onView(withId(R.id.signup_username)).perform(typeText("Agus Testing Espresso"), closeSoftKeyboard());
        onView(withId(R.id.signup_email)).perform(typeText("espressoTest@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signup_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_repassword)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signup_submit)).perform(click());
        pauseTestFor(2500);
        onView(withText("Sign up Successfully. Please check email to verify account!"))
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
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
                .inRoot(withDecorView(Matchers.not(Matchers.is(signUpActivityTestRule.getActivity().getWindow().getDecorView()))))
                .perform(click());
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