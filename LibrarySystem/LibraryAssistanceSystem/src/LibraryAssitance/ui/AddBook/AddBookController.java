package LibraryAssitance.ui.AddBook;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.Handler.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import libraryAssistance.Setting.Preference.AlertMaker;
import libraryAssistance.ui.ShowBook.showBookController;
import libraryAssistance.ui.ShowBook.showBookController.Book;

public class AddBookController implements Initializable {
	@FXML
	private TextField btitle;

	@FXML
	private TextField bid;

	@FXML
	private TextField bauthor;

	@FXML
	private TextField bpublisher;

	@FXML
	private Button save1;

	@FXML
	private Button cancel1;
	@FXML
	private AnchorPane rootPane;

	private Boolean isineditmode = Boolean.FALSE;

	DatabaseHandler databaseHandler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		databaseHandler = DatabaseHandler.getInstance();
		checkData();

	}

	@FXML
	private void addbook(ActionEvent event) {
		String BookID = bid.getText();
		String BookAuthor = bauthor.getText();
		String BookPublisher = bpublisher.getText();
		String BookTitle = btitle.getText();

		if (BookID.isEmpty() || BookAuthor.isEmpty() || BookPublisher.isEmpty() || BookTitle.isEmpty()) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			;
			alert.setContentText("Please Enter The All Field");
			alert.showAndWait();
			return;

		}
		if (isineditmode) {
			handleEditOperation();
			return;

		}

		String qu = "insert into BOOKS values (" + "'" + BookID + "'," + "'" + BookTitle + "'," + "'" + BookAuthor
				+ "'," + "'" + BookPublisher + "'," + "" + true + "" + ")";
		System.out.println(qu);
		if (databaseHandler.execAction(qu)) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			;
			alert.setContentText("Sucess");
			alert.showAndWait();
		} else {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			;
			alert.setContentText("Failed To Enter");
			alert.showAndWait();
		}

	}

	@FXML
	private void cancelbook(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
		stage.close();
		System.out.println("Window HAs Closed");

	}

	private void checkData() {
		// TODO Auto-generated method stub
		String quString = "Select title from BOOKS";
		ResultSet rs = databaseHandler.ExecQuery(quString);
		try {

			while (rs.next()) {
				String titlex = rs.getString("title");
				System.out.println(titlex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void infiltrateShowbookController(showBookController.Book book) {
		btitle.setText(book.getTitle());
		bid.setText(book.getId());
		bauthor.setText(book.getAuthor());
		bpublisher.setText(book.getPublisher());
		bid.setEditable(false);
		isineditmode = Boolean.TRUE;
	}

	private void handleEditOperation() {
		showBookController.Book book = new showBookController.Book(btitle.getText(), bid.getText(), bauthor.getText(),
				bpublisher.getText(), true);
		if (databaseHandler.updateBook(book)) {
			AlertMaker.showsSimplesAlert("SUCCESS", "BOOK UDATED");
		} else {
			AlertMaker.showsErrorAlert("FAILED", "UNABLE TO UPDATE");

		}
	}
}
