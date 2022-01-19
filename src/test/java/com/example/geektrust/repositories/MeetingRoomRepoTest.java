package com.example.geektrust.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.geektrust.entities.BufferTime;
import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.DataNotFoundException;
import com.example.geektrust.exceptions.TimeConflictException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MeetingRoomRepoTest {

    private MeetingRoomRepo meetingRoomRepo;

    @BeforeEach
    public void setup() throws TimeConflictException, ParseException {
        BufferTime bufferTime = new BufferTime();
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00"),
                new SimpleDateFormat("HH:mm").parse("09:15"));

        MeetingRoom meetingRoom = new MeetingRoom("Test-Room", 10, bufferTime);

        meetingRoomRepo = new MeetingRoomRepo();
        meetingRoomRepo.addMeetingRoom(meetingRoom);
    }

    @Test
    @DisplayName("Test - Get Meeting Room by Name - should Return MeetingRoom")
    public void getMeetingRoomByName_shouldReturnMeetingRoom()
            throws TimeConflictException, ParseException, DataNotFoundException
    {
        BufferTime bufferTime = new BufferTime();
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00"),
                new SimpleDateFormat("HH:mm").parse("09:15"));

        MeetingRoom expected = new MeetingRoom("Test-Room", 10, bufferTime);

        MeetingRoom actual = meetingRoomRepo.getRoomByName("Test-Room");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Get Meeting Room by Name - should Throw Exception")
    public void getMeetingRoomByName_shouldThrowException()
            throws TimeConflictException, ParseException, DataNotFoundException
    {

        Assertions.assertThrows(DataNotFoundException.class, () -> {meetingRoomRepo.getRoomByName("Test-Wrong-Room");});
    }

    @Test
    @DisplayName("Test - Get All Rooms - should Return List of Rooms")
    public void getAllRoomsTest_shouldReturnRoomsList() throws TimeConflictException, ParseException
    {
        BufferTime bufferTime = new BufferTime();
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00"),
                new SimpleDateFormat("HH:mm").parse("09:15"));

        MeetingRoom meetingRoom = new MeetingRoom("Test-Room-2", 20, bufferTime);
        meetingRoomRepo.addMeetingRoom(meetingRoom);

        List<MeetingRoom> roomsList = meetingRoomRepo.getAllRooms();

        Assertions.assertEquals(2, roomsList.size());
    }
}