/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.geektrust;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Integration Test")
    void runTest(){
        
        List<String> arguments= new ArrayList<>(List.of("./temp/input1.txt"));
        String expectedOutput = "C-Cave D-Tower G-Mansion"+ "\n" + 
        "C-Cave"+ "\n" + 
        "NO_VACANT_ROOM"+ "\n" + 
        "G-Mansion"+ "\n" + 
        "D-Tower"+ "\n" + 
        "C-Cave"+ "\n" + 
        "D-Tower"+ "\n" + 
        "INCORRECT_INPUT"+ "\n" + 
        "C-Cave G-Mansion"+ "\n" + 
        "C-Cave"+ "\n" + 
        "G-Mansion"+ "\n" + 
        "G-Mansion"+ "\n" + 
        "NO_VACANT_ROOM"+ "\n" + 
        "NO_VACANT_ROOM";
        
        App.run(arguments);

        //Assertions.assertEquals(expectedOutput,outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown() {

        System.setOut(standardOut);

    }

}
