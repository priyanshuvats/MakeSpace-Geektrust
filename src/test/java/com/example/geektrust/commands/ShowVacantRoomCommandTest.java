package com.example.geektrust.commands;

import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
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
public class ShowVacantRoomCommandTest {
    // prints correct output for diff available inputs
    // incorrect time
    // no vacant room
    // rooms
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    MeetingSchedulerService meetingSchedulerServiceMock;

    @InjectMocks
    ShowVacantRoomCommand showVacantRoomCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Test - Execute Method - shouldPrintListofRooms")
    public void executeMethodTest_shouldPrintRoomsList()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        List<MeetingRoom> roomsList = new ArrayList<>(
            List.of(
                    new MeetingRoom("TestRoom2", 10),
                    new MeetingRoom("TestRoom1", 20)
                    )
    );
        when(meetingSchedulerServiceMock.viewAvailableMeetingRoom("12:00", "12:15")).thenReturn(roomsList);
        
        String expected = "TestRoom2 TestRoom1";

        List<String> commands = new ArrayList<String>();
        commands.add("VACANCY"); commands.add("12:00"); commands.add("12:15");
        showVacantRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test - Execute Method - shouldPrintIncorrectInput")
    public void executeMethodTest_shouldPrintIncorrectInput()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        when(meetingSchedulerServiceMock.viewAvailableMeetingRoom("12:00", "12:25")).thenThrow(new IncorrectInputException());
        
        String expected = "INCORRECT_INPUT";

        List<String> commands = new ArrayList<String>();
        commands.add("VACANCY"); commands.add("12:00"); commands.add("12:25");
        showVacantRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Test - Execute Method - shouldPrintNoVacantRoom")
    public void executeMethodTest_shouldPrintNoVacantRoom()
            throws ParseException, IncorrectInputException, RoomNotAvailableException
    {   
        when(meetingSchedulerServiceMock.viewAvailableMeetingRoom("12:00", "12:15")).thenThrow(new RoomNotAvailableException());
        
        String expected = "NO_VACANT_ROOM";

        List<String> commands = new ArrayList<String>();
        commands.add("VACANCY"); commands.add("12:00"); commands.add("12:15");
        showVacantRoomCommand.execute(commands);

        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());
    }

}
