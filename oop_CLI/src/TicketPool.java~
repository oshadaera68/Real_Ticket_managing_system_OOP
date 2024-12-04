import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class TicketPool {
    private final int maximumTicketCapacity; // it means pool's have the maximum capacity of tickets
    private final List<Ticket> ticketQueue;

    // Ticket pool constructor
    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketQueue = Collections.synchronizedList(new LinkedList<>());
    }

    // Add Tickets
    public synchronized void addTickets(List<Ticket> addTicket) {
        while (ticketQueue.size() + addTicket.size() > maximumTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); // interrupt the thread for giving the exception
            }
        }
        ticketQueue.addAll(addTicket);
        System.out.println();
        notifyAll();
    }

    //Removing ticket
    public synchronized Ticket removeTicket() {
        while (ticketQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = ticketQueue.removeFirst();
        notifyAll();
        return ticket;
    }
}
