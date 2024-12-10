import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Coded By: Era Boy
 * Version: v0.1.1
 */

// Shared ticket pool with producer-consumer synchronization.
public class TicketPool {
    private final int maximumTicketCapacity;
    private final Queue<Ticket> ticketQueue;

    public TicketPool(int maximumTicketCapacity) {
        if (maximumTicketCapacity <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be positive.");
        }
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[ERROR] Vendor thread interrupted while adding tickets.");
                return;
            }
        }
        ticketQueue.add(ticket);
        log("Ticket added. Current pool size: " + ticketQueue.size());
        notifyAll();
    }

    public synchronized int getTicketCount() {
        return ticketQueue.size();
    }

    // New Method: retrieveTicket
    public synchronized boolean retrieveTicket() {
        if (!ticketQueue.isEmpty()) {
            ticketQueue.poll(); // Removes the ticket from the queue.
            log("Ticket retrieved. Remaining pool size: " + ticketQueue.size());
            notifyAll(); // Notify any waiting threads.
            return true;
        } else {
            log("No tickets available to retrieve.");
            return false;
        }
    }

    private void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + timestamp + " | " + threadName + "] " + message);
    }
}
