package com.example.geektrust.commands;

import java.text.ParseException;
import java.util.List;

import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.services.IMeetingSchedulerService;

public class BookMeetingRoomCommand implements ICommand {

    private IMeetingSchedulerService meetingSchedulerService;

    public BookMeetingRoomCommand(IMeetingSchedulerService meetingSchedulerService) {
        this.meetingSchedulerService = meetingSchedulerService;
    }

    @Override
    public void execute(List<String> tokens) {
        String start = tokens.get(1);
        String end = tokens.get(2);
        int participants = Integer.valueOf(tokens.get(3));

        try {
            String name = meetingSchedulerService.bookMeetingRoom(start, end, participants);
            System.out.println(name);
        } catch (ParseException e) {
            System.out.println("INCORRECT_INPUT");
        } catch (IncorrectInputException e) {
            System.out.println("INCORRECT_INPUT");
        } catch (RoomNotAvailableException e) {
            System.out.println("NO_VACANT_ROOM");
        }
    }

    
}