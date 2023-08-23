package libraryAssistance.ui.ShowBook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class showBookLoader extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LibraryUI.fxml"));
			Parent rootParent=FXMLLoader.load(getClass().getResource("showbook.fxml"));
			Scene scene = new Scene(rootParent,600,400);
			scene.getStylesheets().add(getClass().getResource("showbook.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
String IMAGE_LOC="C:\\Users\\JITENDER\\Desktop\\LibrarySystem\\LibraryAssistanceSystem\\viewbook.jpg";
			
			primaryStage.getIcons().add(new Image(IMAGE_LOC));;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
	


