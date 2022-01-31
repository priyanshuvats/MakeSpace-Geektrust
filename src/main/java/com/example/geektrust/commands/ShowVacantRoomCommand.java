package com.example.geektrust.commands;

import java.text.ParseException;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.services.IMeetingSchedulerService;

public class ShowVacantRoomCommand implements ICommand {

    private IMeetingSchedulerService meetingSchedulerService;

    public ShowVacantRoomCommand(IMeetingSchedulerService meetingSchedulerService) {
        this.meetingSchedulerService = meetingSchedulerService;
    }

    @Override
    public void execute(List<String> tokens) {
        if(tokens==null || tokens.size()<3){System.out.println("INCORRECT_INPUT"); return;}
        String start = tokens.get(1);
        String end = tokens.get(2);
        try {
            String res = "";
            List<MeetingRoom> roomsList = meetingSchedulerService.viewAvailableMeetingRoom(start, end);
            for(MeetingRoom room: roomsList)
            {
                res+=room.getName();
                res+=" ";
            }
            res = res.substring(0, res.length()-1);
            System.out.println(res);
        } catch (ParseException e) {
            System.out.println("INCORRECT_INPUT");
        } catch (IncorrectInputException e) {
            System.out.println("INCORRECT_INPUT");
        } catch (RoomNotAvailableException e) {
            System.out.println("NO_VACANT_ROOM");
        }
    }
    
}