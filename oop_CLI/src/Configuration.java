import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.Scanner;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Configuration implements Serializable {
    int totalTickets;
    int ticketReleaseRate;
    int customerRetrievalRate;
    int maxTicketCapacity;

    // Save configuration to a file in JSON format
    public void saveConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }
    }

    // Load configuration from a JSON file
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("Invalid JSON format in configuration file.", e);
        }
    }
}