package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CreateJSON {

    public static void main(String[] args) throws IOException {
    	//writer();
    	//reader();
    }
    
    public static void writer() throws IOException {	
	  Gson gson = new Gson();  
        
	  // convert java object to JSON format,  
	  // and returned as JSON formatted string  
	  String json = gson.toJson(Main.library);  
	    
	  try {  
	   //write converted json data to a file named "CountryGSON.json"  
	   FileWriter writer = new FileWriter("Output.json");  
	   writer.write(json);  
	   writer.close();  
	    
	  } catch (IOException e) {  
	   e.printStackTrace();  
	  }  
	    
	  System.out.println(json);  

    }
    public static void reader () throws IOException {
    	 File myFile = new File("./Output.json");
         FileInputStream fIn = new FileInputStream(myFile);
         BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
         String aDataRow = "";
         String aBuffer = ""; //Holds the text
         while ((aDataRow = myReader.readLine()) != null) 
         {
             aBuffer += aDataRow ;
         }
         System.out.println(aBuffer);
         myReader.close();
         
         
        
    }
}