package com.redfox.cuibuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class CUIBuilder {
    public CUIBuilder(String title, String description, Map<Command, Consumer<List<String>>> commands) {
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("No commands found in input parameter.");
        }

        System.out.printf("%s:\n\n", title);
        System.out.println("Type 'help' if needed.\n");

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

            StringBuilder sbCommandsList = new StringBuilder();
            for (Command command : commands.keySet()) {
                sbCommandsList.append("- " + command.getCommand() + ": ");
                sbCommandsList.append(command.getDescription() + ".");
            }

            for (Command command : commands.keySet()) {
                final String commandStr = command.getCommand();

                if (!command.isHelpCommand()) {
                    if (enteredCommand.equalsIgnoreCase("help")) {
                        System.out.printf("""
                            %s
                            ---------------------------------------------
                            Commands:
                                %s
                            """, description, sbCommandsList);
                    }
                }

                if (enteredCommand.equalsIgnoreCase(commandStr)) {
                    commands.get(command).accept(enteredArgs);
                }
            }
        }
    }
}
