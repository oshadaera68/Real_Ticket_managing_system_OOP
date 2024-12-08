package com.eraboy.oop_backend.controller;

import com.eraboy.oop_backend.entity.Ticket;
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

    public TicketSystemController(TicketPoolService ticketPoolService, VendorService vendorService, CustomerService customerService) {
        this.ticketPoolService = ticketPoolService;
        this.vendorService = vendorService;
        this.customerService = customerService;
    }

    @PostMapping("/initialize")
    public String initializeSystem(@RequestParam int maxCapacity, @RequestParam int totalTickets, @RequestParam int ticketReleaseRate, @RequestParam int customerRetrivalRate) {
        ticketPoolService.initializeTicketPool(maxCapacity);
        vendorService.startVending(totalTickets, ticketReleaseRate);
        customerService.startPurchasing(customerRetrivalRate, 5);
        return "System Initialized";
    }

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() { return ticketPoolService.getAllTickets(); }
}
