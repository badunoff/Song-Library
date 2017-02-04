package application.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * @author Tim
 *
 */
public class LayoutController {
	@FXML Button addSong;
	@FXML Button edit;
	@FXML Button delete;
	
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;
	
	@FXML ListView<String> songs;
	
	public void click(ActionEvent e) {
		System.out.println("IN CONTROL");
		
		Button b = (Button)e.getSource();
		if(b == addSong){
			title.setText("ADD");
		}
		else if(b == edit){
			title.setText("EDIT");
		}
		else if(b == delete){
			title.setText("DELETE");
		}
		else{
			title.setText("SOMETHING ELSE");;
		}
	}
	
}
