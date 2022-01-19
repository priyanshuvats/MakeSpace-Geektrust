package com.example.geektrust.exceptions;

public class DataNotFoundException extends Exception{
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public String toString()
    {
        return "This data doesn't exist!";
    }
}