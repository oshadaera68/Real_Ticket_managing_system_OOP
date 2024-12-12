import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Scanner;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 *
 * Main CLI entry point for the Ticket Management System.
 * This class handles user input and manages the system's functionality.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;

        // Display welcome message and commands
        System.out.println("===============================================");
        System.out.println("   Welcome to the Ticket Management System!    ");
        System.out.println("===============================================");
        System.out.println("Available Commands:");
        System.out.println("  start  - Start ticket release.");
        System.out.println("  status - Display the current ticket pool status.");
        System.out.println("  stop   - Stop ticket release and exit the system.");
        System.out.println("  config - Configure ticket system settings.");
        System.out.println("  help   - Display this list of available commands.");
        System.out.println("  exit   - Exit the application.");
        System.out.println("===============================================");

        // Initialize shared TicketPool and threads
        TicketPool ticketPool = null;
        Vendor vendor = null;
        Thread vendorThread = null;

        boolean running = true;

        // Start the main CLI loop
        while (running) {
            System.out.print("Enter the Command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            // Command handling based on user input
            switch (command) {
                case "config":
                    // Set up the system configuration
                    config = setupConfiguration(scanner);
                    ticketPool = new TicketPool(config.maxTicketCapacity);
                    vendor = new Vendor(config.totalTickets, config.ticketReleaseRate, ticketPool);
                    vendorThread = new Thread(vendor, "Vendor");
                    System.out.println("Configuration setup complete.");
                    break;

                case "start":
                    // Start the ticket release process (vendor thread)
                    if (config == null) {
                        System.out.println("Error: Please configure the system first using the 'config' command.");
                    } else if (!vendorThread.isAlive()) {
                        vendorThread.start();
                        System.out.println("Vendor thread started.");
                    } else {
                        System.out.println("Vendor thread is already running.");
                    }
                    break;

                case "status":
                    // Display the current ticket pool status
                    if (ticketPool == null) {
                        System.out.println("Error: Please configure the system first using the 'config' command.");
                    } else {
                        displayTicketPoolStatus(ticketPool);
                    }
                    break;

                case "stop":
                    // Stop the ticket release process and terminate threads
                    if (vendorThread.isAlive()) {
                        Customer.stopAll(); // Signal customers to stop
                        System.out.println("Stopping all customer threads...");

                        // Wait for vendor thread to finish
                        try {
                            vendorThread.join();
                            System.out.println("Vendor thread stopped.");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        System.out.println("No active vendor or customer threads to stop.");
                    }
                    break;

                case "help":
                    // Display available commands to the user
                    displayHelp();
                    break;

                case "exit":
                    // Exit the application and stop any active threads
                    System.out.println("Exiting the system...");
                    if (vendorThread != null && vendorThread.isAlive()) {
                        vendor.stopVendor();
                        vendorThread.interrupt();
                    }
                    running = false;
                    break;

                default:
                    // Handle unknown commands
                    System.out.println("Unknown command. Type 'help' for available commands.");
                    break;
            }
        }

        scanner.close();  // Close the scanner when exiting the program
    }

    /**
     * Sets up the configuration either from a file or manually via user input.
     *
     * @param scanner The Scanner object used for input.
     * @return The configured settings for the ticket system.
     */
    private static Configuration setupConfiguration(Scanner scanner) {
        System.out.print("Do you want to load configuration from a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        Configuration config;

        if (response.equals("yes")) {
            // Load configuration from file
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();

            try {
                config = Configuration.loadConfiguration(filePath); // Attempt to load from file
                System.out.println("Configuration loaded successfully:");
                displayConfiguration(config); // Display the loaded configuration

                System.out.print("Do you want to update the configuration file? (yes/no): ");
                String updateResponse = scanner.nextLine().trim().toLowerCase();

                // Allow user to update configuration if needed
                if (updateResponse.equals("yes")) {
                    config = inputConfiguration(scanner); // Collect updated config values
                    config.saveConfiguration(filePath);  // Save to file
                    System.out.println("Configuration updated and saved successfully.");
                }
            } catch (IOException | JsonSyntaxException e) {
                System.out.println("Error loading configuration: " + e.getMessage());
                System.out.println("Switching to manual configuration input.");
                config = inputConfiguration(scanner); // Fallback to manual configuration input
            }
        } else {
            // Manually input configuration if file loading is not chosen
            config = inputConfiguration(scanner);
        }

        return config;
    }

    /**
     * Displays the configuration details to the user.
     *
     * @param config The configuration object containing system settings.
     */
    private static void displayConfiguration(Configuration config) {
        System.out.println("Total Tickets: " + config.totalTickets);
        System.out.println("Ticket Release Rate: " + config.ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + config.customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + config.maxTicketCapacity);
    }

    /**
     * Displays the current status of the ticket pool.
     *
     * @param ticketPool The ticket pool whose status is to be displayed.
     */
    private static void displayTicketPoolStatus(TicketPool ticketPool) {
        System.out.println("=== Ticket Pool Status ===");
        System.out.println("Tickets Available: " + ticketPool.getTicketCount());
        System.out.println("==========================");
    }

    /**
     * Displays the list of available commands.
     */
    private static void displayHelp() {
        System.out.println("  start  - Start ticket release.");
        System.out.println("  status - Display the current ticket pool status.");
        System.out.println("  stop   - Stop ticket release and exit the system.");
        System.out.println("  config - Configure ticket system settings.");
        System.out.println("  help   - Display this list of available commands.");
        System.out.println("  exit   - Exit the application.");
    }

    /**
     * Gathers configuration input from the user manually.
     *
     * @param scanner The scanner used to gather user input.
     * @return The configuration object with user-provided values.
     */
    private static Configuration inputConfiguration(Scanner scanner) {
        Configuration config = new Configuration();

        config.totalTickets = getValidatedInput(scanner, "Enter total tickets: ",
                totTickets -> totTickets > 0, "Total tickets must be positive.");

        config.ticketReleaseRate = getValidatedInput(scanner, "Enter ticket release rate (in seconds): ",
                releaseRate -> releaseRate > 0, "Ticket release rate must be positive.");

        config.customerRetrievalRate = getValidatedInput(scanner, "Enter customer retrieval rate (in seconds): ",
                retriveRate -> retriveRate > 0, "Customer retrieval rate must be positive.");

        config.maxTicketCapacity = getValidatedInput(scanner, "Enter max ticket capacity: ", value -> value > 0
                        && value >= config.totalTickets,
                "Max ticket capacity must be greater than 0 and at least equal to total tickets.");

        return config;
    }

    /**
     * Validates user input based on a custom validation rule.
     *
     * @param scanner The scanner used to gather user input.
     * @param prompt The prompt to display to the user.
     * @param validation The validation rule to apply.
     * @param errorMessage The error message to display if validation fails.
     * @return The valid input value.
     */
    private static int getValidatedInput(Scanner scanner, String prompt,
                                         ValidationRule validation, String errorMessage) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = Integer.parseInt(scanner.nextLine());
                if (validation.isValid(value)) {
                    break;
                } else {
                    System.out.println("Error: " + errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        return value;
    }
}
