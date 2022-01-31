package com.example.geektrust.commands;

import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.services.MeetingSchedulerService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookMeetingRoomCommandTest {
    //prints correct output for diff available inputs
    //incorrect time
    //no vacant room
    //room
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    MeetingSchedulerService meetingSchedulerServiceMock;

    @InjectMocks
    BookMeetingRoomCommand bookMeetingRoomCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Test - Execute Method - shouldPrintOptimalRoom")
    public void executeMethodTest_shouldPrintOptimalRoom()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        String room = "TestRoom2";

        when(meetingSchedulerServiceMock.bookMeetingRoom("12:00", "12:15", 6)).thenReturn(room);
        
        String expected = "TestRoom2";

        List<String> commands = new ArrayList<String>();
        commands.add("BOOK"); commands.add("12:00"); commands.add("12:15"); commands.add("6");
        bookMeetingRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test - Execute Method - Incorrect Time - shouldPrintIncorrectInput")
    public void executeMethodTest_incorrectTime_shouldPrintIncorrectInput()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        when(meetingSchedulerServiceMock.bookMeetingRoom("12:00", "12:25", 6)).thenThrow(new IncorrectInputException());
        
        String expected = "INCORRECT_INPUT";

        List<String> commands = new ArrayList<String>();
        commands.add("BOOK"); commands.add("12:00"); commands.add("12:25"); commands.add("6");
        bookMeetingRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }


    @Test
    @DisplayName("Test - Execute Method - Incorrect Args - shouldPrintIncorrectInput")
    public void executeMethodTest_incorrectArgs_shouldPrintIncorrectInput()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        //when(meetingSchedulerServiceMock.bookMeetingRoom("12:00", "12:25", 6)).thenThrow(new IncorrectInputException());
        
        String expected = "INCORRECT_INPUT";

        List<String> commands = new ArrayList<String>();

        commands.add("BOOK"); commands.add("12:00"); commands.add("12:15");
        bookMeetingRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test - Execute Method - Incorrect Capacity - shouldPrintIncorrectInput")
    public void executeMethodTest_incorrectCapacity_shouldPrintIncorrectInput()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        //when(meetingSchedulerServiceMock.bookMeetingRoom("12:00", "12:25", 6)).thenThrow(new IncorrectInputException());
        
        String expected = "INCORRECT_INPUT";

        List<String> commands = new ArrayList<String>();

        commands.add("BOOK"); commands.add("12:00"); commands.add("12:15"); commands.add("lol");
        bookMeetingRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }


    @Test
    @DisplayName("Test - Execute Method - shouldPrintNoVacantRoom")
    public void executeMethodTest_shouldPrintNoVacantRoom()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        when(meetingSchedulerServiceMock.bookMeetingRoom("12:00", "12:15", 6)).thenThrow(new RoomNotAvailableException());
        
        String expected = "NO_VACANT_ROOM";

        List<String> commands = new ArrayList<String>();
        commands.add("BOOK"); commands.add("12:00"); commands.add("12:15"); commands.add("6");
        bookMeetingRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

}