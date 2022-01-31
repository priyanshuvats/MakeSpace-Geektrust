package com.example.geektrust.commands;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandInvokerTest {

    private CommandInvoker commandInvoker;

    @Mock
    ShowVacantRoomCommand ShowVacantRoomCommandMock;

    @Mock
    BookMeetingRoomCommand bookMeetingRoomCommandMock;

    @BeforeEach
    void setup(){
        commandInvoker = new CommandInvoker();
        commandInvoker.register("VACANCY",ShowVacantRoomCommandMock);
        commandInvoker.register("BOOK",bookMeetingRoomCommandMock);
    }

    @Test
    @DisplayName("executeCommand method Should Execute Command Given CommandName and List of tokens")
    public void executeCommand_GivenNameAndTokens_ShouldExecuteCommand() {
        
        commandInvoker.executeCommand("VACANCY", anyList());
        commandInvoker.executeCommand("BOOK", anyList());

        verify(ShowVacantRoomCommandMock).execute(anyList());
        verify(bookMeetingRoomCommandMock).execute(anyList());

    }

    @Test
    @DisplayName("executeCommand method Should print Incorrect Imput on Invalid Commands")
    public void executeCommand_GivenInvalidTokens_ShouldPrintIncorrectInput() {
        
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        commandInvoker.executeCommand("LOL", Arrays.asList(new String[]{"12:00", "12:30"}));

        Assertions.assertEquals("INCORRECT_INPUT", outputStreamCaptor.toString().trim());

    }

}