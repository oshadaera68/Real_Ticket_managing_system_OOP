package com.eraboy.oop_backend.configuration;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

@Component
@ConfigurationProperties(prefix = "system")
@Getter
@Setter
public class Configuration {
    @Min(value = 1, message="Total tickets must be greater than 0")
    private int totalTickets;
    @Min(value = 1, message="Total tickets must be greater than 0")
    private int ticketReleaseRate;
    @Min(value = 1, message="Total tickets must be greater than 0")
    private int customerRetrievalRate;
    @Min(value = 1, message="Total tickets must be greater than 0")
    private int maxTicketCapacity;
}
