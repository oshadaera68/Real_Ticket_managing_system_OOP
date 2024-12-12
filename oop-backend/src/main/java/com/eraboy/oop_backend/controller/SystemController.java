package com.eraboy.oop_backend.controller;

import com.eraboy.oop_backend.model.Configuration;
import com.eraboy.oop_backend.model.Ticket;
import com.eraboy.oop_backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles all the ticket-related operations in the system.
 * Provides endpoints to manage ticket creation, selling, retrieval, and system configuration.
 */
@RestController
@RequestMapping("/api/tickets") // Base URL for ticket-related actions
@CrossOrigin("http://localhost:5173/") // Allow requests from the frontend on localhost:5173
public class SystemController {

    @Autowired
    private TicketService ticketService; // Service class to handle business logic for tickets

    @Autowired
    private Configuration configuration; // Configuration object to handle system settings

    /**
     * Endpoint to create a new ticket.
     *
     * @param eventName The name of the event for which the ticket is being created.
     * @return The created ticket.
     */
    @PostMapping("/create")
    public Ticket createTicket(@RequestParam String eventName) {
        // Create a ticket with a specified event name and return the created ticket
        return ticketService.addTicket(eventName);
    }

    /**
     * Endpoint to retrieve all tickets.
     *
     * @return A list of all tickets.
     */
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        // Retrieve and return all tickets from the database
        return ticketService.getTickets();
    }

    /**
     * Endpoint to sell a ticket by marking it as sold.
     *
     * @param id The ID of the ticket to sell.
     * @return The sold ticket.
     * @throws Exception If an error occurs while selling the ticket.
     */
    @PostMapping("/sell/{id}")
    public Ticket sellTicket(@PathVariable Long id) throws Exception {
        // Sell the ticket by its ID and return the updated ticket
        return ticketService.sellTicket(id);
    }

    /**
     * Endpoint to get the current configuration settings.
     *
     * @return The configuration object containing system settings.
     */
    @GetMapping("/config")
    public Configuration getConfiguration() {
        // Return the current configuration settings
        return configuration;
    }

    /**
     * Endpoint to update the configuration settings.
     *
     * @param newConfig A Configuration object with the updated settings.
     * @return The updated configuration.
     */
    @PutMapping("/configuration")
    public Configuration updateConfiguration(@RequestBody Configuration newConfig) {
        // Update the system's configuration with new values
        configuration.setTotalTickets(newConfig.getTotalTickets());
        configuration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(newConfig.getCustomerRetrievalRate());
        configuration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());

        // Optionally save the updated configuration to a file or database
        configuration.saveToFile("config.json");

        return configuration; // Return the updated configuration
    }
}
