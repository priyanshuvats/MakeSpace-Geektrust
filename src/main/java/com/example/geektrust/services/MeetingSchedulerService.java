package com.example.geektrust.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;
import com.example.geektrust.repositories.IMeetingRoomRepo;

public class MeetingSchedulerService implements IMeetingSchedulerService {

    private IMeetingRoomRepo meetingRoomRepo;

    public MeetingSchedulerService(IMeetingRoomRepo meetingRoomRepo) {
        this.meetingRoomRepo = meetingRoomRepo;
    }

    @Override
    public boolean isCorrectTimeFormat(String time) {
        List<String> tokens = Arrays.asList(time.split(":"));
        if (tokens.size() != 2) {
            return false;
        }
        int hours = Integer.valueOf(tokens.get(0));
        int minutes = Integer.valueOf(tokens.get(1));
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || minutes % 15 != 0) {
            return false;
        }
        try {
            Date extractTime = new SimpleDateFormat("HH:mm").parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<MeetingRoom> viewAvailableMeetingRoom(String start, String end)
            throws ParseException, IncorrectInputException, RoomNotAvailableException {

        List<MeetingRoom> roomsList = new ArrayList<MeetingRoom>();

        // check correct format of time
        if (!isCorrectTimeFormat(start) || !isCorrectTimeFormat(end)) {
            throw new IncorrectInputException();
        }
        Date startTime = new SimpleDateFormat("HH:mm").parse(start);
        Date endTime = new SimpleDateFormat("HH:mm").parse(end);

        // check starttime is before endtime
        if (!startTime.before(endTime)) {
            throw new IncorrectInputException();
        }
        // add available rooms to roomsList
        for (MeetingRoom room : meetingRoomRepo.getAllRooms()) {
            if (room.isAvailable(startTime, endTime)) {
                roomsList.add(room);
            }
        }

        Collections.sort(roomsList);
        if (roomsList.size() == 0) {
            throw new RoomNotAvailableException();
        }
        return roomsList;
    }

    @Override
    public String bookMeetingRoom(String start, String end, int participants)
            throws ParseException, IncorrectInputException, RoomNotAvailableException 
    {
        List<MeetingRoom> roomsList = viewAvailableMeetingRoom(start, end);
        Date startTime = new SimpleDateFormat("HH:mm").parse(start);
        Date endTime = new SimpleDateFormat("HH:mm").parse(end);
        for(MeetingRoom room: roomsList)
        {
            if(participants<=room.getCapacity())
            {
                room.scheduleMeeting(startTime, endTime, participants);
                //System.out.println(room.getName());
                return room.getName();
            }
        }
        throw new RoomNotAvailableException();
    }
    
}