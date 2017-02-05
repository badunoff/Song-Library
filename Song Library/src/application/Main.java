package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

	public static void main(String[] args) {
		launch(args);
	}

}