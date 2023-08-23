package libraryAssistance.Setting;

import Database.Handler.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class prefrenceLoader extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LibraryUI.fxml"));
			Parent rootParent=FXMLLoader.load(getClass().getResource("/libraryAssistance/Setting/Settings.fxml"));
			Scene scene = new Scene(rootParent);
			//scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
//			
//			new Thread(()-> {
//				DatabaseHandler.getInstance();						
//			}).start();
//			
			//Preference.initConfig();
	} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
