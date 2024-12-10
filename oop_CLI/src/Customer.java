/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final int ticketsToPurchase;
    private static volatile boolean stop = false; // Shared stop flag

    public Customer(TicketPool ticketPool, int retrievalRate, int ticketsToPurchase) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.ticketsToPurchase = ticketsToPurchase;
    }

    public static void stopAll() {
        stop = true; // Set the stop flag to true
    }

    @Override
    public void run() {
        int purchased = 0;
        while (!stop && purchased < ticketsToPurchase) {
            try {
                // Simulate ticket purchase
                boolean success = ticketPool.retrieveTicket();
                if (success) {
                    purchased++;
                    System.out.println(Thread.currentThread().getName() + " purchased a ticket.");
                } else {
                    System.out.println(Thread.currentThread().getName() + " failed to purchase a ticket.");
                }
                Thread.sleep(retrievalRate); // Simulate retrieval delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop if interrupted
            }
        }
        System.out.println(Thread.currentThread().getName() + " finished purchasing tickets.");
    }
}