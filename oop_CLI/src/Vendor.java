import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        if (totalTickets <= 0 || ticketReleaseRate <= 0) {
            throw new IllegalArgumentException("Total tickets and release rate must be positive.");
        }
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i, "Event-" + i, new BigDecimal(100));
            ticketPool.addTicket(ticket);
            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                System.err.println("Vendor thread interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
