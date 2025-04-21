package Calculator;

import CommandFactory.*;
import Commands.Command;

import java.io.*;
import java.util.*;

public class Calculator {
    private final ExecutionContext storage = new ExecutionContext();
    private final CommandFactory commandFactory = new CommandFactory();

    public Calculator() throws CommandException, IOException {}

    public void readFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                executeCommand(line);
            }
        } catch (Exception e) {
            System.out.println("Error reading file " + file);
        }


    }

    private void executeCommand(String commandLine) {
        String[] split = commandLine.split("\\s+");
        String cmd = split[0].toUpperCase();
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        try {
            Command command = commandFactory.createCommand(cmd);
            command.execute(args, storage);
        } catch (CommandException e) {
            System.out.println("Error command '" + commandLine + "': " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error in command '" + commandLine + "': " + e.getMessage());
        }
    }
}
