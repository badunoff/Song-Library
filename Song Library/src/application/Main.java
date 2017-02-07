package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Main extends Application {
	//seems like bad practice to access the library as Main.library.
	//maybe we should have a separate class for holding and controlling the library
	public static SongLibrary library;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		library = new SongLibrary();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/view/Layout.fxml"));
		
		//GridPane root = (GridPane)loader.load();
		Pane root = (Pane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fahrenheit-Celsius");
		primaryStage.setResizable(false);  
		primaryStage.show();
	    
	}
	public static void poop() {
		Stage popupStage = null;
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(popupStage);
        dialog.setTitle("Invalid Entry");
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Invalid Submission.\nPlease enter at least the song name and artist."));
        Scene dialogScene = new Scene(dialogVbox, 300, 66);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}

}