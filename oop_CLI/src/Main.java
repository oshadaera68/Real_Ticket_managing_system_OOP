import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Scanner;

/**
 * Coded By: Era Boy
 * Version: v2.1.0
 */

// Main CLI entry point for the Ticket Management System.
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

            switch (command) {
                case "config":
                    config = setupConfiguration(scanner);
                    ticketPool = new TicketPool(config.maxTicketCapacity);
                    vendor = new Vendor(config.totalTickets, config.ticketReleaseRate, ticketPool);
                    vendorThread = new Thread(vendor, "Vendor");
                    System.out.println("Configuration setup complete.");
                    break;

                case "start":
                    if (config == null) {
                        System.out.println("Error: Please configure the system first using the 'config' command.");
                    } else if (!vendorThread.isAlive()) {
                        vendorThread.start();
                        System.out.println("Vendor thread started.");

                        // Start multiple customer threads
                        /*System.out.print("Enter the number of customers: ");
                        int customerCount = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        for (int i = 1; i <= customerCount; i++) {
                            System.out.print("Enter tickets to purchase for Customer-" + i + ": ");
                            int tickets = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            Customer customer = new Customer(ticketPool, config.customerRetrievalRate, tickets);
                            Thread customerThread = new Thread(customer, "Customer-" + i);
                            customerThread.start();
                        }*/
                    } else {
                        System.out.println("Vendor thread is already running.");
                    }
                    break;

                case "status":
                    if (ticketPool == null) {
                        System.out.println("Error: Please configure the system first using the 'config' command.");
                    } else {
                        displayTicketPoolStatus(ticketPool);
                    }
                    break;

                case "stop":
                    if (vendorThread.isAlive()) {
                        Customer.stopAll(); // Signal customers to stop
                        System.out.println("Stopping all customer threads...");

                        // Wait for threads to finish (optional: if you manage references to threads)
                        try {
                            vendorThread.join(); // Wait for vendor thread to finish
                            System.out.println("Vendor thread stopped.");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        System.out.println("No active vendor or customer threads to stop.");
                    }
                    break;

                case "help":
                    displayHelp();
                    break;

                case "exit":
                    System.out.println("Exiting the system...");
                    if (vendorThread != null && vendorThread.isAlive()) {
                        vendor.stopVendor();
                        vendorThread.interrupt();
                    }
                    running = false;
                    break;


                default:
                    System.out.println("Unknown command. Type 'help' for available commands.");
                    break;
            }
        }

        scanner.close();
    }

    /**
     * Sets up the configuration either from a file or manually via user input.
     */
    private static Configuration setupConfiguration(Scanner scanner) {
        System.out.print("Do you want to load configuration from a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        Configuration config;

        if (response.equals("yes")) {
            System.out.print("Enter the file path: ");
            String filePath = scanner.nextLine();

            try {
                // Attempt to load the configuration from the file
                config = Configuration.loadConfiguration(filePath);
                System.out.println("Configuration loaded successfully:");
                displayConfiguration(config);

                System.out.print("Do you want to update the configuration file? (yes/no): ");
                String updateResponse = scanner.nextLine().trim().toLowerCase();

                // Allow the user to update and save the configuration file if needed
                if (updateResponse.equals("yes")) {
                    config = inputConfiguration(scanner);
                    config.saveConfiguration(filePath);
                    System.out.println("Configuration updated and saved successfully.");
                }
            } catch (IOException | JsonSyntaxException e) {
                System.out.println("Error loading configuration: " + e.getMessage());
                System.out.println("Switching to manual configuration input.");
                config = inputConfiguration(scanner); // Fallback to manual configuration input
            }
        } else {
            // If user opts not to load from a file, input configuration manually
            config = inputConfiguration(scanner);
        }

        return config;
    }

    /**
     * Displays the all configs in the system
     */
    private static void displayConfiguration(Configuration config) {
        System.out.println("Total Tickets: " + config.totalTickets);
        System.out.println("Ticket Release Rate: " + config.ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + config.customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + config.maxTicketCapacity);
    }

    /**
     * Displays the ticket pool's current status.
     */
    private static void displayTicketPoolStatus(TicketPool ticketPool) {
        System.out.println("=== Ticket Pool Status ===");
        System.out.println("Tickets Available: " + ticketPool.getTicketCount());
        System.out.println("==========================");
    }

    /**
     * Displays available CLI commands.
     */
    private static void displayHelp() {
        System.out.println("Available Commands:");
        System.out.println("start  - Start ticket release");
        System.out.println("status - Display current ticket pool status");
        System.out.println("stop   - Stop ticket release");
        System.out.println("exit   - Exit the application");
    }

    /**
     * Gathers configuration input manually.
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
     * Validates user input with custom rules.
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