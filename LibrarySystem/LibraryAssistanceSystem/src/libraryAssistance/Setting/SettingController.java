package libraryAssistance.Setting;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingController implements Initializable {
	
	@FXML
	private TextField withoutfine;
	
	@FXML
	private TextField dayfine;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	@FXML
	private AnchorPane rootPane;
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initDeafultValues();
	}
	
	@FXML
	
	private void saveAction(ActionEvent event) {
		int days=Integer.parseInt(withoutfine.getText());
		float fine=Float.parseFloat(dayfine.getText());
		String unsme1=username.getText();
		String pasString=password.getText();
		
		Preference preference=Preference.getPreference();
		preference.setWithoutfine(days);
		preference.setDayfine(fine);
		preference.setUsername(unsme1);
		preference.setPassword(pasString);
		
		Preference.writePreferenceofFile(preference);
	}
	@FXML
	private void cancelAction(ActionEvent event) {
		Stage stage=(Stage) rootPane.getScene().getWindow();
		stage.close();
		System.out.println("Window HAs Closed");

	}

	private void initDeafultValues() {
		Preference preference=Preference.getPreference();
		withoutfine.setText(String.valueOf(preference.getWithoutfine()));
		dayfine.setText(String.valueOf(preference.getDayfine()));
		username.setText(preference.getUsername());
		password.setText(preference.getPassword());
		
	}
	
	
}
