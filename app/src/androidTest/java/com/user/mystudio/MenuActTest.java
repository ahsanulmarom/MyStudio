package com.user.mystudio;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class MenuActTest {
    @Rule
    public IntentsTestRule<MenuAct> menuIntentsTestRule = new IntentsTestRule<>(MenuAct.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLaunchButtonClick() {
        menuIntentsTestRule.launchActivity(null);
        onView(withId(R.id.menu_booking)).perform(click());
        intended(hasComponent(BookingPhoto.class.getName()));
        intended(hasComponent(MenuAct.class.getName()), times(0));
        pressBack();
    }

    @Test
    public void testToolbar() {
        menuIntentsTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.drawer)).check(matches(isClosed(Gravity.LEFT))).perform(open());
    }

    @Test
    public void testToolbarMenu() {
        menuIntentsTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.drawer)).check(matches(isClosed(Gravity.LEFT))).perform(open());
        onView(withId(R.id.navigation_view)).perform(navigateTo(R.id.nav_home));
        pauseTestFor(2000);
        intended(hasComponent(MenuAct.class.getName()));
        pauseTestFor(1000);
    }

    @Test
    public void testToolbarBooking() {
        menuIntentsTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.drawer)).check(matches(isClosed(Gravity.LEFT))).perform(open());
        onView(withId(R.id.navigation_view)).perform(navigateTo(R.id.nav_booking));
        pauseTestFor(2000);
        intended(hasComponent(BookingPhoto.class.getName()));
        pauseTestFor(1000);
    }

    @Test
    public void testToolbarSchedule() {
        menuIntentsTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.drawer)).check(matches(isClosed(Gravity.LEFT))).perform(open());
        onView(withId(R.id.navigation_view)).perform(navigateTo(R.id.nav_schedule));
        pauseTestFor(2000);
        intended(hasComponent(Schedule.class.getName()));
        pauseTestFor(1000);
    }

    @Test
    public void testToolbarLogout() {
        menuIntentsTestRule.launchActivity(null);
        pauseTestFor(1000);
        onView(withId(R.id.drawer)).check(matches(isClosed(Gravity.LEFT))).perform(open());
        onView(withId(R.id.navigation_view)).perform(navigateTo(R.id.nav_logout));
        pauseTestFor(4000);
        intended(hasComponent(Login.class.getName()));
        pauseTestFor(1000);
    }
}