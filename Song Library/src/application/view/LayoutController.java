package application.view;

import java.io.IOException;
import java.util.Map.Entry;

import application.CreateJSON;
import application.Main;
import application.Song;
import application.SongLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LayoutController {
	String orig_title;
	String orig_artist;
	
	@FXML Button addSong;
	@FXML Button edit;
	@FXML Button delete;
	
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	@FXML ListView<String> songs;
	
	public void handleMouseClick(MouseEvent arg0) {
				SongLibrary library = Main.library;
				String key = songs.getSelectionModel().getSelectedItem().toLowerCase();
				Song song = library.getSong(key); 
						
				System.out.println("clicked on " + key);
				
				orig_title = song.getTitle();
				orig_artist = song.getArtist();
				edit.setText("Edit");				
				
		 	    title.setText(song.getTitle());
		 		artist.setText(song.getArtist());
		 		album.setText(song.getAlbum());
		 		year.setText(song.getYear());
		 	}
	
	public void add(ActionEvent e) {
		System.out.println("ADD");
		
		add();
	}
	
	public void edit(ActionEvent e) {
		System.out.println("EDIT");
		
		//Button b = (Button)e.getSource();
		//title.setText(b.textProperty().getValueSafe());
		
		edit();
		
	}
	
	public void delete(ActionEvent e) {
		System.out.println("DELETE");
		
		delete();
	}
	
	
	
	
	
	
	
	
	
	
	
	//maybe bad style
	/*------------------------
	 * Interacting with library methods 
	 * -----------------------*/
	
	
	private void add(){
		orig_title = "";
		orig_artist = "";
		
		title.setText("");
		artist.setText("");
		album.setText("");
		year.setText("");
		
		title.setEditable(true);
		artist.setEditable(true);
		album.setEditable(true);
		year.setEditable(true);
		edit.setText("Save");
	}
	
	private void edit(){
		ObservableList<String> obsList = FXCollections.observableArrayList();
		Song song;
		
		if(edit.textProperty().getValueSafe().equals("Edit")){
			edit.setText("Save");
			
			orig_title = title.getText();
			orig_artist = artist.getText();
			
			title.setEditable(true);
			artist.setEditable(true);
			album.setEditable(true);
			year.setEditable(true);
		}
		else{//SAVE
			//makes me uncomfortable
			SongLibrary library = Main.library;
			
			System.out.println("Original Title: " + orig_title);
			System.out.println("Original Artist: " + orig_artist);
			
			if(title.getText().trim().equals("") || artist.getText().trim().equals("")){
				// TODO needs to be pop-out
				inputError("Please enter both title and artist.");
				System.out.println("invalid submission");
				return;
			}
			
			title.setEditable(false);
			artist.setEditable(false);
			album.setEditable(false);
			year.setEditable(false);
			
			String titleL = title.getText();
			String artistL = artist.getText();
			String albumL = album.getText();
			if(albumL == null){
				albumL = "";
			}
			
			String yearL_string = year.getText().trim();
			int yearL;
			
			System.out.println("title input: " + titleL);
			System.out.println("artist input: " + artistL);
			System.out.println("album input: " + albumL);
			System.out.println("year input: " + yearL_string);
			
			
			if(yearL_string.trim().equals("") || yearL_string.trim() == null){//no year entered
				try {
					if(orig_title.equals("") && orig_artist.equals("")){
						song = library.add(titleL, artistL, albumL);
					}
					else{
						song = library.edit(orig_title, orig_artist, titleL, artistL, albumL);
					}
					
					for(String key : library.getKeys()){
						song = library.getSong(key);		
						obsList.add(song.getTitle() + " - " + song.getArtist());
					}
					
					songs.setItems(obsList);
				} catch (Exception e) {
					System.out.println("Song already exists");
					inputError("Song already exists");
				}
			}
			else{//year entered
				try{
					yearL = Integer.parseInt(yearL_string);
					// TODO nested try-catch sucks
					try{
						if(orig_title.equals("") && orig_artist.equals("")){//adding
							System.out.println("adding to library");
							song = library.add(titleL, artistL, albumL, yearL);
						}
						else{//Editing
							song = library.edit(orig_title, orig_artist, titleL, artistL, albumL, yearL);
						}
						
						for(String key : library.getKeys()){
							song = library.getSong(key);		
							obsList.add(song.getTitle() + " - " + song.getArtist());
						}
						
						songs.setItems(obsList);
					}
					catch(Exception e){
						System.out.println("Song already exists");
						inputError("Song already exists");
					}
				}catch(Exception e){
					// TODO need to change this whole thing. If the song already exists, then it will trigger this too. Maybe create different kinds of exceptions with many catches
					System.out.println("Invalid Year");
					inputError("Invalid Year");
					title.setEditable(true);
					artist.setEditable(true);
					album.setEditable(true);
					year.setEditable(true);
				}
			}
			
			
			
	
			edit.setText("Edit");
			try {
				CreateJSON.writer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(String key : library.getKeys()){
				System.out.println(library.getSong(key));
			}
			
			for(String key : library.getKeys()){
				System.out.println(library.getSong(key));
			}
			
		}
	}
	
	private void delete(){
		SongLibrary library = Main.library;
		ObservableList<String> obsList = FXCollections.observableArrayList();
		Song song;
		
		String titleL = title.getText();
		String artistL = artist.getText();
		
		title.setText("");
		artist.setText("");
		album.setText("");
		year.setText("");
		
		edit.setText("Edit");
		
		library.deleteSong(titleL, artistL);
		
		orig_title = "";
		orig_artist = "";
		
		for(String key : library.getKeys()){
			song = library.getSong(key);		
			obsList.add(song.getTitle() + " - " + song.getArtist());
		}
		
		songs.setItems(obsList);
	}
	
	
	private Song getSong(){
		return null;
	}
	
	public static void inputError(String s) {
		Stage popupStage = null;
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(popupStage);
        dialog.setTitle("Invalid Entry");
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Invalid Submission.\n" + s + "\n"));
        Scene dialogScene = new Scene(dialogVbox, 300, 66);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
	
	
}
