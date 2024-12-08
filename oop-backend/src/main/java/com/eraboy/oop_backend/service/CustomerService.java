package com.eraboy.oop_backend.service;

import com.eraboy.oop_backend.entity.Ticket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@Service
public class CustomerService {
    private final TicketPoolService ticketPoolService;

    public CustomerService(TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    @Async
    public void startPurchasing(int retrivalRate, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Ticket ticket = ticketPoolService.buyTicket();
            System.out.println("Purchased Ticket: " + ticket);
            try {
                Thread.sleep(retrivalRate * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
