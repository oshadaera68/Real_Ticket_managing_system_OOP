/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Customer implements Runnable {
    private final TicketPool ticketPool; // Shared pool of tickets
    private final int retrievalRate; // Time between customer ticket retrieval attempts (in milliseconds)
    private final int ticketsToPurchase; // Total number of tickets the customer wants to purchase
    private static volatile boolean stop = false; // Shared flag to stop all customer threads

    /**
     * Constructor to initialize the customer with ticket pool, retrieval rate, and number of tickets to purchase.
     *
     * @param ticketPool        the shared pool of tickets
     * @param retrievalRate     the rate at which the customer attempts to retrieve tickets
     * @param ticketsToPurchase the number of tickets the customer intends to purchase
     */
    public Customer(TicketPool ticketPool, int retrievalRate, int ticketsToPurchase) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.ticketsToPurchase = ticketsToPurchase;
    }

    /**
     * Stop all customer threads by setting the stop flag to true.
     */
    public static void stopAll() {
        stop = true; // Set the stop flag to true to signal customers to stop purchasing
    }

    /**
     * Run method to simulate the customer's ticket purchasing process.
     * Attempts to purchase tickets until the stop flag is set or the customer has purchased all requested tickets.
     */
    @Override
    public void run() {
        int purchased = 0; // Counter for the number of tickets the customer has successfully purchased
        while (!stop && purchased < ticketsToPurchase) { // Loop until stop flag is true or all tickets are purchased
            try {
                // Attempt to retrieve a ticket from the pool
                boolean success = ticketPool.retrieveTicket();
                if (success) {
                    purchased++; // Increment purchased ticket count
                    System.out.println(Thread.currentThread().getName() + " purchased a ticket.");
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to purchase a ticket.");
                }
                Thread.sleep(retrievalRate); // Simulate delay between retrieval attempts
            } catch (InterruptedException e) {
                // Handle interruption by setting the thread's interrupt status and breaking the loop
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " finished purchasing tickets.");
    }
}
