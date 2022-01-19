package com.example.geektrust.entities;

import java.util.List;

import com.example.geektrust.exceptions.RoomNotAvailableException;

import java.util.ArrayList;
import java.util.Date;

public class MeetingRoom implements Comparable<MeetingRoom>{
    private String name;
    private int capacity;
    private List<Meeting> scheduledMeetings;
    private BufferTime bufferTime;

    public MeetingRoom(String name, int capacity, BufferTime bufferTime) {
        this.name = name;
        this.capacity = capacity;
        this.bufferTime = bufferTime;
        this.scheduledMeetings = new ArrayList<Meeting>();
    }

    public MeetingRoom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.bufferTime = new BufferTime();
        this.scheduledMeetings = new ArrayList<Meeting>();
    }

    public boolean isAvailable(Date startTime, Date endTime)
    {
        //check if it has any conflict with bufferTime of meeting room
        if(bufferTime.isConflictingBuffer(startTime, endTime))
        {
            return false;
        }

        //check if it has any conflict with scheduled meetings
        for(Meeting meeting: scheduledMeetings)
        {
            Date meetingStartTime = meeting.getStartTime();
            Date meetingEndTime = meeting.getEndTime();
            if(startTime.before(meetingEndTime) && startTime.after(meetingStartTime)){return false;}
            if(endTime.after(meetingStartTime) && endTime.before(meetingEndTime)){return false;}
            if(!startTime.after(meetingStartTime) && !endTime.before(meetingEndTime)){return false;}
        }

        return true;
    }
    
    public void scheduleMeeting(Date startTime, Date endTime, int participants) throws RoomNotAvailableException
    {
        if(!isAvailable(startTime, endTime) || participants>capacity)
        {
            throw new RoomNotAvailableException(); 
        }

        scheduledMeetings.add(new Meeting(startTime, endTime, participants, name));
    }

    public String getName() {
        String nameClone = new String(name);
        return nameClone;
    }

    public int getCapacity() {
        int capacityClone = capacity;
        return capacityClone;
    }

    @Override
    public int compareTo(MeetingRoom room) {
        if(this.capacity==room.getCapacity())  
            return 0;  
        else if(this.capacity>room.getCapacity())  
            return 1;  
        else  
            return -1;  
    }

    public List<Meeting> getScheduledMeetings() {
        ArrayList<Meeting> copySchedule = (ArrayList)((ArrayList)scheduledMeetings).clone();
        return copySchedule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + capacity;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MeetingRoom other = (MeetingRoom) obj;
        if (capacity != other.capacity)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


}