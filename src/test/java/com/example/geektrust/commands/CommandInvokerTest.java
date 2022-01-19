package com.example.geektrust.commands;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

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

}