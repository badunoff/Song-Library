package application.view;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resources;
import javax.print.DocFlavor.URL;

import application.CreateJSON;
import application.Main;
import application.Song;
import application.SongLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
	int orig_index; 
	
	@FXML Button addSong;
	@FXML Button edit;
	@FXML Button delete;
	
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	@FXML ListView<String> songs;
	
	public void initialize() {
		SongLibrary library = Main.library;
		ObservableList<String> obsList = FXCollections.observableArrayList();
		
		setGray(true);
		for(String key : library.getKeys()){
			Song song = library.getSong(key);		
			
			obsList.add(song.getTitle() + " - " + song.getArtist());
		}
		songs.setItems(obsList);
		
		if (!obsList.isEmpty()) {
			makeSelection(0, obsList);
		}
	}
	
	//TODO merge handleMouseClick and makeSelection
	private void makeSelection(int index, ObservableList<String> obsList2) {
		SongLibrary library = Main.library;	
		if (obsList2.isEmpty()) {
			clearFields();
		}
		else {
			songs.scrollTo(index);
	        songs.getSelectionModel().select(index);
	        String key = songs.getSelectionModel().getSelectedItem().toLowerCase();
			Song song = library.getSong(key); 
					
			orig_index = songs.getSelectionModel().getSelectedIndex();
			
			System.out.println("clicked on " + key);
			
			orig_title = song.getTitle();
			orig_artist = song.getArtist();
			edit.setText("Edit");
			delete.setText("Delete");
			
	 	    title.setText(song.getTitle());
	 		artist.setText(song.getArtist());
	 		album.setText(song.getAlbum());
	 		year.setText(song.getYear());
		}
		
	}
	
	
	public void handleMouseClick(MouseEvent arg0) {
		SongLibrary library = Main.library;
		if(songs.getSelectionModel().getSelectedItem() == null){
			return;
		}
		
		String key = songs.getSelectionModel().getSelectedItem().toLowerCase();
		Song song = library.getSong(key); 
		
		orig_index = songs.getSelectionModel().getSelectedIndex();
		
		System.out.println("clicked on " + key);
		
		orig_title = song.getTitle();
		orig_artist = song.getArtist();
		edit.setText("Edit");				
		delete.setText("Delete");
		
 	    title.setText(song.getTitle());
 		artist.setText(song.getArtist());
 		album.setText(song.getAlbum());
 		year.setText(song.getYear());
 	}
	
	public void add(ActionEvent e) {
		System.out.println("ADD");
		
		orig_index = songs.getSelectionModel().getSelectedIndex();
		
		setGray(false);
		add();
	}
	
	public void edit(ActionEvent e) {
		System.out.println("EDIT");
		
		//Button b = (Button)e.getSource();
		//title.setText(b.textProperty().getValueSafe());
		setGray(false);
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
		delete.setText("Cancel");
		setGray(false);
	}
	
	private void edit(){
		ObservableList<String> obsList = FXCollections.observableArrayList();
		Song song;
		
		if(edit.textProperty().getValueSafe().equals("Edit")){
			edit.setText("Save");
			delete.setText("Cancel");
			
			orig_title = title.getText();
			orig_artist = artist.getText();
			
			title.setEditable(true);
			artist.setEditable(true);
			album.setEditable(true);
			year.setEditable(true);
		}
		else{//SAVE
			//makes me uncomfortable
			
			if (!confirm("save")) {
				return;
			}
			
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
					
					if(yearL > Calendar.getInstance().get(Calendar.YEAR) || yearL < 1800){
						System.out.println("Invalid Year");
						inputError("Invalid Year");
						title.setEditable(true);
						artist.setEditable(true);
						album.setEditable(true);
						year.setEditable(true);
						return;
					}
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
					System.out.println("Invalid Year");
					inputError("Invalid Year");
					title.setEditable(true);
					artist.setEditable(true);
					album.setEditable(true);
					year.setEditable(true);
					return;
				}
			}
			
			
			
	
			edit.setText("Edit");
			delete.setText("Delete");
			try {
				CreateJSON.writer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setGray(true);
			
			for(String key : library.getKeys()){
				System.out.println(library.getSong(key));
			}
			
		}
	}
	
	private void delete(){
		SongLibrary library = Main.library;
		ObservableList<String> obsList = FXCollections.observableArrayList();
		Song song;
		
		
		if(delete.textProperty().getValueSafe().equals("Delete")){
			if(!confirm("delete")){
				return;
			}
			
			int index = songs.getSelectionModel().getSelectedIndex();
			System.out.println("About to delete " + index);
			int count = library.getKeys().size();
			
			if (index == count-1) {
				index--;
			}
			
			String titleL = title.getText();
			String artistL = artist.getText();
			
			library.deleteSong(titleL, artistL);
			
			clearFields();
			
			for(String key : library.getKeys()){
				song = library.getSong(key);		
				obsList.add(song.getTitle() + " - " + song.getArtist());
			}
			
			songs.setItems(obsList);
			makeSelection(index, obsList);
		}
		else{
			System.out.println("Orig index = " + orig_index);
			obsList = songs.getItems();
			makeSelection(orig_index, obsList);
			
			title.setEditable(false);
			artist.setEditable(false);
			album.setEditable(false);
			year.setEditable(false);
		}

		
		edit.setText("Edit");
		delete.setText("Delete");
		
		try {
			CreateJSON.writer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setGray(true);
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
	
	public static boolean confirm(String s) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Window");
        alert.setHeaderText("You chose to " + s + " a song. Are you sure?");
        alert.setContentText("Choose your option.");

        ButtonType yes = new ButtonType("Yes");
        ButtonType Cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, Cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            return true;
        } else {
            return false;
        }

	}
	
	
	
	
	
	
	
	
	
	
	
	
	private void setFields(String key){
		Song song = Main.library.getSong(key);

		orig_title = song.getTitle();
		orig_artist = song.getArtist();
		
		title.setText(song.getTitle());
		artist.setText(song.getArtist());
		album.setText(song.getAlbum());
		year.setText(song.getYear());
	}
	
	public void revertFields(){
		Song song = Main.library.getSong(Main.library.generateKey(orig_title, orig_artist));
		
		title.setText(song.getTitle());
		artist.setText(song.getArtist());
		album.setText(song.getAlbum());
		year.setText(song.getYear());
	}
	
	public void clearFields(){
		orig_title = "";
		orig_artist = "";
		
		title.setText("");
		artist.setText("");
		album.setText("");
		year.setText("");
	}

	private void setGray(boolean gray){
		title.setDisable(gray);
		artist.setDisable(gray);
		album.setDisable(gray);
		year.setDisable(gray);
		
	}
}
