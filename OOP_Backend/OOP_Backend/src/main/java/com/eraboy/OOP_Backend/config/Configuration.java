package com.eraboy.OOP_Backend.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
public class Configuration {
    @Id
    private int ticketId;
    private int poolSize = 0;
    private BigDecimal ticketPrice;
    private String eventName;
    private int ticketsPerVendor;
    private int releaseRate;
    private int buyRate;
    private int vendors;
    private int customers;
}

