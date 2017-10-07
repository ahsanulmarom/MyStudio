package com.user.mystudio;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuActTest {
    @Rule
    public IntentsTestRule<MenuAct> menuIntentsTestRule = new IntentsTestRule<>(MenuAct.class, true, false);

    @Test
    public void testLaunchButtonClick() {
        menuIntentsTestRule.launchActivity(null);
        onView(withId(R.id.menu_booking)).perform(click());
        intended(hasComponent(BookingPhoto.class.getName()));
        intended(hasComponent(MenuAct.class.getName()), times(0));
        pressBack();
    }
}