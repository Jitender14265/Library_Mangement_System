package libraryAssistance.Utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryAssistanceUitls {

	public static final String IMAGE_LOC="C:\\Users\\JITENDER\\Desktop\\LibrarySystem\\LibraryAssistanceSystem\\main.png";

	public static void  Stageicon(Stage stage) {
		stage.getIcons().add(new Image(IMAGE_LOC));
		
	}
	
}
