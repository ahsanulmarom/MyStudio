package com.user.mystudio;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by user on 01/10/2017.
 */
public class ScheduleTest {
    private Schedule schedule;

    @Before
    public void setUp() throws Exception {
        schedule = new Schedule();
    }

    @Test
    public void loadScheduleTest() {
        schedule.loadSchedule("aaaa","10/10/2017","10:00","Studio","MrX","waiting");
        String result = schedule.fillMaps.get(0).get("pemesan").toString();
        Assert.assertEquals("MrX", result);
    }

    @After
    public void tearDown() throws Exception {
        schedule = null;
    }

}