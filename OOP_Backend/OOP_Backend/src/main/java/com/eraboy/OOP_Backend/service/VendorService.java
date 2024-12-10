package com.eraboy.OOP_Backend.service;

import com.eraboy.OOP_Backend.config.Configuration;
import com.eraboy.OOP_Backend.entity.Ticket;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@NoArgsConstructor
@Service
public class VendorService implements Runnable {
    private TicketPoolService ticketPool;
    private Ticket ticket;
    private Configuration config;

    public VendorService(TicketPoolService ticketPool, Ticket ticket, Configuration config) {
        this.ticketPool = ticketPool;
        this.ticket = ticket;
        this.config = config;
    }

    @Override
    public void run() {
        try {
            for (int ticketNum = 1; ticketNum <= config.getTicketsPerVendor(); ticketNum++) {
                ticket = new Ticket(config.getTicketPrice(), config.getEventName());
                ticketPool.addTickets(ticket);

                try {
                    Thread.sleep(1000L * config.getReleaseRate());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception " + e + " at Vendor run()");
        }
    }
}
