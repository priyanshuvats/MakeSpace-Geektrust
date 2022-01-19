package com.example.geektrust.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.example.geektrust.commands.*;
import com.example.geektrust.entities.*;
import com.example.geektrust.exceptions.TimeConflictException;
import com.example.geektrust.repositories.*;
import com.example.geektrust.services.*;


public class ApplicationConfig {

    BufferTime bufferTime = new BufferTime();

    private final IMeetingRoomRepo imeetingRoomRepo = new MeetingRoomRepo();

    public ApplicationConfig() {
        try {
            bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00") , new SimpleDateFormat("HH:mm").parse("09:15"));
            bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("13:15") , new SimpleDateFormat("HH:mm").parse("13:45"));
			bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("18:45") , new SimpleDateFormat("HH:mm").parse("19:00"));
		} catch (TimeConflictException | ParseException e) {
			System.out.println("INPUT NOT CORRECT FOR BUFFER TIME");
		}

        imeetingRoomRepo.addMeetingRoom(new MeetingRoom("C-Cave", 3, bufferTime));
        imeetingRoomRepo.addMeetingRoom(new MeetingRoom("D-Tower", 7, bufferTime));
        imeetingRoomRepo.addMeetingRoom(new MeetingRoom("G-Mansion", 20, bufferTime));
	}

    private final IMeetingSchedulerService imeetingService = new MeetingSchedulerService(imeetingRoomRepo);

    private final ICommand showVacantRoomCommand = new ShowVacantRoomCommand(imeetingService);
    private final ICommand bookMeetingRoomCommand = new BookMeetingRoomCommand(imeetingService);


    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("VACANCY",showVacantRoomCommand);
        commandInvoker.register("BOOK",bookMeetingRoomCommand);
        return commandInvoker;
    }
}