package com.eraboy.oop_backend.controller;

import com.eraboy.oop_backend.entity.Ticket;
import com.eraboy.oop_backend.repo.TicketRepo;
import com.eraboy.oop_backend.service.CustomerService;
import com.eraboy.oop_backend.service.TicketPoolService;
import com.eraboy.oop_backend.service.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@RestController
@RequestMapping("/api")
public class TicketSystemController {
    private final TicketPoolService ticketPoolService;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final TicketRepo ticketRepo;

    public TicketSystemController(TicketPoolService ticketPoolService, VendorService vendorService, CustomerService customerService, TicketRepo ticketRepo) {
        this.ticketPoolService = ticketPoolService;
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/initialize")
    public String initializeSystem(@RequestParam int maxTicketCapacity,
                                   @RequestParam int totalTickets,
                                   @RequestParam int ticketReleaseRate,
                                   @RequestParam int customerRetrivalRate) {
        ticketPoolService.initializeTicketPool(maxTicketCapacity);
        vendorService.startVending(totalTickets, ticketReleaseRate);
        customerService.startPurchasing(customerRetrivalRate, 5);
        return "System Initialized";
    }

    @GetMapping("/tickets")
    public List<Ticket> getTickets() {
        return ticketPoolService.getAllTickets();
    }

    @PostMapping("/tickets")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        ticketPoolService.addTicket(ticket);
        return ticket;
    }

}
