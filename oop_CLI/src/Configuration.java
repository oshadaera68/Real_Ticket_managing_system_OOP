import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

public class Configuration implements Serializable {
    // Number of tickets to be handled in total
    int totalTickets;

    // Rate at which tickets are released (in seconds)
    int ticketReleaseRate;

    // Rate at which customers retrieve tickets (in seconds)
    int customerRetrievalRate;

    // Maximum capacity of the ticket pool
    int maxTicketCapacity;

    /**
     * Loads configuration data from a JSON file.
     *
     * @param filePath The path to the JSON configuration file.
     * @return A Configuration object populated with data from the file.
     * @throws IOException If the file cannot be read or if the JSON format is invalid.
     */
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            // Deserialize the JSON content into a Configuration object
            return gson.fromJson(reader, Configuration.class);
        } catch (JsonSyntaxException e) {
            // Handle invalid JSON format errors
            throw new IOException("Invalid JSON format in configuration file.", e);
        }
    }

    /**
     * Saves the current configuration to a file in JSON format.
     *
     * @param filePath The path to the file where the configuration will be saved.
     * @throws IOException If the file cannot be written.
     */
    public void saveConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            // Serialize the Configuration object to JSON and write to the file
            gson.toJson(this, writer);
        }
    }
}