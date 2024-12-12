import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 *
 * The Ticket class represents a ticket for an event, containing a unique ticket ID,
 * the event's name, and the price of the ticket.
 **/
public class Ticket {

    // The unique identifier for the ticket
    private final int ticketId;

    // The name of the event associated with the ticket
    private final String event;

    // The price of the ticket
    private final BigDecimal price;

    /**
     * Constructor to initialize the Ticket object with the given ticket ID, event, and price.
     *
     * @param ticketId The unique ID for the ticket
     * @param event The name of the event
     * @param price The price of the ticket
     */
    public Ticket(int ticketId, String event, BigDecimal price) {
        this.ticketId = ticketId;
        this.event = event;
        this.price = price;
    }

    /**
     * Overrides the toString method to provide a string representation of the Ticket object.
     * This representation includes the ticket ID, event name, and price for easy display or debugging.
     *
     * @return A string representation of the Ticket object
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +  // Ticket ID
                ", event='" + event + '\'' +  // Event name
                ", price=" + price +  // Price of the ticket
                '}';
    }
}
