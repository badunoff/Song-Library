package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.view.LayoutController;


public class CreateJSON {
	
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
    	 SongLibrary library = Main.library;
    	 try {
	    	 File myFile = new File("./Output.json");
	         FileInputStream fIn = new FileInputStream(myFile);
	         BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
	         String aDataRow = "";
	         String aBuffer = ""; //Holds the text
	         while ((aDataRow = myReader.readLine()) != null) 
	         {
	        	 aBuffer += aDataRow ;
	         }
	         String[] parts = aBuffer.split("\\{|\\}");
	         int l = parts.length;
	         for (int i = 3; i < l-1; i+=2) {
	             try {
					songObjectCreator(parts[i], library);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	         }
	         myReader.close();
    	 }
         catch (Exception ex) {
             System.out.println("Welcome, first time user!");
         }
    }
    
    private static void songObjectCreator (String part, SongLibrary lib) throws NumberFormatException, Exception {
    	Gson ggson = new GsonBuilder().create();
    	String first = "{";
    	first += part;
    	String end = "}";
    	first += end;
		Song address=ggson.fromJson(first, Song.class);
		if (address.getYear() != "") {
			lib.add(address.getTitle(), address.getArtist(), address.getAlbum(), Integer.parseInt(address.getYear()) );
		}
		else {
			lib.add(address.getTitle(), address.getArtist(), address.getAlbum() );
		}
    }
}