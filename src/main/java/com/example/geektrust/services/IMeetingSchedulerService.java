package com.example.geektrust.services;

import java.text.ParseException;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.IncorrectInputException;
import com.example.geektrust.exceptions.RoomNotAvailableException;

public interface IMeetingSchedulerService {
    public boolean isCorrectTimeFormat(String time);
    public List<MeetingRoom> viewAvailableMeetingRoom(String startTime, String endTime) throws ParseException, IncorrectInputException, RoomNotAvailableException;
    public String bookMeetingRoom(String startTime, String endTime, int participants) throws ParseException, IncorrectInputException, RoomNotAvailableException;
}