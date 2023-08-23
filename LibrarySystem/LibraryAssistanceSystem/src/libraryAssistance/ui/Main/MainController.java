package libraryAssistance.ui.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

import Database.Handler.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

	@FXML
	private HBox book_info;

	@FXML
	private HBox member_info;

	@FXML
	private TextField bookId;

	@FXML
	private TextField memberId;

	@FXML
	private Text bookName;

	@FXML
	private Text memberName;

	@FXML
	private Text bookAuthor;

	@FXML
	private Text bookStatus;

	@FXML
	private Text department;

	@FXML
	private Text phone;
	@FXML
	private TextField rbookId;

	@FXML
	private StackPane rootPane;

	
	@FXML
	private ListView<String> listview;
	
	@FXML
	private ListView<String> listview2;
	
	@FXML private TextField MmemberId;

	Boolean submssion = true;

	DatabaseHandler handler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		handler = DatabaseHandler.getInstance();

	}

	public void viewBook(ActionEvent event) {
		loadWindow("/libraryAssistance/ui/ShowBook/showbook.fxml", "View Presents Books");

	}

	public void addBook(ActionEvent event) {

		loadWindow("/LibraryAssitance/ui/AddBook/AddBook.fxml", "Add New Books");
	}

	public void viewMember(ActionEvent event) {

		loadWindow("/libraryAssistance/ui/ShowMemeber/ShowMember.fxml", "View Present Member");
	}

	public void addMember(ActionEvent event) {

		loadWindow("/libraryAssistance/ui/addMember/addmember.fxml", "Add New Member");
	}

	@FXML
	private void loadSettings(ActionEvent event) {
		loadWindow("/libraryAssistance/Setting/Settings.fxml", "Settings");
	}
	void loadWindow(String loc, String title) {

		try {
			Parent parent = FXMLLoader.load(getClass().getResource(loc));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle(title);
			stage.setScene(new Scene(parent));
			stage.show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void loadBook() {
		clearBookCache();
		String id = bookId.getText();
		String query = "select * from BOOKS where id= '" + id + "'";
		ResultSet rSet = handler.ExecQuery(query);
		Boolean flagBoolean = false;
		try {
			while (rSet.next()) {
				String nameString = rSet.getString("title");
				String authorString = rSet.getString("author");
				Boolean statusBoolean = rSet.getBoolean("isAvail");

				bookName.setText(nameString);
				bookAuthor.setText(authorString);
				String status = (statusBoolean) ? "Available" : "Not Available";
				bookStatus.setText(status);
				flagBoolean = true;
			}

			if (flagBoolean == false) {
				bookName.setText("");
				bookAuthor.setText("Not Available");
				bookStatus.setText("");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadMember() {
		clearMemberCache();
		String id = memberId.getText();
		String query = "select * from MEMBERS where id= '" + id + "'";
		ResultSet rSet = handler.ExecQuery(query);
		Boolean flagBoolean = false;
		try {
			while (rSet.next()) {
				String nameString = rSet.getString("name");
				String department = rSet.getString("department");
				String phone = rSet.getString("mobile");

				memberName.setText(nameString);
				this.department.setText(department);
				this.phone.setText(phone);
				flagBoolean = true;
			}

			if (flagBoolean == false) {
				memberName.setText("");
				department.setText("Not a Member");
				phone.setText("");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void issueBook(ActionEvent event) throws SQLException {
		String mid = memberId.getText();
		String bid = bookId.getText();
		if (mid.isEmpty() || bid.isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Please Enter In The All The Field");
			alert.setHeaderText(null);
			alert.setContentText("Please Enter In The All The Field");

		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirm Issue Operation");
			alert.setHeaderText(null);
			alert.setContentText("Are you Sure Want To Issue the Book " + bookName.getText() + "\n To "
					+ memberName.getText() + "\n");
			Optional<ButtonType> response = alert.showAndWait();

			if (response.get() == ButtonType.OK) {
				String str = "INSERT INTO ISSUE(memberID,bookID) VALUES ('" + mid + "','" + bid + "')";
				String str2 = "UPDATE BOOKS SET isAvail = false WHERE id = '" + bid + "'";

				System.out.println(str + " AND " + str2);

				if (handler.execAction(str) && handler.execAction(str2)) {
					Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
					alert2.setTitle("Success");
					alert2.setHeaderText(null);
					alert2.setContentText("BOOK IS ISSUED");
					alert2.showAndWait();
				} else {
					Alert alert2 = new Alert(Alert.AlertType.ERROR);
					alert2.setTitle("Failed");
					alert2.setHeaderText(null);
					alert2.setContentText("BOOK IS FAILED TO BE ISSUED");
					alert2.showAndWait();
				}
			} else {
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setTitle("Canceled");
				alert2.setHeaderText(null);
				alert2.setContentText("BOOK ISSUANCE IS CANCELED");
				alert2.showAndWait();
			}
		}
	}

	@FXML
	public void loadmemberBookInfo(ActionEvent event) {
		submssion = true;
		ObservableList<String> issueDataList = FXCollections.observableArrayList();

		String id2 = rbookId.getText();
		String qu0 = "SELECT * FROM ISSUE WHERE bookID = '" + id2 + "'";
		ResultSet rSet = handler.ExecQuery(qu0);
		try {
			while (rSet.next()) {
				String mBookid = id2;
				String memberid = rSet.getString("memberID");
				Timestamp issuetime = rSet.getTimestamp("issueTime");
				int renewcount = rSet.getInt("renew_count");

				issueDataList.add("BOOK Information :- ");
				issueDataList.add("");
			
				issueDataList.add("Issue Date And Time : " + issuetime.toGMTString());
				issueDataList.add("Renew Count : " + renewcount);

				


				qu0 = "select * from BOOKS where id = '" + mBookid + "'";
				ResultSet r1 = handler.ExecQuery(qu0);
				while (r1.next()) {
					issueDataList.add("Book Name : " + r1.getString("title"));
					issueDataList.add("Book ID : " + r1.getString("id"));
					issueDataList.add("Book Author : " + r1.getString("author"));
					issueDataList.add("Book Publisher : " + r1.getString("publisher"));

				}
				issueDataList.add("");
				issueDataList.add("-------------------------------------------------------------------------------------------------------------------------");
				issueDataList.add("");

				issueDataList.add("Member Information :- ");
				issueDataList.add("");
				
				qu0 = "select * from MEMBERS where id = '" + memberid + "'";
				ResultSet r2 = handler.ExecQuery(qu0);
				while (r2.next()) {
					issueDataList.add("Member Name : " + r2.getString("name"));
					issueDataList.add("Member ID : " + r2.getString("id"));
					issueDataList.add("Member Department : " + r2.getString("department"));
					issueDataList.add("Member Contact : " + r2.getString("mobile"));
					issueDataList.add("Member Email Id : " + r2.getString("Email"));
				}
				issueDataList.add("");
				

				submssion = false;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listview.getItems().setAll(issueDataList);
	}

	public void renewBook(ActionEvent event) {
		if (submssion) {
			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("FAILED");
			alert2.setHeaderText(null);
			alert2.setContentText("Please Select A Book To RENEWED");
			alert2.showAndWait();
			return;
		}
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm RENEW Operation");
		alert.setHeaderText(null);
		alert.setContentText("Are you Sure Want To return The Book");
		Optional<ButtonType> response = alert.showAndWait();

		if (response.get() == ButtonType.OK) {
			
			String ac3="UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP , RENEW_COUNT = RENEW_COUNT + 1  WHERE BOOKID = '" + rbookId.getText()+"'";
			System.out.println(ac3);
			
			if (handler.execAction(ac3)) {
				Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
				alert2.setTitle("SUCCESS");
				alert2.setHeaderText(null);
				alert2.setContentText("Book Has Been RENEWED");
				alert2.showAndWait();
			}else {
				Alert alert2 = new Alert(Alert.AlertType.ERROR);
				alert2.setTitle("FAILED");
				alert2.setHeaderText(null);
				alert2.setContentText("BOOK HAS  NOT Been Renewed");
				alert2.showAndWait();
			}
		}else {
			Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setTitle("CANCELED");
			alert2.setHeaderText(null);
			alert2.setContentText("CANCEL RENEW OPERATION");
			alert2.showAndWait();
			
		}
		}

	

	public void submissiomBook(ActionEvent event) {
		if (submssion) {
			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("FAILED");
			alert2.setHeaderText(null);
			alert2.setContentText("Please Select A Book To SUBMITTED");
			alert2.showAndWait();
			return;
		}
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirm Submission Operation");
		alert.setHeaderText(null);
		alert.setContentText("Are you Sure Want To return The Book");
		Optional<ButtonType> response = alert.showAndWait();

		if (response.get() == ButtonType.OK) {

		String id2 = rbookId.getText();
		String ac1 = "DELETE FROM ISSUE WHERE bookID = '" + id2 + "'";
		String ac2 = "UPDATE BOOKS SET isAvail = TRUE WHERE ID = '" + id2 + "'";

		if (handler.execAction(ac2) && handler.execAction(ac1)) {

			Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
			alert2.setTitle("SUCCESS");
			alert2.setHeaderText(null);
			alert2.setContentText("BOOK HAS SUBMITTED");
			alert2.showAndWait();
		} else {
			Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setTitle("FAILED");
			alert2.setHeaderText(null);
			alert2.setContentText("BOOK HAS NOT SUBMITTED");
			alert2.showAndWait();

		}
		}else {
			Alert alert2 = new Alert(Alert.AlertType.ERROR);
			alert2.setTitle("FAILED");
			alert2.setHeaderText(null);
			alert2.setContentText("BOOK HAS NOT SUBMITTED");
			alert2.showAndWait();
		}
	}
	
	@FXML 
	private void loadmemberBookInfo2(ActionEvent event) {
		
		ObservableList<String> issueDataList = FXCollections.observableArrayList();

		String id2 = MmemberId.getText();
		String qu0 = "SELECT * FROM ISSUE WHERE memberID = '" + id2 + "'";
		ResultSet rSet = handler.ExecQuery(qu0);
		try {
			while (rSet.next()) {
				String mBookid = rSet.getString("bookID");
				String memberid = id2;
				Timestamp issuetime = rSet.getTimestamp("issueTime");
				int renewcount = rSet.getInt("renew_count");

				

				issueDataList.add("Member Information :- ");

				qu0 = "select * from BOOKS where id = '" + mBookid + "'";
				ResultSet r1 = handler.ExecQuery(qu0);
				while (r1.next()) {
					issueDataList.add("Book Name : " + r1.getString("title"));
					issueDataList.add("Book ID : " + r1.getString("id"));
					issueDataList.add("Book Author : " + r1.getString("author"));
					issueDataList.add("Book Publisher : " + r1.getString("publisher"));
									
				}

				issueDataList.add("Issue Date And Time : " + issuetime.toGMTString());
				issueDataList.add("Renew Count : " + renewcount);
				
				issueDataList.add("");
				issueDataList.add("-------------------------------------------------------------------------------------------------------------------------");
				issueDataList.add("");

				
//				issueDataList.add("Member Information :- ");
//				qu0 = "select * from MEMBERS where id = '" + memberid + "'";
//				ResultSet r2 = handler.ExecQuery(qu0);
//				while (r2.next()) {
//					issueDataList.add("Member Name : " + r2.getString("name"));
//					issueDataList.add("Member ID : " + r2.getString("id"));
//					issueDataList.add("Member Department : " + r2.getString("department"));
//					issueDataList.add("Member Contact : " + r2.getString("mobile"));
//					issueDataList.add("Member Email Id : " + r2.getString("Email"));
//				}

				

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listview2.getItems().setAll(issueDataList);
		
	}

	void clearBookCache() {
		bookName.setText("");
		bookAuthor.setText("");
		bookStatus.setText("");
	}

	void clearMemberCache() {
		memberName.setText("");
		department.setText("");
		phone.setText("");
	}
	
	@FXML
	private void closeMenu() {
		((Stage)rootPane.getScene().getWindow()).close();
	}
	
	@FXML
	private void FullScreenMenu() {
		Stage stage=((Stage)rootPane.getScene().getWindow());
		stage.setFullScreen(!stage.isFullScreen());
	}
}
