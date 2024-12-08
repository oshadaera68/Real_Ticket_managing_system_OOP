package com.eraboy.oop_backend.service;

import com.eraboy.oop_backend.entity.Ticket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@Service
public class VendorService {
    private final TicketPoolService ticketPoolService;

    public VendorService(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    @Async
    public void startVending(int totalTickets, int releaseRate) {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = new Ticket("Event-" + i, new BigDecimal("100"));
            ticketPoolService.addTicket(ticket);
            try {
                Thread.sleep(releaseRate * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
