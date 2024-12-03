/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Main {
    public static void main(String[] args) {
        try {
            Configuration systemConfig = new Configuration();
            systemConfig.inputFromCLI();
            systemConfig.saveConfiguration("test.json");

            // Initialize the ticket pool with the maximum capacity
            TicketPool ticketPool = new TicketPool(systemConfig.maximumTicketCapacity);

            // Start vendor threads
            Thread vendorThread = new Thread(new Vendor(systemConfig.ticketReleaseRate, ticketPool));
            vendorThread.start();

            // Start customer threads
            Thread customerThread = new Thread(new Customer(systemConfig.customerRetrivalRate, ticketPool));
            customerThread.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
