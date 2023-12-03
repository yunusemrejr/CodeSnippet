package com.panoptic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;

public class DeleteSnippet {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: DELETE name_of_snippet or DELETE #name_of_snippet");
            return;
        }

        String snippetNameToDelete = args[0];
        snippetNameToDelete = snippetNameToDelete.replace("delete ", "");
        if (snippetNameToDelete.startsWith("#")) {
            snippetNameToDelete = snippetNameToDelete.substring(1); // Remove the '#' prefix
            System.out.println(snippetNameToDelete);
        }
         File jsonFile = new File("cli/src/main/java/com/panoptic/files/snippets.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(jsonFile);

            if (rootNode.isArray()) {
                int indexToDelete = -1;
                for (int i = 0; i < rootNode.size(); i++) {
                    JsonNode node = rootNode.get(i);
                    if (node.isObject() && node.has("snippetID")) {
                        String existingSnippetID = node.get("snippetID").textValue();
                        if (existingSnippetID != null && existingSnippetID.equalsIgnoreCase(snippetNameToDelete)) {
                            indexToDelete = i;
                            break;
                        }
                    }
                }

                if (indexToDelete >= 0) {
                    ((ArrayNode) rootNode).remove(indexToDelete);
                    mapper.writeValue(jsonFile, rootNode);
                    System.out.println("Snippet with ID " + snippetNameToDelete + " deleted successfully.");
                } else {
                    System.out.println("(!) ERROR. Snippet with ID " + snippetNameToDelete + " not found.");
                }
            } else {
                System.out.println("(!) ERROR. JSON root is not an array.");
            }
        } catch (IOException e) {
            System.out.println("(!) ERROR. Unable to read/write JSON file: " + e.getMessage());
        }
    }
}
