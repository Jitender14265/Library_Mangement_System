package libraryAssistance.ui.addMember;

import java.net.URL;
import java.util.ResourceBundle;

import Database.Handler.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libraryAssistance.Setting.Preference.AlertMaker;
import libraryAssistance.ui.ShowMemeber.ShowMemberController;

import libraryAssistance.ui.ShowMemeber.ShowMemberController.Member;

public class MemberAddController implements Initializable {
	@FXML
	private TextField name;
	
	@FXML
	private TextField mid;
	
	@FXML
	private TextField mdepartment;
	
	@FXML
	private TextField mnumber;

	@FXML
	private TextField memail;

	@FXML
	private Button save1;
	
	@FXML
	private Button cancel1;
	@FXML
	private AnchorPane rootPane;
	private Boolean isineditMode=Boolean.FALSE;
	
	DatabaseHandler databaseHandler;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		databaseHandler= DatabaseHandler.getInstance();
	//	checkData();
		
	}

	@FXML
	private void addMember(ActionEvent event) {
		String idString=mid.getText();
		String nameString=name.getText();
		String departString=mdepartment.getText();
		String numberString=mnumber.getText();
		String emailString=memail.getText();
		
		if (idString.isEmpty()||nameString.isEmpty()||departString.isEmpty()||numberString.isEmpty()||emailString.isEmpty()) {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);;
			alert.setContentText("Please Enter The All Field");
			alert.showAndWait();
			return;
			
		}
		
		if (isineditMode) {
			handleUpdateMember();
			return;
			
		}
		
		String qu="insert into MEMBERS values ("+
				"'"+ idString +"',"+
				"'"+ nameString +"',"+
				"'"+ departString +"',"+
				"'"+ numberString +"',"+
				"'"+ emailString +"'"+
				")";
		System.out.println(qu);
		if (databaseHandler.execAction(qu)) {

			Alert alert=new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);;
			alert.setContentText("Sucess");
			alert.showAndWait();
		}
		else {
			
			Alert alert=new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);;
			alert.setContentText("Failed To Enter");
			alert.showAndWait();
		}
		
	}
	
	

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage=(Stage) rootPane.getScene().getWindow();
		stage.close();
		System.out.println("Window HAs Closed");
	}
	@FXML
	private void textfield() {
		
	}

	public void infiltrateShowMemberController(Member member) {
		name.setText(member.getName());
		mid.setText(member.getId());
		mdepartment.setText(member.getDepartment());
		mnumber.setText(member.getDepartment());
		memail.setText(member.getEmail());
		isineditMode=Boolean.TRUE;
		mid.setEditable(false);
		// TODO Auto-generated method stub
		
	}
	

	private void handleUpdateMember() {
		// TODO Auto-generated method stub
		Member member  = new ShowMemberController.Member(name.getText(), mid.getText(), mdepartment.getText(),
				mnumber.getText(),memail.getText());
		if (DatabaseHandler.getInstance().updateMember(member)) {
			AlertMaker.showsSimplesAlert("SUCCESS", "Member UDATED");
		} else {
			AlertMaker.showsErrorAlert("FAILED", "UNABLE TO UPDATE Member");

		}
	}

	}
	


