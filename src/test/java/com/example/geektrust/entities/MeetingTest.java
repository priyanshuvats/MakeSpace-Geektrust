package com.example.geektrust.entities;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MeetingTest {

    private Meeting meeting;

    @BeforeEach
    public void setup() throws ParseException {
        
        meeting = new Meeting(new SimpleDateFormat("HH:mm").parse("09:00"), new SimpleDateFormat("HH:mm").parse("09:30"), 10 , "Test-Room");
    }

    @Test
    @DisplayName("Test-Get Start Time-Should Return Start Time")
    public void getStartTime_shouldReturnStartTime() throws ParseException
    {
        Date expected = new SimpleDateFormat("HH:mm").parse("09:00");
        Date actual = meeting.getStartTime();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @DisplayName("Test-Get Start Time-Should Return End Time")
    public void getStartTime_shouldReturnEndTime() throws ParseException
    {
        Date expected = new SimpleDateFormat("HH:mm").parse("09:30");
        Date actual = meeting.getEndTime();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test-Get Count Attendees-Should Count")
    public void getCount_shouldReturnCount() throws ParseException
    {
        int expected = 10;
        int actual = meeting.getCountAttendees();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test-Equals-Should Return True")
    public void equalsTest_shouldReturnTrue() throws ParseException
    {
        boolean expected = true;
        boolean actual = meeting.equals(new Meeting(new SimpleDateFormat("HH:mm").parse("09:00"), new SimpleDateFormat("HH:mm").parse("09:30"), 10 , "Test-Room"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test-Equals-Should Return False")
    public void equalsTest_shouldReturnFalse() throws ParseException
    {
        boolean expected = false;
        boolean actual = meeting.equals(new Meeting(new SimpleDateFormat("HH:mm").parse("08:00"), new SimpleDateFormat("HH:mm").parse("09:30"), 10 , "Test-Room"));
        Assertions.assertEquals(expected, actual);
    }
}