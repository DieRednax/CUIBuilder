package com.redfox.cuibuilder;

public class Command {
    private String command;
    private String description;

    public Command(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public boolean isHelpCommand() {
        return command.equalsIgnoreCase("help");
    }

    public String getCommand() { return this.command; }
    public String getDescription() { return this.description; }

    public void setCommand(String command) { this.command = command; }
    public void setDescription(String description) { this.description = description; }
}
