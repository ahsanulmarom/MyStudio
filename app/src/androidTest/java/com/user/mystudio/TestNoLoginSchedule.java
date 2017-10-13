package com.user.mystudio;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class TestNoLoginSchedule {
    @Rule
    public ActivityTestRule<Schedule> scheduleActivityTestRule = new ActivityTestRule<>(Schedule.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testButtonSchedule() {
        scheduleActivityTestRule.launchActivity(null);
        pauseTestFor(1000);
        intended(hasComponent(Login.class.getName()));
    }


}