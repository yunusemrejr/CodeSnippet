package com.panoptic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public class CloneSnippet {
    public static void main(String[] args) throws IOException {
 
        String sourceSnippetID = args[1];
        String copySnippetID = args[3];
        sourceSnippetID = sourceSnippetID.replace("clone ", "");
        copySnippetID = copySnippetID.replace("as ", "");

        if (sourceSnippetID.startsWith("#")) {
            sourceSnippetID = sourceSnippetID.substring(1); // Remove the '#' prefix
        }
        if (copySnippetID.startsWith("#")) {
            copySnippetID = copySnippetID.substring(1); // Remove the '#' prefix
        }

        File jsonFile = new File("cli/src/main/java/com/panoptic/files/snippets.json");
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(jsonFile);

            if (rootNode.isArray()) {
                ArrayNode newArrayNode = mapper.createArrayNode();

                for (JsonNode node : rootNode) {
                    if (node.isObject() && node.has("snippetID")) {
                        String existingSnippetID = node.get("snippetID").textValue();
                        if (existingSnippetID != null && existingSnippetID.equalsIgnoreCase(sourceSnippetID)) {
                            // Clone the snippet and change its ID
                            ObjectNode copyNode = (ObjectNode) node.deepCopy();
                            copyNode.put("snippetID", copySnippetID);
                            newArrayNode.add(copyNode);
                        }
                    }
                    newArrayNode.add(node);
                }

                // Convert the updated JSON content to a string
                String updatedJson = mapper.writeValueAsString(newArrayNode);

                // Write the updated JSON content back to the file
                mapper.writeValue(jsonFile, newArrayNode);
                System.out.println("Snippet with ID " + sourceSnippetID + " cloned as " + copySnippetID);
            } else {
                System.out.println("(!) ERROR. JSON root is not an array.");
            }
        } catch (IOException e) {
            System.out.println("(!) ERROR. Unable to read/write JSON file: " + e.getMessage());
        }
    }
}
