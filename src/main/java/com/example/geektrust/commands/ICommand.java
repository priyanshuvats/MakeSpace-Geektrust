package com.example.geektrust.commands;

import java.util.List;

public interface ICommand {
    public void execute(List<String> tokens);
}