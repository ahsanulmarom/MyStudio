package com.user.mystudio;

import android.widget.ListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 01/10/2017.
 */
public class ScheduleTest {
    private Schedule schedule;
    private String key, date, time, lokasi, pemesan, status;
    private List<HashMap<String, Object>> fillMaps = new ArrayList<>();
    private ListView lv;

    @Before
    public void setUp() throws Exception {
        schedule = new Schedule();
        key = "12345";
        date = "12-12-2017";
        time = "12:00";
        lokasi = "Rumah";
        pemesan = "X";
        status = "Waiting";
    }

    @Test
    public void loadScheduleTest() {
/*        schedule.loadSchedule(key, date, time, lokasi, pemesan, status);
        String result = String.valueOf(fillMaps.get(0).get("key"));
        Assert.assertEquals(result, key);*/
    }

    @After
    public void tearDown() throws Exception {
        schedule = null;
    }

}