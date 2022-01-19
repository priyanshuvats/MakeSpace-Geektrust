package com.example.geektrust.services;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.repositories.MeetingRoomRepo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MeetingSchedulerServiceTest {
    // isCorrectTimeFormat - check with many variations
    // view available meeting room
    // throws exception on incorrect time format
    // throws exception if endTime<=startTime
    // throws exception if no room available due to conflict
    // bookMeetingRoom
    // throws exception on incorrect time format
    // throws exception if endTime<=startTime
    // throws exception if no room available due to conflict
    // throws exception if no room available for given capacity

    @Mock
    MeetingRoomRepo meetingRoomRepoMock;

    @InjectMocks
    MeetingSchedulerService meetingSchedulerService;

    @Test
    @DisplayName("Test - Is Correct Time Format - should Return False")
    public void isCorrectTimeFormatTest_shouldReturnFalse() {
        boolean expected = false;

        // minutes more than 59
        boolean actual = meetingSchedulerService.isCorrectTimeFormat("22:63");
        Assertions.assertEquals(expected, actual);

        // not folllowing HH:MM
        actual = meetingSchedulerService.isCorrectTimeFormat("11:45:30");
        Assertions.assertEquals(expected, actual);

        // 15 min rule
        actual = meetingSchedulerService.isCorrectTimeFormat("11:20");
        Assertions.assertEquals(expected, actual);

        // Random Number
        actual = meetingSchedulerService.isCorrectTimeFormat("11520");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Is Correct Time Format - should Return True")
    public void isCorrectTimeFormatTest_shouldReturnTrue() {
        boolean expected = true;
        boolean actual = meetingSchedulerService.isCorrectTimeFormat("22:45");
        Assertions.assertEquals(expected, actual);

        actual = meetingSchedulerService.isCorrectTimeFormat("11:00");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - View Available Rooms - should Return Sorted RoomsList")
    public void viewAvailableRoomsTest_shouldReturnRoomsList()
            throws RoomNotAvailableException, ParseException, IncorrectInputException
    {
        List<MeetingRoom> roomsList = new ArrayList<>(
                List.of(
                        new MeetingRoom("TestRoom1", 20),
                        new MeetingRoom("TestRoom2", 10)
                        )
        );

        roomsList.get(0).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        roomsList.get(1).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);

        when(meetingRoomRepoMock.getAllRooms()).thenReturn(roomsList);

        List<MeetingRoom> expected = new ArrayList<>(
                List.of(
                        new MeetingRoom("TestRoom2", 10),
                        new MeetingRoom("TestRoom1", 20)
                        )
        );

        List<MeetingRoom> actual = meetingSchedulerService.viewAvailableMeetingRoom("12:00", "12:15");
        Assertions.assertEquals(expected, actual);

    }



    @Test
    @DisplayName("Test - View Available Rooms - should Throw IncorrectInput Exception")
    public void viewAvailableRoomsTest_shouldThrowIcorrectInputException()
            throws RoomNotAvailableException, ParseException
    {
        Assertions.assertThrows(IncorrectInputException.class, () -> {meetingSchedulerService.viewAvailableMeetingRoom("12:00", "12:25");});

    }

    @Test
    @DisplayName("Test - View Available Rooms - should Throw RoomNotAvailable Exception")
    public void viewAvailableRoomsTest_shouldThrowRoomNotAvailableException()
            throws ParseException, RoomNotAvailableException
    {
        List<MeetingRoom> roomsList = new ArrayList<>(
                List.of(
                        new MeetingRoom("TestRoom1", 20),
                        new MeetingRoom("TestRoom2", 10)
                        )
        );

        roomsList.get(0).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        roomsList.get(1).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);

        when(meetingRoomRepoMock.getAllRooms()).thenReturn(roomsList);

        //Exception due to conflict
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.viewAvailableMeetingRoom("11:00", "11:30");});
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.viewAvailableMeetingRoom("11:30", "12:00");});
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.viewAvailableMeetingRoom("11:00", "12:00");});

    }



    @Test
    @DisplayName("Test - Book Meeting Rooms - should Book Optimal Room")
    public void bookMeetingRoomsTest_shouldBookOptimalRoom()
            throws RoomNotAvailableException, ParseException, IncorrectInputException
    {
        List<MeetingRoom> roomsList = new ArrayList<>(
                List.of(
                        new MeetingRoom("TestRoom1", 20),
                        new MeetingRoom("TestRoom2", 10)
                        )
        );

        roomsList.get(0).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        roomsList.get(1).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);

        when(meetingRoomRepoMock.getAllRooms()).thenReturn(roomsList);

        String expected = "TestRoom2";
        String actual = meetingSchedulerService.bookMeetingRoom("12:00", "12:15", 7);

        Assertions.assertEquals(expected, actual);

        expected = "TestRoom1";
        actual = meetingSchedulerService.bookMeetingRoom("12:00", "12:15", 19);

        Assertions.assertEquals(expected, actual);

    }



    @Test
    @DisplayName("Test - Book Rooms - should Throw IncorrectInput Exception")
    public void bookMeetingRoomsTest_shouldThrowIncorrectInputException()
            throws RoomNotAvailableException, ParseException
    {
        Assertions.assertThrows(IncorrectInputException.class, () -> {meetingSchedulerService.bookMeetingRoom("12:00", "12:25", 6);});
        Assertions.assertThrows(IncorrectInputException.class, () -> {meetingSchedulerService.bookMeetingRoom("12:00", "12:15:30", 6);});

    }

    @Test
    @DisplayName("Test - Book Rooms - should Throw RoomNotAvailable Exception")
    public void bookMeetingRoomTest_shouldThrowRoomNotAvailableException()
            throws ParseException, RoomNotAvailableException
    {
        List<MeetingRoom> roomsList = new ArrayList<>(
                List.of(
                        new MeetingRoom("TestRoom1", 20),
                        new MeetingRoom("TestRoom2", 10)
                        )
        );

        roomsList.get(0).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);
        roomsList.get(1).scheduleMeeting(new SimpleDateFormat("HH:mm").parse("11:15") , new SimpleDateFormat("HH:mm").parse("11:45"), 6);

        when(meetingRoomRepoMock.getAllRooms()).thenReturn(roomsList);

        //Exception due to conflict
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.bookMeetingRoom("11:00", "11:30", 7);});
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.bookMeetingRoom("11:30", "12:00", 19);});
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.bookMeetingRoom("11:00", "12:00", 10);});

        //Exception due to capacity
        Assertions.assertThrows(RoomNotAvailableException.class, () -> {meetingSchedulerService.bookMeetingRoom("15:00", "15:30", 21);});
    }


}