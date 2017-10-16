package com.user.mystudio;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

/**
 * Created by Edwin Dwi Ahmad on 12-Oct-17.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class ScheduleTest {
    @Rule
    public ActivityTestRule<Login> loginActivityTestRule = new ActivityTestRule<>(Login.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void nullSchedule() {
        loginActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom.amamam@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withId(R.id.menu_booking)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(5000);
        onView(withText("No Schedule")).check(matches(isDisplayed()));
        pauseTestFor(1000);
        onView(withText("Booking Photo")).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(1000);
        onView(withText("Cancel")).perform(click());
        pauseTestFor(1000);
    }

    @Test
    public void testScheduleList1() {
        loginActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withId(R.id.menu_booking)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(5000);
        onData(anything()).inAdapterView(withId(R.id.sch_listView)).atPosition(0).perform(click());
        pauseTestFor(1000);
        onView(withText("My Detail Schedule")).check(matches(isDisplayed()));
        onView(withText("Cancel Booking")).check(matches(isDisplayed()));
        pauseTestFor(1000);
    }

    @Test
    public void testScheduleList2() {
        loginActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withId(R.id.menu_booking)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(5000);
        onData(anything()).inAdapterView(withId(R.id.sch_listView)).atPosition(0).perform(click());
        pauseTestFor(1000);
        onView(withText("My Detail Schedule")).check(matches(isDisplayed()));
        onView(withText("Close")).check(matches(isDisplayed()));
        pauseTestFor(1000);
    }

    @Test
    public void testScheduleList3() {
        loginActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withId(R.id.menu_booking)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(5000);
        onData(anything()).inAdapterView(withId(R.id.sch_listView)).atPosition(1).perform(click());
        pauseTestFor(1000);
        onView(withText("My Detail Schedule")).check(matches(isDisplayed()));
        onView(withText("Close")).check(matches(isDisplayed()));
        pauseTestFor(1000);
    }

    @Test
    public void testScheduleList4() {
        loginActivityTestRule.launchActivity(null);
        onView(withId(R.id.login_email)).perform(typeText("ahsanulmarom@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btnLogin)).perform(click());
        pauseTestFor(7000);
        onView(withId(R.id.menu_booking)).perform(click());
        pauseTestFor(500);
        onView(withId(R.id.booking_schedule)).perform(click());
        pauseTestFor(5000);
        onData(anything()).inAdapterView(withId(R.id.sch_listView)).atPosition(2).perform(click());
        pauseTestFor(1000);
        onView(withText("My Detail Schedule")).check(matches(isDisplayed()));
        onView(withText("Close")).check(matches(isDisplayed()));
        pauseTestFor(1000);
    }
}
