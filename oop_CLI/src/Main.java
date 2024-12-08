import java.io.IOException;
import java.util.Scanner;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config;

        // Prompt user to decide whether to load the configuration from a file or input manually
        System.out.print("Do you want to load configuration from a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            // User chose to load configuration from a file
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();

            try {
                // Load configuration from the specified file
                config = Configuration.loadConfiguration(filePath);
                System.out.println("Configuration loaded successfully.");
                System.out.println("Total Tickets: " + config.totalTickets);
                System.out.println("Ticket Release Rate: " + config.ticketReleaseRate);
                System.out.println("Customer Retrieval Rate: " + config.customerRetrievalRate);
                System.out.println("Max Ticket Capacity: " + config.maxTicketCapacity);
            } catch (IOException e) {
                // Handle error if file loading fails
                System.out.println("Error loading configuration: " + e.getMessage());
                System.out.println("Switching to manual configuration input.");
                config = inputConfiguration(scanner);
            }
        } else {
            // User chose to manually input configuration
            config = inputConfiguration(scanner);
        }

        // Initialize the TicketPool with the configured maximum ticket capacity
        TicketPool ticketPool = new TicketPool(config.maxTicketCapacity);

        // Create and start the vendor thread to generate tickets
        Vendor vendor = new Vendor(config.totalTickets, config.ticketReleaseRate, ticketPool);
        Thread vendorThread = new Thread(vendor, "Vendor");
        vendorThread.start();

        // Create and start the customer thread to retrieve tickets
        Customer customer = new Customer(ticketPool, config.customerRetrievalRate, 5);
        Thread customerThread = new Thread(customer, "Customer");
        customerThread.start();
    }

    /**
     * This method is used to manually input configuration values.
     * It includes validation for each input.
     *
     * @param scanner The scanner instance for user input.
     * @return The populated Configuration object.
     */
    private static Configuration inputConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        // Input total tickets with validation
        config.totalTickets = getValidatedInput(scanner, "Enter total tickets: ",
                value -> value > 0, "Total tickets must be a positive number.");

        // Input ticket release rate with validation
        config.ticketReleaseRate = getValidatedInput(scanner, "Enter ticket release rate (in seconds): ",
                value -> value > 0, "Ticket release rate must be a positive number.");

        // Input customer retrieval rate with validation
        config.customerRetrievalRate = getValidatedInput(scanner, "Enter customer retrieval rate (in seconds): ",
                value -> value > 0, "Customer retrieval rate must be a positive number.");

        // Input maximum ticket capacity with validation
        config.maxTicketCapacity = getValidatedInput(scanner, "Enter max ticket capacity: ",
                value -> value > 0 && value >= config.totalTickets,
                "Max ticket capacity must be greater than 0 and greater than or equal to total tickets.");

        // Prompt user to save the configuration to a file if desired
        System.out.print("Do you want to save this configuration to a file? (yes/no): ");
        String saveResponse = scanner.nextLine().trim().toLowerCase();
        if (saveResponse.equals("yes")) {
            System.out.print("Enter the file path to save: ");
            String filePath = scanner.nextLine();
            try {
                // Save configuration to the specified file
                config.saveConfiguration(filePath);
                System.out.println("Configuration saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving configuration: " + e.getMessage());
            }
        }

        return config;
    }

    /**
     * This method validates user input for configuration parameters.
     *
     * @param scanner      The scanner instance for user input.
     * @param prompt       The prompt message to display to the user.
     * @param validation   A validation rule to check the input.
     * @param errorMessage The error message to display for invalid input.
     * @return The validated integer input.
     */
    private static int getValidatedInput(Scanner scanner, String prompt, ValidationRule validation, String errorMessage) {
        int value;
        while (true) {
            try {
                // Display the prompt and read user input
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                // Check if the input satisfies the validation rule
                if (validation.isValid(value)) {
                    break;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                // Handle non-numeric input
                System.out.println("Error: Please enter a valid number.");
            }
        }
        return value;
    }
}