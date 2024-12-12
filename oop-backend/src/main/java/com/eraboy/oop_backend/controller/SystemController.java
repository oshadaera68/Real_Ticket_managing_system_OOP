package com.eraboy.oop_backend.controller;

import com.eraboy.oop_backend.model.Configuration;
import com.eraboy.oop_backend.model.Ticket;
import com.eraboy.oop_backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller for managing tickets and system configuration.
 * It provides endpoints for creating, retrieving, and selling tickets, as well as getting and updating system configuration.
 */
@RestController
@RequestMapping("/api/tickets")  // Base URL for ticket-related actions
@CrossOrigin(origins = "http://localhost:5173")
public class SystemController {

    // The TicketService instance used to perform business logic operations on tickets
    @Autowired
    private TicketService ticketService;

    // The Configuration instance used to store and retrieve system configuration
    @Autowired
    private Configuration systemConfiguration;

    /**
     * Creates a new ticket with the specified event name.
     *
     * @param eventName The name of the event for which the ticket is being created
     * @return The newly created ticket
     */
    @PostMapping("/create")
    public Ticket createNewTicket(@RequestParam String eventName) {
        // Create a ticket with a specified event name
        return ticketService.createNewTicket(eventName);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of all tickets
     */
    @GetMapping("/all")
    public List<Ticket> getAllExistingTickets() {
        // Get all tickets from the database
        return ticketService.getAllTickets();
    }

    /**
     * Sells a ticket with the specified ID.
     *
     * @param ticketId The ID of the ticket to be sold
     * @return The sold ticket
     * @throws Exception If the ticket cannot be sold (e.g., if it is already sold)
     */
    @PostMapping("/sell/{ticketId}")
    public Ticket sellExistingTicket(@PathVariable Long ticketId) throws Exception {
        // Sell the ticket by ID
        return ticketService.sellExistingTicket(ticketId);
    }

    /**
     * Retrieves the current system configuration.
     *
     * @return The current system configuration
     */
    @GetMapping("/config")
    public Configuration getSystemConfiguration() {
        // Retrieve the configuration object
        return systemConfiguration;
    }

    /**
     * Updates the system configuration with the provided values.
     *
     * @param newConfiguration The new system configuration values
     * @return The updated system configuration
     */
    @PutMapping("/configuration")
    public Configuration updateSystemConfiguration(@RequestBody Configuration newConfiguration) {
        // Update configuration values based on the input from the client
        systemConfiguration.setTotalTickets(newConfiguration.getTotalTickets());
        systemConfiguration.setTicketReleaseRate(newConfiguration.getTicketReleaseRate());
        systemConfiguration.setCustomerRetrievalRate(newConfiguration.getCustomerRetrievalRate());
        systemConfiguration.setMaxTicketCapacity(newConfiguration.getMaxTicketCapacity());

        // Optionally save the configuration back to the file or database
        systemConfiguration.saveToFile("config.json");

        return systemConfiguration;
    }
}