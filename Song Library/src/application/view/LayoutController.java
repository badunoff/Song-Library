package application.view;

import application.Main;
import application.Song;
import application.SongLibrary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
		Song song;
		
		if(edit.textProperty().getValueSafe().equals("Edit")){
			edit.setText("Save");
			
			orig_title = title.getText();
			orig_artist = artist.getText();
			
			if(orig_title == null){
				orig_title = "";
			}
			
			if(orig_artist == null){
				orig_artist = "";
			}
			
			title.setEditable(true);
			artist.setEditable(true);
			album.setEditable(true);
			year.setEditable(true);
		}
		else{//SAVE
			System.out.println("Original Title: " + orig_title);
			System.out.println("Original Artist: " + orig_artist);
			
			if(title.getText().trim().equals("") || artist.getText().trim().equals("")){
				// TODO needs to be pop-out
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
			
			//makes me uncomfortable
			SongLibrary library = Main.library;
			
			if(yearL_string.trim().equals("") || yearL_string.trim() == null){
				try {
					if(orig_title.equals("") && orig_artist.equals("")){
						song = library.add(titleL, artistL, albumL);
					}
					else{
						song = library.edit(orig_title, orig_artist, titleL, artistL, albumL);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try{
					yearL = Integer.parseInt(yearL_string);
					// TODO nested try-catch sucks
					try{
						if(orig_title.equals("") && orig_artist.equals("")){
							song = library.add(titleL, artistL, albumL, yearL);
						}
						else{
							song = library.edit(orig_title, orig_artist, titleL, artistL, albumL, yearL);
						}
					}
					catch(Exception e){
						System.out.println("Song already exists");
					}
				}catch(Exception e){
					// TODO need to change this whole thing. If the song already exists, then it will trigger this too. Maybe create different kinds of exceptions with many catches
					System.out.println("Invalid Year");
					title.setEditable(true);
					artist.setEditable(true);
					album.setEditable(true);
					year.setEditable(true);
				}
			}
			
			edit.setText("Edit");
			
			
			for(String key : library.getKeys()){
				System.out.println(library.getSong(key));
			}
			
		}
	}
	
	private void delete(){
		SongLibrary library = Main.library;
		
		String titleL = title.getText();
		String artistL = artist.getText();
		
		title.setText("");
		artist.setText("");
		album.setText("");
		year.setText("");
		
		edit.setText("Edit");
		
		library.deleteSong(titleL, artistL);
	}
	
	
	private Song getSong(){
		return null;
	}
}
