package com.user.mystudio;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
/**
 * Created by Edwin Dwi Ahmad on 09-Oct-17.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.DEFAULT)

public class BookingPhotoTest {
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
    public void testBookingNoDate(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testBookingNoTime(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("12/10/2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testBookingNoAlamatLain(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("12/10/2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioLain)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_alamat)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_alamat)).check(matches(hasErrorText("This Field is Required")));
    }

    @Test
    public void testBookingDateSalah(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("2-10-2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah2(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("002-10-2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah3(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("31/10/2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Maksimal tanggal 30")));
    }

    @Test
    public void testBookingDateSalah4(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/13/2017"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Maksimal bulan adalah 12")));
    }

    @Test
    public void testBookingDateSalah20(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2019"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Maksimal tahun adalah 2018")));
    }

    @Test
    public void testBookingDateSalah5(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30+11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah6(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30*11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah7(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30-11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah8(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30.11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah9(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30&11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah10(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30:11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah11(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30%11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah12(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30$11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah13(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30#11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah14(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30@11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah15(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30!11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah17(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30`11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah18(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30~11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah19(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30;11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah21(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11+2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah22(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11*2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah23(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11-2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah24(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11.2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah25(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11&2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah26(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11:2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah27(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11%2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah28(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11$2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah29(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11#2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah30(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11@2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah31(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11!2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah32(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11`2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah33(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11~2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingDateSalah34(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11;2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("10:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_date)).check(matches(hasErrorText("Isi sesuai Format dd/mm/yyyy")));
    }

    @Test
    public void testBookingTimeSalah1(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("-2:00"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Jam salah")));
    }

    @Test
    public void testBookingTimeSalah2(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("24:02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Jam salah")));
    }

    @Test
    public void testBookingTimeSalah3(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21:-2"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Jam salah")));
    }

    @Test
    public void testBookingTimeSalah4(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21:72"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Jam salah")));
    }

    @Test
    public void testBookingTimeSalah5(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21`02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah6(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21~02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah7(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21!02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah8(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21@02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah9(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21#02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah10(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21$02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah11(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21%02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah12(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21^02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah13(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21&02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah14(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21*02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah15(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21(02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah16(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21)02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah17(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21-02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah18(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21_02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah19(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21+02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah20(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21=02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah21(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21[02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah22(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21]02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah23(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21{02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah24(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21}02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah25(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21;02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah26(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21'02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah27(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21,02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah28(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21.02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah29(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21/02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah30(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21<02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah31(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21>02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }

    @Test
    public void testBookingTimeSalah32(){
        bookingPhotoActivityTestRule.launchActivity(null);
        onView(withId(R.id.booking_date)).perform(typeText("30/11/2018"), closeSoftKeyboard());
        onView(withId(R.id.booking_time)).perform(typeText("21?02"), closeSoftKeyboard());
        onView(withId(R.id.radioStudio)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.booking_now)).perform(click());
        pauseTestFor(1000);
        onView(withId(R.id.booking_time)).check(matches(hasErrorText("Format jam salah")));
    }
}