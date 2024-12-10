package com.eraboy.oop_backend.controller;

/**
 * Coded By: Era Boy
 * Version: v0.1.0
 **/

//@RestController
//@RequestMapping("/api/v1")
//public class ConfigurationController {
//    private final Configuration configuration;
//    private final ConfigRepo configRepo;
//
//    public ConfigurationController(Configuration configuration, ConfigRepo configRepo) {
//        this.configuration = configuration;
//        this.configRepo = configRepo;
//    }
//
//    // Export configuration to a JSON file
////    @PostMapping("/configuration/export")
////    public String exportConfiguration(@RequestParam(defaultValue = "config.json") String filePath) {
////        try {
////            configuration.saveToJson(filePath);
////            return "Configuration successfully exported to " + filePath;
////        } catch (IOException e) {
////            e.printStackTrace();
////            return "Error occurred while exporting configuration: " + e.getMessage();
////        }
////    }
//
//
//    // Import configuration from a JSON file
////    @GetMapping("/configuration/import")
////    public String importConfiguration(@RequestParam(defaultValue = "test.json") String filePath) {
////        try {
////            Configuration newConfig = Configuration.loadFromJson(filePath);
////
////            // Overwrite the current configuration
////            configuration.setTotalTickets(newConfig.getTotalTickets());
////            configuration.setTicketReleaseRate(newConfig.getTicketReleaseRate());
////            configuration.setCustomerRetrivalRate(newConfig.getCustomerRetrivalRate());
////            configuration.setMaxTicketCapacity(newConfig.getMaxTicketCapacity());
////
////            // Save updated configuration to the database
////            configRepo.save(configuration);
////
////            return "Configuration successfully imported from " + filePath + ": " + configuration;
////        } catch (IOException e) {
////            e.printStackTrace();
////            return "Error occurred while importing configuration: " + e.getMessage();
////        }
////    }
//
//}
