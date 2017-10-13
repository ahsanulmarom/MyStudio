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
public class TestNoLoginBookingPhoto {
    @Rule
    public ActivityTestRule<BookingPhoto> bookingPhotoActivityTestRule = new ActivityTestRule<>(BookingPhoto.class, true, false);

    private void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testButtonSchedule() {
        bookingPhotoActivityTestRule.launchActivity(null);
        pauseTestFor(1000);
        intended(hasComponent(Login.class.getName()));
    }


}