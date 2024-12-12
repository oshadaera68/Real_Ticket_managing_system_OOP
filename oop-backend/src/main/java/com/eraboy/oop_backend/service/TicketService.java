package com.eraboy.oop_backend.service;

import com.eraboy.oop_backend.model.Ticket;
import com.eraboy.oop_backend.repo.TicketRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is a service for managing tickets.
 * It provides methods for adding, retrieving, and selling tickets.
 */
@Service
public class TicketService {

    // The logger instance used to log events in the service
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    // The TicketRepo instance used to interact with the ticket repository
    private final TicketRepo ticketRepository;

    /**
     * Constructs a new TicketService instance with the provided TicketRepo instance.
     *
     * @param ticketRepository The TicketRepo instance to use
     */
    @Autowired
    public TicketService(TicketRepo ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Creates a new ticket with the specified event name and adds it to the repository.
     *
     * @param eventName The name of the event for which the ticket is being created
     * @return The newly created ticket
     */
    public Ticket createNewTicket(String eventName) {
        // Create a new ticket with the specified event name
        Ticket newTicket = new Ticket(eventName);
        // Save the new ticket to the repository
        return ticketRepository.save(newTicket);
    }

    /**
     * Retrieves a list of all tickets from the repository.
     *
     * @return A list of all tickets
     */
    public List<Ticket> getAllTickets() {
        // Retrieve all tickets from the repository
        return ticketRepository.findAll();
    }

    /**
     * Sells a ticket with the specified ID.
     *
     * @param ticketId The ID of the ticket to be sold
     * @return The sold ticket
     * @throws Exception If the ticket cannot be sold (e.g., if it is already sold or not found)
     */
    public Ticket sellExistingTicket(Long ticketId) throws Exception {
        // Retrieve the ticket with the specified ID from the repository
        Ticket ticketToSell = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception("Ticket with ID " + ticketId + " not found"));

        // Check if the ticket is already sold
        if (ticketToSell.isSold()) {
            throw new Exception("Ticket with ID " + ticketId + " is already sold");
        }

        // Mark the ticket as sold
        ticketToSell.setSold(true);
        // Save the updated ticket to the repository
        ticketRepository.save(ticketToSell);

        // Log the successful sale of the ticket
        logger.info("Ticket with ID {} sold successfully", ticketId);

        return ticketToSell;
    }
}