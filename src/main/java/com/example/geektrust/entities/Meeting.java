package com.example.geektrust.entities;

import java.util.Date;

public class Meeting {
    private Date startTime;
    private Date endTime;
    private int participants;
    private String meetingRoomName;

    public Meeting(Date startTime, Date endTime, int participants, String meetingRoomName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
        this.meetingRoomName = meetingRoomName;
    }

    public Date getStartTime() {
        return (Date)startTime.clone();
    }

    public Date getEndTime() {
        return (Date)endTime.clone();
    }


    public int getCountAttendees() {
        int copyParticipants = participants;
        return copyParticipants;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((meetingRoomName == null) ? 0 : meetingRoomName.hashCode());
        result = prime * result + participants;
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
        Meeting other = (Meeting) obj;
        if (endTime == null) {
            if (other.endTime != null)
                return false;
        } else if (!endTime.equals(other.endTime))
            return false;
        if (meetingRoomName == null) {
            if (other.meetingRoomName != null)
                return false;
        } else if (!meetingRoomName.equals(other.meetingRoomName))
            return false;
        if (participants != other.participants)
            return false;
        if (startTime == null) {
            if (other.startTime != null)
                return false;
        } else if (!startTime.equals(other.startTime))
            return false;
        return true;
    }

    
}