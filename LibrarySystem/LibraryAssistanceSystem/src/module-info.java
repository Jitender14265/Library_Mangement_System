module LibraryAssistanceSystem {
	opens libraryAssistance.ui.ShowBook;
	exports libraryAssistance.ui.ShowBook;
	
	opens libraryAssistance.ui.addMember;
	exports libraryAssistance.ui.addMember;
	
	opens libraryAssistance.ui.ShowMemeber;
	exports libraryAssistance.ui.ShowMemeber;
	
	opens libraryAssistance.ui.Main;
	exports libraryAssistance.ui.Main;
	
	opens libraryAssistance.Setting;
	exports libraryAssistance.Setting;
	
	opens libraryAssistance.ui.Login;
	exports libraryAssistance.ui.Login;
	
	opens Resources.css;
	exports Resources.css;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires java.logging;
	requires javafx.graphics;
	requires com.google.gson;
	requires org.apache.commons.codec;
	
	opens LibraryAssitance.ui.AddBook to javafx.graphics, javafx.fxml;
	
	
	
}
