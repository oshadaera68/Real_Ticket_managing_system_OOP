import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public int totalTickets;
    public int ticketReleaseRate;
    public int customerRetrievalRate;
    public int maxTicketCapacity;

    /**
     * Loads configuration from a JSON file.
     */
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        }
    }

    /**
     * Saves the current configuration to a JSON file.
     */
    public void saveConfiguration(String filePath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
        }
    }
}