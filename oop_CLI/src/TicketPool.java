import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Coded By: Era Boy
 * Version: v0.1.1
 *
 * The TicketPool class manages a shared pool of tickets using a producer-consumer
 * synchronization model to ensure thread safety when adding or retrieving tickets.
 */
public class TicketPool {

    // Maximum capacity of the ticket pool
    private final int maximumTicketCapacity;

    // A queue that holds the tickets in the pool
    private final Queue<Ticket> ticketQueue;

    /**
     * Constructor for initializing the ticket pool with a specified maximum ticket capacity.
     *
     * @param maximumTicketCapacity The maximum number of tickets that can be stored in the pool
     * @throws IllegalArgumentException if the maximumTicketCapacity is less than or equal to zero
     */
    public TicketPool(int maximumTicketCapacity) {
        if (maximumTicketCapacity <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be positive.");
        }
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = new LinkedList<>();
    }

    /**
     * Adds a ticket to the pool, but only if the pool is not full. If the pool is full,
     * the method waits until space becomes available.
     *
     * @param ticket The ticket to be added to the pool
     */
    public synchronized void addTicket(Ticket ticket) {
        // Wait while the pool is at maximum capacity
        while (ticketQueue.size() >= maximumTicketCapacity) {
            try {
                wait();  // Wait until notified by another thread
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[ERROR] Vendor thread interrupted while adding tickets.");
                return;
            }
        }
        ticketQueue.add(ticket);  // Add the ticket to the pool
        log("Ticket added. Current pool size: " + ticketQueue.size());
        notifyAll();  // Notify any waiting threads that a ticket has been added
    }

    /**
     * Returns the current number of tickets in the pool.
     *
     * @return The number of tickets currently in the pool
     */
    public synchronized int getTicketCount() {
        return ticketQueue.size();
    }

    /**
     * Retrieves a ticket from the pool if available. If no tickets are in the pool,
     * it returns false.
     *
     * @return true if a ticket was successfully retrieved, false if no tickets are available
     */
    public synchronized boolean retrieveTicket() {
        // Check if there are tickets in the pool
        if (!ticketQueue.isEmpty()) {
            ticketQueue.poll();  // Remove the ticket from the pool
            log("Ticket retrieved. Remaining pool size: " + ticketQueue.size());
            notifyAll();  // Notify any waiting threads that a ticket has been retrieved
            return true;
        } else {
            log("No tickets available to retrieve.");
            return false;  // No tickets available to retrieve
        }
    }

    /**
     * Logs messages with a timestamp and thread name to track actions on the ticket pool.
     *
     * @param message The message to log
     */
    private void log(String message) {
        // Get the current timestamp formatted as "yyyy-MM-dd HH:mm:ss"
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Get the current thread name
        String threadName = Thread.currentThread().getName();
        // Print the log message with timestamp and thread name
        System.out.println("[" + timestamp + " | " + threadName + "] " + message);
    }
}
