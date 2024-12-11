package com.eraboy.oop_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customer implements Runnable {
    private final TicketService ticketService;
    private final int retrievalRate;

    @Autowired
    public Customer(TicketService ticketService) { //remove int customerRetrievalRate
        this.ticketService = ticketService;
        this.retrievalRate = 1; // Example rate, adjust as needed
    }

    @Override
    public void run() {
        try {
            while (true) {

                var tickets = ticketService.getTickets();
                if (!tickets.isEmpty()) {
                    Long ticketId = tickets.get(0).getId();
                    ticketService.sellTicket(ticketId);
                    System.out.println("Customer bought ticket ID: " + ticketId);
                } else {
                    System.out.println("No tickets available");
                }
                Thread.sleep(1000 / retrievalRate); // Controls the retrieval rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Customer thread interrupted");
        } catch (Exception e) {
            System.out.println("Error while purchasing ticket: " + e.getMessage());
        }
    }
}