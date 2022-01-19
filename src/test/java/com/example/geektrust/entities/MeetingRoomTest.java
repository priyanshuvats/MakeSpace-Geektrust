package com.example.geektrust.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.exceptions.TimeConflictException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MeetingRoomTest {
    // check if a room is available at a given time range
    // check both buffer time and already scheduled meetings
    // schedule a meeting
    // throws exception if conflict with existing or buffer
    // throws exception if participants are more than capacity

    private MeetingRoom meetingRoom;

    @BeforeEach
    public void setup() throws TimeConflictException, ParseException {
        BufferTime bufferTime = new BufferTime();
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00"),
                new SimpleDateFormat("HH:mm").parse("09:15"));
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("13:15"),
                new SimpleDateFormat("HH:mm").parse("13:45"));
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("18:45"),
                new SimpleDateFormat("HH:mm").parse("19:00"));

        meetingRoom = new MeetingRoom("Test-Room", 10, bufferTime);
    }

    @Test
    @DisplayName("Test - Schedule Meeting - Should Schedule Meeting")
    public void scheduleMeetingTest_shouldScheduleMeeting() throws ParseException, RoomNotAvailableException
    {
        meetingRoom.scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        Meeting expected = new Meeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6, meetingRoom.getName());
        Meeting actual = meetingRoom.getScheduledMeetings().get(0);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Is Meeting Room Available - Should Return True")
    public void isMeetingRoomAvailableTest_shouldReturnTrue() throws ParseException
    {
        boolean expected = true;
        boolean actual = meetingRoom.isAvailable(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Is Meeting Room Available - Should Return False")
    public void isMeetingRoomAvailableTest_shouldReturnFalse() throws ParseException, RoomNotAvailableException
    {
        meetingRoom.scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);

        boolean expected = false;

        //Conflict with existing meetings
        boolean actual = meetingRoom.isAvailable(new SimpleDateFormat("HH:mm").parse("11:00") , new SimpleDateFormat("HH:mm").parse("11:30"));
        Assertions.assertEquals(expected, actual);

        actual = meetingRoom.isAvailable(new SimpleDateFormat("HH:mm").parse("11:30") , new SimpleDateFormat("HH:mm").parse("12:00"));
        Assertions.assertEquals(expected, actual);

        actual = meetingRoom.isAvailable(new SimpleDateFormat("HH:mm").parse("11:00") , new SimpleDateFormat("HH:mm").parse("12:00"));
        Assertions.assertEquals(expected, actual);

        //Conflict with bufferTime
        actual = meetingRoom.isAvailable(new SimpleDateFormat("HH:mm").parse("13:30") , new SimpleDateFormat("HH:mm").parse("14:30"));
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Test - Schedule Meeting - Should Throw Exception")
    public void scheduleMeetingTest_shouldThrowException() throws RoomNotAvailableException, ParseException
    {   
        meetingRoom.scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        
        //Time Conflict with existing meeting
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingRoom.scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:00") , new SimpleDateFormat("HH:mm").parse("11:30"), 6);});

        //Participants more than the capacity
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingRoom.scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:00") , new SimpleDateFormat("HH:mm").parse("11:30"), 11);});
    }

    @Test
    @DisplayName("Test- Get Name- should Return Name")
    public void getNameTest_shouldReturnName()
    {
        String expected = "Test-Room";
        String actual = meetingRoom.getName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test- Get Capacity- should Return Capacity")
    public void getCapacityTest_shouldReturnCapacity()
    {
        int expected = 10;
        int actual = meetingRoom.getCapacity();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test- Equals- should Return True")
    public void ewualsTest_shouldReturnTrue()
    {
        boolean expected = true;
        boolean actual = meetingRoom.equals(new MeetingRoom("Test-Room", 10));
        Assertions.assertEquals(expected, actual);
    }

}