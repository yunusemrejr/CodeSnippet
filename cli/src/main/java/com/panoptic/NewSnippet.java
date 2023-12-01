package com.panoptic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewSnippet {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].length() > 15) {
            String modifiedArgs = args[0].toLowerCase().replace("@@\"", "").replace("\"@@", "").replace("add", "");
            String patternForID = "#(\\w+)";
            Pattern r = Pattern.compile(patternForID);
            Matcher m = r.matcher(modifiedArgs);
            String snippetID = null;
        
            if(m.find()){
               snippetID =  m.group(1);
               modifiedArgs = modifiedArgs.replace("#"+snippetID, "");
               JsonObject jsonObject = new JsonObject();
               String snippet = modifiedArgs;
               jsonObject.setSnippet(snippet);
               jsonObject.setSnippetID(snippetID);
           
            
            ObjectMapper mapper = new ObjectMapper();
          
            try {
// Convert the modifiedArgs string to JSON
String json = mapper.writeValueAsString(jsonObject);

File jsonFile = new File("cli/src/main/java/com/panoptic/files/snippets.json");
JsonNode rootNode = mapper.readTree(jsonFile);

String lowercaseSnippetID = snippetID.toLowerCase(); // Convert to lowercase for case-insensitive comparison

if (!isSnippetIDInUse(rootNode, lowercaseSnippetID)) {
    if (rootNode.isArray()) {
        // If the root is an array, create a new object node
        ObjectNode newObjectNode = mapper.createObjectNode();
        newObjectNode.put("snippetID", snippetID);
        newObjectNode.put("snippet", snippet);

        // Add the new object node to the array
        ((ArrayNode) rootNode).add(newObjectNode);
    } else if (rootNode.isObject()) {
        // If the root is an object, simply add the new key-value pair
        ((ObjectNode) rootNode).put(snippetID, snippet);
    } else {
        // Handle other cases if needed
        System.out.println("(!) ERROR. Unsupported JSON root type.");
        return;
    }

    // Convert the updated JSON content to a string
    json = mapper.writeValueAsString(rootNode);

    // Write the updated JSON content back to the file
    mapper.writeValue(jsonFile, rootNode);
    System.out.println("NEW Snippet ADDED to list: " + json);
} else {
    System.out.println("ERROR (!): SNIPPET ID ALREADY IN USE! ");
}

               
            } catch (JsonProcessingException e) {
                System.out.println("(!) ERROR. Unable to convert to JSON: " + e.getMessage());
            }
             }else{
                System.out.println("ERROR finding snippet identifier/name/key.");
            }
        } else {
            System.out.println("(!) ERROR. YOU CANNOT SAVE A SNIPPET THAT SHORT!");
        }
    }

    private static boolean isSnippetIDInUse(JsonNode rootNode, String snippetID) {
        // Check if the snippet ID is already in use in a case-insensitive manner
        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                if (node.isObject() && node.has("snippetID")) {
                    String existingSnippetID = node.get("snippetID").textValue();
                    if (existingSnippetID != null && existingSnippetID.equalsIgnoreCase(snippetID)) {
                        return true;
                    }
                }
            }
        } else if (rootNode.isObject()) {
            return rootNode.has(snippetID);
        }
        return false;
    }
    
    static class JsonObject {
        private String snippetID;
        private String snippet;

        public String getSnippetID() {
            return snippetID;
        }

        public void setSnippetID(String snippetID) {
            this.snippetID = snippetID;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }
    }

}


