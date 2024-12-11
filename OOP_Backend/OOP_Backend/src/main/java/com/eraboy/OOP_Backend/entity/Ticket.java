package com.eraboy.OOP_Backend.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v1.0.0
 **/


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int ticketId;
private String event;
private BigDecimal price;

public Ticket(String event, BigDecimal price) {
    this.event = event;
    this.price = price;
}

public Ticket() {}

@Override
public String toString() {
    return "Ticket{" +
            "ticketId=" + ticketId +
            ", event='" + event + '\'' +
            ", price=" + price +
            '}';
}