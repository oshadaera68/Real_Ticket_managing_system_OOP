package com.eraboy.oop_backend.service;

import com.eraboy.oop_backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Vendor implements Runnable {
    private final TicketService ticketService;
    private final int releaseRate; // Tickets per second

    @Autowired
    public Vendor(TicketService ticketService) {
        this.ticketService = ticketService;
        this.releaseRate = 2; // Example rate, adjust as needed
    }

    @Override
    public void run() {
        try {
            int ticketNumber = 1;
            while (true) {
                String eventName = "Event Ticket #" + ticketNumber++;
                ticketService.addTicket(eventName);
                System.out.println("Vendor added: " + eventName);
                Thread.sleep(1000 / releaseRate); // Controls the release rate
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Vendor thread interrupted");
        }
    }
}
