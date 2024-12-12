package com.eraboy.oop_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class represents a customer that can purchase tickets.
 * It implements the Runnable interface to allow it to run in a separate thread.
 */
@Component
public class Customer implements Runnable {

    // The TicketService instance used to interact with the ticket service
    private final TicketService ticketService;

    // The rate at which the customer attempts to purchase tickets
    private final int ticketRetrievalRate;

    /**
     * Constructs a new Customer instance with the provided TicketService instance.
     *
     * @param ticketService The TicketService instance to use
     */
    @Autowired
    public Customer(TicketService ticketService) {
        this.ticketService = ticketService;
        this.ticketRetrievalRate = 1; // Example rate, adjust as needed
    }

    /**
     * Runs the customer thread, which continuously attempts to purchase tickets.
     */
    @Override
    public void run() {
        try {
            // Continuously attempt to purchase tickets
            while (true) {
                // Retrieve all available tickets
                var availableTickets = ticketService.getAllTickets();

                // Check if there are any available tickets
                if (!availableTickets.isEmpty()) {
                    // Get the ID of the first available ticket
                    Long ticketId = availableTickets.get(0).getTicketId();

                    // Attempt to purchase the ticket
                    ticketService.sellExistingTicket(ticketId);

                    // Print a message indicating that the ticket was purchased
                    System.out.println("Customer bought ticket ID: " + ticketId);
                } else {
                    // Print a message indicating that there are no available tickets
                    System.out.println("No tickets available");
                }

                // Sleep for a duration based on the ticket retrieval rate
                Thread.sleep(1000 / ticketRetrievalRate); // Controls the retrieval rate
            }
        } catch (InterruptedException e) {
            // Interrupt the current thread if an InterruptedException occurs
            Thread.currentThread().interrupt();
            System.out.println("Customer thread interrupted");
        } catch (Exception e) {
            // Print an error message if an exception occurs while purchasing a ticket
            System.out.println("Error while purchasing ticket: " + e.getMessage());
        }
    }
}