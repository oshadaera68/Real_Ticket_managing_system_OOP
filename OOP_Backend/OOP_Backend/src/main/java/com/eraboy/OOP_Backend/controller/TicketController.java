package com.eraboy.OOP_Backend.controller;

import com.eraboy.OOP_Backend.config.Configuration;
import com.eraboy.OOP_Backend.entity.Ticket;
import com.eraboy.OOP_Backend.service.CustomerService;
import com.eraboy.OOP_Backend.service.TicketPoolService;
import com.eraboy.OOP_Backend.service.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*Coded By: Era Boy
*Version: v0.1.0
**/

@RestController
@RequestMapping("/api")
public class TicketController {
    private Configuration config;
    private TicketPoolService ticketPool;
    private Ticket ticket;

    public TicketController(Configuration config, TicketPoolService ticketPool, Ticket ticket) {
        this.config = config;
        this.ticketPool = ticketPool;
        this.ticket = ticket;
//        System.out.println("At TicketController constructor");
    }

    //    @RequestMapping("/show")
    public void runSystem() {
//        System.out.println("At runSystem");
        for (int i = 1; i <= config.getVendors(); i++) {
            VendorService vendors = new VendorService(this.ticketPool, this.ticket, this.config);
            Thread vendorThread = new Thread(vendors);
            vendorThread.setName("Vendor " + i);
//            System.out.println("Starting Vendor Thread");
            vendorThread.start();
        }

        for (int i = 1; i <= config.getCustomers(); i++) {
            Customer customer = new Customer(this.ticketPool, this.ticket, this.config);
            Thread customerThread = new Thread(customer);
            customerThread.setName("Customer " + i);
//            System.out.println("Starting Customer Thread");
            customerThread.start();
        }
    }
}