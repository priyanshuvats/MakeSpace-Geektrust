package com.example.geektrust.repositories;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.DataNotFoundException;

import java.util.List;

public interface IMeetingRoomRepo {
    public void addMeetingRoom(MeetingRoom meetingRoom);
    public MeetingRoom getRoomByName(String name) throws DataNotFoundException;
    public List<MeetingRoom> getAllRooms();
}