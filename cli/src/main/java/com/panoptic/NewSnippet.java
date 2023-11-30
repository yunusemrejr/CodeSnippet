package com.panoptic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class NewSnippet {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].length() > 15) {
            String modifiedArgs = args[0].replace("add @@\"", "").replace("\"@@", "");

            ObjectMapper mapper = new ObjectMapper();

            try {
                // Convert the modifiedArgs string to JSON
                String json = mapper.writeValueAsString(modifiedArgs);

                // Print the JSON string to the console
                System.out.println("newSnippet: " + json);
            } catch (JsonProcessingException e) {
                System.out.println("(!) ERROR. Unable to convert to JSON: " + e.getMessage());
            }
        } else {
            System.out.println("(!) ERROR. YOU CANNOT SAVE A SNIPPET THAT SHORT!");
        }
    }
}
