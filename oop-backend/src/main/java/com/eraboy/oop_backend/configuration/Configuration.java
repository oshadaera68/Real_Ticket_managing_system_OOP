package com.eraboy.oop_backend.configuration;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "system")
public class Configuration {
    // Getters and Setters
    @Min(value = 1, message = "Total tickets must be greater than 0")
    private int totalTickets;

    @Min(value = 1, message = "Ticket release rate must be greater than 0")
    private int ticketReleaseRate;

    @Min(value = 1, message = "Customer retrieval rate must be greater than 0")
    private int customerRetrivalRate;

    @Min(value = 1, message = "Max ticket capacity must be greater than 0")
    private int maxTicketCapacity;

    // Save configuration to a JSON file
    /*public void saveToJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
        System.out.println("Configuration successfully written to " + file.getAbsolutePath());
    }*/

    // Load configuration from a JSON file
   /* public static Configuration loadFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Configuration config = objectMapper.readValue(new File(filePath), Configuration.class);

        // Debug log to verify loaded values
        System.out.println("Loaded Configuration from JSON: " + config);
        return config;
    }*/

    @Override
    public String toString() {
        return "Configuration{" + "totalTickets=" + totalTickets + ", ticketReleaseRate=" + ticketReleaseRate + ", customerRetrivalRate=" + customerRetrivalRate + ", maxTicketCapacity=" + maxTicketCapacity + '}';
    }
}