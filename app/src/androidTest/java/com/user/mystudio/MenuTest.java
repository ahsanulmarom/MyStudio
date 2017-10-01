package com.user.mystudio;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by user on 30/09/2017.
 */
public class MenuTest {
    @Rule
    public ActivityTestRule<Menu> menuActivityTestRule = new ActivityTestRule<Menu>(Menu.class);
    private Menu menu = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(BookingPhoto.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        menu = menuActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchButtonClick() {
        assertNotNull(menu.findViewById(R.id.menu_booking));
        onView(withId(R.id.menu_booking)).perform(click());
        Activity BookingPhoto = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
        assertNotNull(BookingPhoto);
        BookingPhoto.finish();
    }

    @After
    public void tearDown() throws Exception {
        menu = null;
    }
}