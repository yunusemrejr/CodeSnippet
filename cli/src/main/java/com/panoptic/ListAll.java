package com.panoptic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ListAll {
    public static void main(String[] args) throws IOException {
        File jsonFile = new File("cli/src/main/java/com/panoptic/files/snippets.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(jsonFile);

            if (rootNode.isArray()) {
                System.out.println("List of Snippets:");
                for (JsonNode node : rootNode) {
                    if (node.isObject() && node.has("snippetID") && node.has("snippet")) {
                        String snippetID = node.get("snippetID").textValue();
                        String snippet = node.get("snippet").textValue();
                        System.out.println("Snippet ID: " + snippetID);
                        System.out.println("Snippet: " + snippet);
                        System.out.println();
                    }
                }
            } else {
                System.out.println("(!) ERROR. JSON root is not an array.");
            }
        } catch (IOException e) {
            System.out.println("(!) ERROR. Unable to read JSON file: " + e.getMessage());
        }
    }
}
