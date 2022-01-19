package com.example.geektrust.exceptions;

public class RoomNotAvailableException extends Exception{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String toString()
    {
        return "This room is not available!";
    }
}
