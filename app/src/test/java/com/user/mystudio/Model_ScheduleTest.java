package com.user.mystudio;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Model_ScheduleTest {
    private Model_Schedule modelSchedule = null;


    @Before
    public void setUp() throws Exception {
        modelSchedule = new Model_Schedule("10-10-2017","10:00","Studio","MrX","X");
    }

    @After
    public void tearDown() throws Exception {
        modelSchedule = null;
    }

    @Test
    public void getDate() throws Exception {
        String result = modelSchedule.getDate();
        Assert.assertEquals(result,"10-10-2017");
        Assert.assertNotEquals(result, "29-04-2018");
        Assert.assertNotNull(result);
    }

    @Test
    public void setDate() throws Exception {
        modelSchedule.setDate("29-04-2018");
        String result = modelSchedule.getDate();
        Assert.assertEquals(result,"29-04-2018");
        Assert.assertNotEquals(result, "10-10-2017");
        Assert.assertNotNull(result);
    }

    @Test
    public void getTime() throws Exception {
        String result = modelSchedule.getTime();
        Assert.assertEquals(result,"10:00");
        Assert.assertNotEquals(result, "12:00");
        Assert.assertNotNull(result);
    }

    @Test
    public void setTime() throws Exception {
        modelSchedule.setTime("12:00");
        String result = modelSchedule.getTime();
        Assert.assertEquals(result,"12:00");
        Assert.assertNotEquals(result, "10:00");
        Assert.assertNotNull(result);
    }

    @Test
    public void getLokasi() throws Exception {
        String result = modelSchedule.getLokasi();
        Assert.assertEquals(result,"Studio");
        Assert.assertNotEquals(result, "Rumah");
        Assert.assertNotNull(result);
    }

    @Test
    public void setLokasi() throws Exception {
        modelSchedule.setLokasi("Rumah");
        String result = modelSchedule.getLokasi();
        Assert.assertEquals(result,"Rumah");
        Assert.assertNotEquals(result, "Studio");
        Assert.assertNotNull(result);
    }

    @Test
    public void getPemesan() throws Exception {
        String result = modelSchedule.getPemesan();
        Assert.assertEquals(result,"MrX");
        Assert.assertNotEquals(result, "Aku");
        Assert.assertNotNull(result);
    }

    @Test
    public void setPemesan() throws Exception {
        modelSchedule.setPemesan("Aku");
        String result = modelSchedule.getPemesan();
        Assert.assertEquals(result,"Aku");
        Assert.assertNotEquals(result, "MrX");
        Assert.assertNotNull(result);
    }

    @Test
    public void getStatus() throws Exception {
        String result = modelSchedule.getStatus();
        Assert.assertEquals(result,"X");
        Assert.assertNotEquals(result, "Waiting");
        Assert.assertNotNull(result);
    }

    @Test
    public void setStatus() throws Exception {
        modelSchedule.setStatus("Waiting");
        String result = modelSchedule.getStatus();
        Assert.assertEquals(result,"Waiting");
        Assert.assertNotEquals(result, "X");
        Assert.assertNotNull(result);
    }

}