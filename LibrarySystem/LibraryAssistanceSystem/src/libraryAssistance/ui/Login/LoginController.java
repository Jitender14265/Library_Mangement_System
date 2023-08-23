package libraryAssistance.ui.Login;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.*;
import libraryAssistance.Setting.Preference;
import libraryAssistance.Utils.LibraryAssistanceUitls;
import libraryAssistance.ui.Main.Main;

public class LoginController implements Initializable{

	@FXML
	private TextField lusername;
	@FXML
	private TextField lpassword;
	@FXML private Label titlelabel;
	Preference preference;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		preference=Preference.getPreference();
		
	}
	
	@FXML
	private void loginAction(ActionEvent event) {
		
		
		String user=lusername.getText();
		String pass= DigestUtils.sha256Hex(lpassword.getText());
		
		if (user.equals(preference.getUsername()) && pass.equals(preference.getPassword())) {
			//Login
			closeStage();
			loadMainWindow();
			
		}else {
			Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setTitle("Failed to Login");
			alert2.setHeaderText(null);
			alert2.setContentText("Incorrect Username and Password");
			alert2.showAndWait();
		}
	}
	
	@FXML
	private void cancelAction(ActionEvent event) {
		System.exit(0);
	}
	
	private void closeStage() {
	((Stage)lusername.getScene().getWindow()).close();
		
	}
	
	void loadMainWindow() {

		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/libraryAssistance/ui/Main/Main.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Library Assistance");
			stage.setScene(new Scene(parent));
			stage.show();
			 LibraryAssistanceUitls.Stageicon(stage);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
