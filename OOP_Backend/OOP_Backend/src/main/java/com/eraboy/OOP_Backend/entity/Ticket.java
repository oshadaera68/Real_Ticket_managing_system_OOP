package com.eraboy.OOP_Backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v1.0.0
 **/

@Data
@NoArgsConstructor
@Component
public class Ticket {
    private static int ticketNo = 0;
    private String ticketId;
    private BigDecimal ticketPrice;
    private String eventName;

    public Ticket(BigDecimal ticketPrice, String eventName) {
        this.ticketId = "Ticket " + getNewId();
        this.ticketPrice = ticketPrice;
        this.eventName = eventName;
    }

    private int getNewId() {
        return ++Ticket.ticketNo;
    }
}