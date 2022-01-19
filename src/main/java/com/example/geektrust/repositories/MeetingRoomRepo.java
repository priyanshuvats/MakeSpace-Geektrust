package com.example.geektrust.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.geektrust.entities.MeetingRoom;
import com.example.geektrust.exceptions.DataNotFoundException;

public class MeetingRoomRepo implements IMeetingRoomRepo {

    private final HashMap<String, MeetingRoom> roomMap;

    public MeetingRoomRepo() {
        roomMap = new HashMap<String, MeetingRoom>();
    }

    @Override
    public void addMeetingRoom(MeetingRoom meetingRoom) {
        String name = meetingRoom.getName();
        if(!roomMap.containsKey(name))
        {
            roomMap.put(name, meetingRoom);
        }
    }

    @Override
    public MeetingRoom getRoomByName(String name) throws DataNotFoundException {
        if(!roomMap.containsKey(name))
        {
            throw new DataNotFoundException();
        }
        return roomMap.get(name);
    }

    @Override
    public List<MeetingRoom> getAllRooms() {
        List<MeetingRoom> meetingRoomList = new ArrayList<MeetingRoom>();
        for(String name: roomMap.keySet())
        {
            meetingRoomList.add(roomMap.get(name));
        }
        return meetingRoomList;
    }

    
    
}