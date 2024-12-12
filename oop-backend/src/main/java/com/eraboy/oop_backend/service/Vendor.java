package com.eraboy.oop_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class represents a vendor that can add new tickets to the system.
 * It implements the Runnable interface to allow it to run in a separate thread.
 */
@Component
public class Vendor implements Runnable {

    // The TicketService instance used to interact with the ticket service
    private final TicketService ticketService;

    // The rate at which the vendor adds new tickets to the system
    private final int ticketReleaseRate;

    /**
     * Constructs a new Vendor instance with the provided TicketService instance.
     *
     * @param ticketService The TicketService instance to use
     */
    @Autowired
    public Vendor(TicketService ticketService) {
        this.ticketService = ticketService;
        this.ticketReleaseRate = 2; // Example rate, adjust as needed
    }

    /**
     * Runs the vendor thread, which continuously adds new tickets to the system.
     */
    @Override
    public void run() {
        try {
            // Initialize a counter for the ticket number
            int ticketNumber = 1;

            // Continuously add new tickets to the system
            while (true) {
                // Generate a unique event name for the new ticket
                String eventName = "Event Ticket #" + ticketNumber++;

                // Add the new ticket to the system
                ticketService.createNewTicket(eventName);

                // Print a message indicating that the new ticket was added
                System.out.println("Vendor added: " + eventName);

                // Sleep for a duration based on the ticket release rate
                Thread.sleep(1000 / ticketReleaseRate); // Controls the release rate
            }
        } catch (InterruptedException e) {
            // Interrupt the current thread if an InterruptedException occurs
            Thread.currentThread().interrupt();
            System.out.println("Vendor thread interrupted");
        }
    }
}