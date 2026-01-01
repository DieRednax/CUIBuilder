package com.redfox.cuibuilder;

import java.util.*;
import java.util.function.Consumer;

public class CUIBuilder {
    public CUIBuilder(String title, String description, Map<Command, Consumer<List<String>>> commands) {
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("No commands found in input parameter.");
        }

        System.out.printf("%s:\n\n", title);
        System.out.println("Type 'help' if needed.\n");

        Map<Command, Consumer<List<String>>> modifiedCommands = new HashMap<>(commands);

        boolean commandContainsHelp = false;
        for (Command command : commands.keySet()) {
            commandContainsHelp = command.isHelpCommand();
        }
        if (!commandContainsHelp) {
            modifiedCommands.put(new Command("help", "help"), (args) -> {
                StringBuilder sbCommandsList = new StringBuilder();
                for (Command command : commands.keySet()) {
                    sbCommandsList.append("- " + command.getCommand() + ": ");
                    sbCommandsList.append(command.getDescription() + ".");
                }

                System.out.printf("""
                            %s
                            ---------------------------------------------
                            Commands:
                                %s
                            """, description, sbCommandsList);
            });
        }

        buildCUI(modifiedCommands);
    }
    public CUIBuilder(String title, Map<Command, Consumer<List<String>>> commands, Consumer<List<String>> helpCommand) {
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("No commands found in input parameter.");
        }

        System.out.printf("%s:\n\n", title);
        System.out.println("Type 'help' if needed.\n");

        Map<Command, Consumer<List<String>>> modifiedCommands = new HashMap<>(commands);

        boolean commandContainsHelp = false;
        for (Command command : commands.keySet()) {
            commandContainsHelp = command.isHelpCommand();
        }
        if (!commandContainsHelp) {
            modifiedCommands.put(new Command("help", "help"), helpCommand);
        }

        buildCUI(modifiedCommands);
    }


    private void buildCUI(Map<Command, Consumer<List<String>>> commands) {
        boolean open = true;
        Scanner scanner = new Scanner(System.in);
        while (open) {
            String nextLine = scanner.nextLine();
            String[] tokens = nextLine.split("\\s");

            String enteredCommand = tokens[0];
            List<String> enteredArgs = new ArrayList<>();
            int i = 0;
            for (String token : tokens) {
                if (i != 0) {
                    enteredArgs.add(token);
                }

                i++;
            }

            for (Command command : commands.keySet()) {
                final String commandStr = command.getCommand();

                if (enteredCommand.equalsIgnoreCase(commandStr)) {
                    commands.get(command).accept(enteredArgs);
                }
            }
        }
    }
}
