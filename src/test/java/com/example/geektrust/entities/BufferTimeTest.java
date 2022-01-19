package com.example.geektrust.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.geektrust.exceptions.TimeConflictException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BufferTimeTest")
public class BufferTimeTest {
    // Reponsibilities
    // check if a time range is conflicting with buffer
    // throw exception if it is
    // add buffer time
    // throw exception if conflicts
    private BufferTime bufferTime;

    @BeforeEach
    public void setup() throws TimeConflictException, ParseException
    {
        bufferTime = new BufferTime();
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("09:00") , new SimpleDateFormat("HH:mm").parse("09:15"));
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("13:15") , new SimpleDateFormat("HH:mm").parse("13:45"));
		bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("18:45") , new SimpleDateFormat("HH:mm").parse("19:00"));
    }

    @Test
    @DisplayName("Test - Is a Time Range Conflicting Buffer - Should Return False")
    public void isConflictingBufferTest_shouldReturnFalse() throws ParseException
    {
        boolean expected = false;

        boolean actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("10:00") , new SimpleDateFormat("HH:mm").parse("10:30"));
        Assertions.assertEquals(expected, actual);

        actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("18:00") , new SimpleDateFormat("HH:mm").parse("18:45"));
        Assertions.assertEquals(expected, actual);

        actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("13:45") , new SimpleDateFormat("HH:mm").parse("18:45"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Is a Time Range Conflicting Buffer - Should Return True")
    public void isConflictingBufferTest_shouldReturnTrue() throws ParseException
    {
        boolean expected = true;

        boolean actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("08:30") , new SimpleDateFormat("HH:mm").parse("09:10"));
        Assertions.assertEquals(expected, actual);

        actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("13:30") , new SimpleDateFormat("HH:mm").parse("14:30"));
        Assertions.assertEquals(expected, actual);

        actual = bufferTime.isConflictingBuffer(new SimpleDateFormat("HH:mm").parse("18:30") , new SimpleDateFormat("HH:mm").parse("19:30"));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - Add BufferTime when no conflict")
    public void addBufferTimeTest_shouldAddBuffer() throws TimeConflictException, ParseException
    {
        bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("11:00") , new SimpleDateFormat("HH:mm").parse("11:15"));
        ArrayList<Date> expected = new ArrayList<Date>();
        expected.add(new SimpleDateFormat("HH:mm").parse("11:00"));
        expected.add(new SimpleDateFormat("HH:mm").parse("11:15"));
        Assertions.assertEquals(expected, bufferTime.getBufferSchedule().get(3));
    }

    @Test
    @DisplayName("Test - Add BufferTime when there is a conflict - Should Throw Exception")
    public void addBufferTimeTest_shouldThrowException()
    {
        Assertions.assertThrows(TimeConflictException.class, () -> {bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("08:30") , new SimpleDateFormat("HH:mm").parse("09:10"));});
        Assertions.assertThrows(TimeConflictException.class, () -> {bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("13:30") , new SimpleDateFormat("HH:mm").parse("14:30"));});
        Assertions.assertThrows(TimeConflictException.class, () -> {bufferTime.addBufferTime(new SimpleDateFormat("HH:mm").parse("18:30") , new SimpleDateFormat("HH:mm").parse("19:30"));});
    }
}