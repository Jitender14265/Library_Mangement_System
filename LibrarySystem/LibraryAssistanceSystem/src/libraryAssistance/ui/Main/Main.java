package libraryAssistance.ui.Main;

import Database.Handler.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libraryAssistance.Utils.LibraryAssistanceUitls;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent rootParent=FXMLLoader.load(getClass().getResource("/libraryAssistance/ui/Login/Login.fxml"));
			Scene scene = new Scene(rootParent);
			//scene.getStylesheets().add(getClass().getResource("ssss.css").toExternalForm());
			primaryStage.setTitle("Library Assistance Login");
			primaryStage.setScene(scene);
			primaryStage.show();
			LibraryAssistanceUitls.Stageicon(primaryStage);
			new Thread(()-> {
				DatabaseHandler.getInstance();						
			}).start();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
