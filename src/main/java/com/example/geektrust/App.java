package com.example.geektrust;

import com.example.geektrust.commands.*;
import com.example.geektrust.config.*;

import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;


public class App {
    public static void run(List<String> commandLineArgs) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        BufferedReader reader;

        String inputFile = commandLineArgs.get(0);
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0),tokens);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
       // List<String> commandLineArgs = Arrays.asList(args);
        List<String> commandLineArgs = Arrays.asList(new String[]{"temp/input1.txt"});
        run(commandLineArgs);
    }
}
