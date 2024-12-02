import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Configuration implements Serializable {
    int totalTickets;
    int customerRetrivalRate;
    int ticketReleaseRate;
    int maximumTicketCapacity;

    public static Configuration loadConfiguration(String filepath) throws JsonSyntaxException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Save configuration to a file in JSON format using Gson
    public void saveConfiguration(String filepath) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filepath)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Input configuration from the CLI with validation
    public void inputFromCLI() {
        Scanner inputCli = new Scanner(System.in);

        // Validating the total tickets
        totalTickets = getValidateInput(inputCli, "Enter the total count of tickets: ",
                ticketCount -> ticketCount > 0,
                "Total tickets must be greater than 0 and positive number.");

        // Validating ticket release rate
        ticketReleaseRate = getValidateInput(inputCli, "Enter the ticket Release rate: ",
                releaseRate -> releaseRate > 0,
                "Ticket release rate must be a greater than 0 and positive number.");

        customerRetrivalRate = getValidateInput(inputCli, "Enter the customer retrival rate: ",
                cusRetrivalRate -> cusRetrivalRate > 0,
                "Customer retrieval rate must be a greater than 0 and positive number.");

        maximumTicketCapacity = getValidateInput(inputCli, "Enter the ticket Release rate: ",
                ticketCapacity -> ticketCapacity >= totalTickets,
                "Max ticket capacity must be greater than or equal to total tickets.");
    }


    // Helper method to validate user input
    public int getValidateInput(Scanner scanner, String consolePrompt, ValidationRule rule, String errorMessage) {
        int inputValue;
        while (true) {
            try {
                System.out.println(consolePrompt);
                inputValue = Integer.parseInt(scanner.nextLine());
                if (rule.isValid(inputValue)) {
                    break;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        return inputValue;
    }
}
