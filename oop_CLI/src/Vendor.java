import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

/**
 * The Vendor class represents a ticket vendor responsible for releasing tickets into a ticket pool.
 * The vendor operates in a separate thread, releasing tickets at a specified rate until all tickets are released.
 */
public class Vendor implements Runnable {
    // Total number of tickets to be released by the vendor
    private final int totalTickets;

    // Time interval between releasing tickets (in seconds)
    private final int ticketReleaseRate;

    // Shared TicketPool where tickets are added
    private final TicketPool ticketPool;

    /**
     * Constructs a Vendor with the specified parameters.
     *
     * @param totalTickets     The total number of tickets to release.
     * @param ticketReleaseRate The rate at which tickets are released (in seconds).
     * @param ticketPool       The shared TicketPool to add tickets to.
     * @throws IllegalArgumentException If totalTickets or ticketReleaseRate is not positive.
     */
    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        if (totalTickets <= 0 || ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Total tickets and release rate must be positive.");
        }
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    /**
     * The main execution method for the vendor thread.
     * This method releases tickets into the ticket pool at the specified rate.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            // Create a new ticket with a unique ID, event name, and fixed price
            Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal(100));

            // Add the ticket to the shared ticket pool
            ticketPool.addTicket(ticket);

            try {
                // Wait for the specified release rate before adding the next ticket
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                // Handle thread interruption gracefully
                System.err.println("Vendor thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}