package com.example.geektrust.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.geektrust.exceptions.TimeConflictException;

public class BufferTime {
    private List<ArrayList<Date>> bufferSchedule;

    public BufferTime()
    {
        this.bufferSchedule = new ArrayList<ArrayList<Date>>();
    }

    public boolean isConflictingBuffer(Date startTime, Date endTime)
    {   
        //check if the passed start and endTime conflicts with buffertime of meetingRoom
        for(ArrayList<Date> interval: bufferSchedule)
        {
            Date bufferStartTime = interval.get(0);
            Date bufferEndTime = interval.get(1);
            if(startTime.before(bufferEndTime) && startTime.after(bufferStartTime)){return true;}
            if(endTime.after(bufferStartTime) && endTime.before(bufferEndTime)){return true;}
            if(!startTime.after(bufferStartTime) && !endTime.before(bufferEndTime)){return true;}
        }
        return false;
    }

    public void addBufferTime(Date startTime, Date endTime) throws TimeConflictException
    {
        if(isConflictingBuffer(startTime, endTime))
        {
            throw new TimeConflictException();
        }
        ArrayList<Date> interval = new ArrayList<Date>(2);
        interval.add(startTime);
        interval.add(endTime);
        bufferSchedule.add(interval);
    }

    public List<ArrayList<Date>> getBufferSchedule() {
        ArrayList<ArrayList<Date>> copySchedule = new ArrayList<ArrayList<Date>>();
        for(ArrayList<Date> list: bufferSchedule)
        {
            copySchedule.add((ArrayList)list.clone());
        }
        return copySchedule;
    }
}