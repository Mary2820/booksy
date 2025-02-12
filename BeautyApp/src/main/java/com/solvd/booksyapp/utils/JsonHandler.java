package com.solvd.booksyapp.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.solvd.booksyapp.wrappers.BooksyDataWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class JsonHandler {
    private static final Logger logger = LogManager.getLogger(JsonHandler.class.getName());
    private static final String INPUT_PATH = "src/main/resources/jsonfiles/booksy_data.json";
    private static final String OUTPUT_PATH = "src/main/resources/jsonfiles/marshaled_data.json";

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        try {
            File inputFile = new File(INPUT_PATH);
            if (!inputFile.exists()) {
                logger.error("Input file not found: {}", INPUT_PATH);
                return;
            }

            File outputFile = new File(OUTPUT_PATH);

            logger.info("Reading JSON from {}", INPUT_PATH);
            BooksyDataWrapper data = mapper.readValue(inputFile, BooksyDataWrapper.class);
            logger.info("Successfully read JSON");

            logger.info("Writing JSON to {}", OUTPUT_PATH);
            mapper.writeValue(outputFile, data);
            logger.info("Successfully wrote JSON");

        } catch (Exception e) {
            logger.error("Error processing JSON: {}", e.getMessage());
        }
    }
} 