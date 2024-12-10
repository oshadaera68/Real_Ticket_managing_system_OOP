package com.eraboy.oop_backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private String eventName;
    private BigDecimal ticketPrice;

    public Ticket() {
    }

    public Ticket(String eventName, BigDecimal ticketPrice) {
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }
}
