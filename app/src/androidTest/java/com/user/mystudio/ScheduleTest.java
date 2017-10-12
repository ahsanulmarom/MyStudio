package com.user.mystudio;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Created by Edwin Dwi Ahmad on 12-Oct-17.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)
public class ScheduleTest {
    @Rule
    public ActivityTestRule<Login> scheduleActivityActivityTestRule = new ActivityTestRule<>(Schedule.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
