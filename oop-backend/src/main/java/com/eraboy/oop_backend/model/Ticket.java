package com.eraboy.oop_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the ticket

    private String eventName; // Name of the event associated with the ticket
    private boolean sold; // Status to indicate if the ticket is sold

    // Default constructor (required by JPA)
    public Ticket() {
    }

    // Constructor to initialize with event name
    public Ticket(String eventName) {
        this.eventName = eventName;
        this.sold = false; // Tickets are unsold by default
    }

    // Getter for ID
    public Long getId() {
        return id;
    }

    // Setter for ID
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for Event Name
    public String getEventName() {
        return eventName;
    }

    // Setter for Event Name
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // Getter for Sold Status
    public boolean isSold() {
        return sold;
    }

    // Setter for Sold Status
    public void setSold(boolean sold) {
        this.sold = sold;
    }
}
