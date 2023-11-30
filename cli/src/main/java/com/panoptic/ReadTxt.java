package com.panoptic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadTxt {
    public static void main(String[] args){
      
        if(args.length ==0){
            System.out.println("err");
            return;
        }

        String filePath = args[0];

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
          String line;
          while((line=br.readLine()) !=null){
            System.out.println(line);
          }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
