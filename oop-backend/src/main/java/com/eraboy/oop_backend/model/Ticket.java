package com.eraboy.oop_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Era Boy
 * @version v0.1.0
 */

// Represents a ticket with a unique ID, event name, and sold status.
@Entity
public class Ticket {

    // Unique identifier for the ticket
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    // Name of the event associated with the ticket
    private String eventTitle;

    // Indicates whether the ticket has been sold
    private boolean isSold;

    /**
     * Default constructor for the Ticket class.
     */
    public Ticket() {
    }

    /**
     * Constructs a new Ticket instance with the specified event title.
     *
     * @param eventTitle The name of the event associated with the ticket
     */
    public Ticket(String eventTitle) {
        this.eventTitle = eventTitle;
        this.isSold = false; // Initialize sold status to false
    }

    /**
     * Gets the unique identifier for the ticket.
     *
     * @return The ticket ID
     */
    public Long getTicketId() {
        return ticketId;
    }

    /**
     * Sets the unique identifier for the ticket.
     *
     * @param ticketId The new ticket ID
     */
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Gets the name of the event associated with the ticket.
     *
     * @return The event title
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * Sets the name of the event associated with the ticket.
     *
     * @param eventTitle The new event title
     */
    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    /**
     * Indicates whether the ticket has been sold.
     *
     * @return True if the ticket is sold, false otherwise
     */
    public boolean isSold() {
        return isSold;
    }

    /**
     * Sets the sold status of the ticket.
     *
     * @param isSold The new sold status
     */
    public void setSold(boolean isSold) {
        this.isSold = isSold;
    }
}