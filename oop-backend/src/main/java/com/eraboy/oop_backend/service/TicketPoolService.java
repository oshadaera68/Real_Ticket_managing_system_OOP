package com.eraboy.oop_backend.service;

import com.eraboy.oop_backend.entity.Ticket;
import com.eraboy.oop_backend.repo.TicketRepo;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/
@Service
public class TicketPoolService {
    private final TicketRepo ticketRepo;
    private final Queue<Ticket> tikQueue = new LinkedList<>();
    private int maxCapacity;

    public TicketPoolService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void initializeTicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(Ticket ticket) {
        while (tikQueue.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        tikQueue.add(ticket);
        ticketRepo.save(ticket);
        System.out.println("Ticket added: " + ticket);
        notifyAll();
    }

    public synchronized Ticket buyTicket() {
        while (tikQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = tikQueue.poll();
        notifyAll();
        return ticket;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
}