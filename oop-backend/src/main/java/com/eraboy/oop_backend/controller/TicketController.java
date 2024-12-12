package com.eraboy.oop_backend.controller;

import com.eraboy.oop_backend.model.Ticket;
import com.eraboy.oop_backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 * /

/**
 * This class is a REST controller for managing tickets.
 * It provides endpoints for creating, retrieving, and selling tickets.
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    // The TicketService instance used to perform business logic operations on tickets
    @Autowired
    private TicketService ticketService;

    /**
     * Creates a new ticket with the specified event name.
     *
     * @param eventName The name of the event for which the ticket is being created
     * @return The newly created ticket
     */
    @PostMapping
    public Ticket createTicket(@RequestParam String eventName) {
        return ticketService.createNewTicket(eventName);
    }

    /**
     * Retrieves a list of all tickets.
     *
     * @return A list of all tickets
     */
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    /**
     * Sells a ticket with the specified ID.
     *
     * @param ticketId The ID of the ticket to be sold
     * @return The sold ticket
     * @throws Exception If the ticket cannot be sold (e.g., if it is already sold)
     */
    @PostMapping("/{ticketId}/sell")
    public Ticket sellTicket(@PathVariable Long ticketId) throws Exception {
        return ticketService.sellExistingTicket(ticketId);
    }
}