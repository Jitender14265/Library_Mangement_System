package libraryAssistance.ui.addMember;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MemberAddLoader extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LibraryUI.fxml"));
			Parent rootParent=FXMLLoader.load(getClass().getResource("addmember.fxml"));
			Scene scene = new Scene(rootParent,600,400);
			scene.getStylesheets().add(getClass().getResource("addmember.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			 TextField name = (TextField) scene.lookup("#name");
		        TextField mid = (TextField) scene.lookup("#mid");
		        TextField mdepartment = (TextField) scene.lookup("#mdepartment");;
		        TextField mnumber =(TextField) scene.lookup("#mnumber");
		        TextField memail = (TextField) scene.lookup("#memail");
		        
		        
		        name.setOnAction(event -> mid.requestFocus());
		        mid.setOnAction(event -> mdepartment.requestFocus());
		        mdepartment.setOnAction(event -> mnumber.requestFocus());
		        mnumber.setOnAction(event -> memail.requestFocus());

		       
		        
			String IMAGE_LOC="C:\\Users\\JITENDER\\Desktop\\LibrarySystem\\LibraryAssistanceSystem\\member.jpg";
			
			primaryStage.getIcons().add(new Image(IMAGE_LOC));;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
