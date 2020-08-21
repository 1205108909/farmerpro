package com.phenix.data;

import com.phenix.exception.IllegalDataException;
import com.phenix.farmer.TestUtil;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by LI JT on 2016/7/8.
 * Description:
 */
public class TimeSlotTest {
    private TimeSlot timeSlot;

    @Test
    public void testGetOffset() throws Exception {
        TimeSlot timeSlot = this.getTimeSlot();

        Assert.assertEquals(0, timeSlot.getOffset(0));
        Assert.assertEquals(0, timeSlot.getOffset(90000));
        Assert.assertEquals(0, timeSlot.getOffset(91500));
        Assert.assertEquals(1, timeSlot.getOffset(91501));
        Assert.assertEquals(599, timeSlot.getOffset(92500));
        Assert.assertEquals(599, timeSlot.getOffset(92501));
        Assert.assertEquals(600, timeSlot.getOffset(93000));
        Assert.assertEquals(7799, timeSlot.getOffset(115959));
        Assert.assertEquals(7799, timeSlot.getOffset(113000));
        Assert.assertEquals(7800, timeSlot.getOffset(130000));
        Assert.assertEquals(8401, timeSlot.getOffset(131001));
        Assert.assertEquals(14819, timeSlot.getOffset(145659));
        Assert.assertEquals(14820, timeSlot.getOffset(145700));
        Assert.assertEquals(14999, timeSlot.getOffset(145959));
        Assert.assertEquals(14999, timeSlot.getOffset(150000));
        Assert.assertEquals(14999, timeSlot.getOffset(150251));
    }

    @Test
    public void testGetStepInSecs() throws Exception {
        TimeSlot timeSlot = this.getTimeSlot();
        Assert.assertEquals(1, timeSlot.getStepInSecs());
    }

    private TimeSlot getTimeSlot() {
        if (this.timeSlot == null) {
            SessionGroup group = TestUtil.buildSessionGroup(Exchange.SZ);
            timeSlot = new TimeSlot();
            timeSlot.addTimeSlots(group);
        }
        return timeSlot;
    }
    
    @Test
    public void testValidateTradingSessions() {
        SessionGroup group = TestUtil.buildSessionGroupMissingSession();
        TimeSlot ts = new TimeSlot();
        boolean exceptionCaught = false;

        try {
            ts.addTimeSlots(group);
        } catch (IllegalDataException exception) {
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testValidateTradingSessionsInvalidStartEndTime() {
        SessionGroup group = TestUtil.buildSessionGroupInvalidStartEndTime(true);
        TimeSlot ts = new TimeSlot();
        boolean exceptionCaught = false;

        try {
            ts.addTimeSlots(group);
        } catch (IllegalDataException exception) {
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);

        group = TestUtil.buildSessionGroupInvalidStartEndTime(false);
        ts = new TimeSlot();
        exceptionCaught = false;

        try {
            ts.addTimeSlots(group);
        } catch (IllegalDataException exception) {
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);
    }

}