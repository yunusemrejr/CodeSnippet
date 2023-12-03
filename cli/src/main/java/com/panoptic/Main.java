package com.panoptic;

import java.io.IOException;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) throws IOException{
        
        System.out.println("\nWelcome to The Panoptic Pen Code Snipper Manager / www.panopticpen.space / v.1.0.0 by Yunus Emre Vurgun - Nov. 2023\n");
        System.out.println("*********************************\n");
        System.out.println("To view the About section, type INFO, \nto view the list of commands, type COMMANDS, \nto exit the application, type EXIT,\nif you are a returning user, continue as usual.\n");
        
        runScannerListening();
 
}


    public static  void runScannerListening() throws IOException{     
                Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("> ");
            String input = scanner.nextLine();

            switch(input.toLowerCase()){
                case "exit":
                    System.out.println("Your entered: "+input+"\n");
                    System.out.println("exiting...\n");
                    scanner.close();
                    System.exit(0);
                    break;

                case "info":
                    System.out.println("Your entered: "+input+"\n");
                    ReadTxt.main(new String[]{"cli/src/main/java/com/panoptic/files/about.txt"});
                    continue;

                case "commands":
                System.out.println("Your entered: "+input+"\n");
                     ReadTxt.main(new String[]{"cli/src/main/java/com/panoptic/files/commands.txt"});
                    continue;

                case "add":
                System.out.println("Your need to provide content to add as a snippet. Type COMMANDS to see how.\n");
                    
                    
                    continue;

         

                case "listall":
                System.out.println("Your entered: "+input+"\n");
                    ListAll.main(new String[]{});
                     
                    continue;

                default:
                if (input.toLowerCase().matches("(?i)add\\s+#\\w+\\s+@@\"[^\"@@]+\"@@")) {
                  NewSnippet.main(new String[]{input});
                  }
                  else if(input.toLowerCase().matches("(?i)delete\\s+#\\w+")){
                   DeleteSnippet.main(new String[]{input.toLowerCase()});
                  }
                  else if(input.toLowerCase().matches("(?i)view\\s+#\\w+")){
                   ViewSnippet.main(new String[]{input.toLowerCase()});
                  }
                  else if (input.toLowerCase().matches("(?i)clone\\s+#\\w+\\s+as\\s+#\\w+")) {
                    CloneSnippet.main(input.toLowerCase().split("\\s+"));
                }
                
                
                  else{
                    System.out.println("(!) ** Invalid command ** : "+input+"\n");
                  }
                  
                  continue;            
                
                }

                

        }
     
       // scanner.close();
        
    }
}