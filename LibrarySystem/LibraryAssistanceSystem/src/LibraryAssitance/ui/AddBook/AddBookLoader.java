package LibraryAssitance.ui.AddBook;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class AddBookLoader extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LibraryUI.fxml"));
			Parent rootParent=FXMLLoader.load(getClass().getResource("AddBook.fxml"));
			Scene scene = new Scene(rootParent,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			

			 TextField btitle = (TextField) scene.lookup("#btitle");
		        TextField bid = (TextField) scene.lookup("#bid");
		        TextField bauthor = (TextField) scene.lookup("#bauthor");;
		        TextField bpublisher =(TextField) scene.lookup("#bpublisher");
		       
		        
		        
		        btitle.setOnAction(event -> bid.requestFocus());
		        bid.setOnAction(event -> bauthor.requestFocus());
		        bauthor.setOnAction(event -> bpublisher.requestFocus());
		        
			
String IMAGE_LOC="C:\\Users\\JITENDER\\Desktop\\LibrarySystem\\LibraryAssistanceSystem\\book.png";
			
			primaryStage.getIcons().add(new Image(IMAGE_LOC));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
