package com.panoptic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ViewSnippet {
    public static void main(String[] args) throws IOException {
    

        String snippetIDToView = args[0];
        snippetIDToView = snippetIDToView.replace("view ", "");
        if (snippetIDToView.startsWith("#")) {
            snippetIDToView = snippetIDToView.substring(1); // Remove the '#' prefix
        }

        File jsonFile = new File("cli/src/main/java/com/panoptic/files/snippets.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(jsonFile);

            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    if (node.isObject() && node.has("snippetID")) {
                        String existingSnippetID = node.get("snippetID").textValue();
                        if (existingSnippetID != null && existingSnippetID.equalsIgnoreCase(snippetIDToView)) {
                            // Print the details of the found snippet
                            System.out.println("Snippet ID: " + existingSnippetID);
                            if (node.has("snippet")) {
                                String snippetContent = node.get("snippet").textValue();
                                System.out.println("Snippet Content: " + snippetContent);
                            } else {
                                System.out.println("Snippet Content not found.");
                            }
                            return;
                        }
                    }
                }
                System.out.println("(!) ERROR. Snippet with ID " + snippetIDToView + " not found.");
            } else {
                System.out.println("(!) ERROR. JSON root is not an array.");
            }
        } catch (IOException e) {
            System.out.println("(!) ERROR. Unable to read JSON file: " + e.getMessage());
        }
    }
}
