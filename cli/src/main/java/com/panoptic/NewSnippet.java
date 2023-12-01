package com.panoptic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewSnippet {
    public static void main(String[] args) {
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

                // Print the JSON string to the console
                System.out.println("newSnippet: " + json);
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


