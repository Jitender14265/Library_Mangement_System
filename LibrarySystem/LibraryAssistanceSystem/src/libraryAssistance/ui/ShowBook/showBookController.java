package libraryAssistance.ui.ShowBook;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Database.Handler.DatabaseHandler;
import LibraryAssitance.ui.AddBook.AddBookController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libraryAssistance.Setting.Preference.AlertMaker;
import libraryAssistance.Utils.LibraryAssistanceUitls;

public class showBookController implements Initializable {

	ObservableList<Book> list=FXCollections.observableArrayList();
	
	@FXML
	private TableView<Book> tableView;
	@FXML
	private TableColumn<Book, String> titleCol;
	@FXML
	private TableColumn<Book, String> idCol;
	@FXML
	private TableColumn<Book, String> authorCol;
	@FXML
	private TableColumn<Book, String> publisherCol;
	@FXML
	private TableColumn<Book, Boolean> availableCol;
	@FXML
	private Boolean isineditmode=Boolean.FALSE;
	
//	private TableColumn<Book, Integer> numbercol;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initcol();
		loaddata();
		
	}
	
	private void initcol() {
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
		
	}
	
	private void loaddata() {
		list.clear();
		
		DatabaseHandler handler= DatabaseHandler.getInstance();
		String quString="SELECT * FROM BOOKS";
		ResultSet resultSet=handler.ExecQuery(quString);
		try {
			while(resultSet.next()) {
				
				String title= resultSet.getString("title");
				String idx= resultSet.getString("id");
				String authorx= resultSet.getString("author");
				String publisherx= resultSet.getString("publisher");
				Boolean availx= resultSet.getBoolean("isAvail");
				list.add(new Book(title, idx, authorx, publisherx, availx));
				
			}
		} catch (Exception e) {
			//Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE,null,e);
			// TODO: handle exception
			e.printStackTrace();
		}
		tableView.setItems(list);
		
	}
	
	
	 @FXML 
	 private void DeleteAction(ActionEvent event) {
		 Book selectionForDeletionBook=tableView.getSelectionModel().getSelectedItem();
		 if (selectionForDeletionBook==null) 
		 {
			 AlertMaker.showsErrorAlert("No Book Selected", "Please Select The For Deletion");
			 return;
		}
		 if (DatabaseHandler.getInstance().checkIssue(selectionForDeletionBook)) {
			 Alert alert=new Alert(AlertType.ERROR);
			 alert.setTitle("Failed TO DELETE");
			 alert.setContentText("This '" + selectionForDeletionBook.getTitle() + "'" +  "  Book is Issued To Someone");
			 alert.showAndWait();
			
		}else {
		 Alert alert=new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Deletion");
		 alert.setContentText("Are you sure Wnat to Delete the Book  " + selectionForDeletionBook.getTitle() + "  ");
		Optional<ButtonType> answerOptional= alert.showAndWait();
		
		if (answerOptional.get()==ButtonType.OK) {
			Boolean result=DatabaseHandler.getInstance().DeleteBook(selectionForDeletionBook);
			if (result) {
				AlertMaker.showsSimplesAlert("BOOK DELETED", selectionForDeletionBook.getTitle()+" Was deleted successfully");
				list.remove(selectionForDeletionBook);
			}else {
				AlertMaker.showsErrorAlert("Failed",selectionForDeletionBook.getTitle()+"  could not be deleted");
			}
		}
		else {
			AlertMaker.showsSimplesAlert("Cancelled", "Deletion Process is Canceled");
		}
	 }
	 }
	 
	 @FXML
	 private void EditAction(ActionEvent event) {
		 Book selectionForEditBook=tableView.getSelectionModel().getSelectedItem();
		 if (selectionForEditBook==null) 
		 {
			 AlertMaker.showsErrorAlert("No Book Selected", "Please Select The For Edit");
			 return;
		}
		
		 try {
			 FXMLLoader loader=new FXMLLoader(getClass().getResource("/LibraryAssitance/ui/AddBook/AddBook.fxml"));
			Parent parent=loader.load();
			AddBookController controller=loader.getController();
			controller.infiltrateShowbookController(selectionForEditBook);
			
			Stage stage=new Stage(StageStyle.DECORATED);
			stage.setTitle("Edit Book");
			stage.setScene(new Scene(parent));
			stage.show();
			LibraryAssistanceUitls.Stageicon(stage);
			
			stage.setOnCloseRequest((e)->{
				RefreshAction(new ActionEvent());
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	 }
	 
	 @FXML 
	 public void RefreshAction(ActionEvent event) {
		 loaddata();
	 }
	public static class Book{
		private final SimpleStringProperty title;
		private  final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private  final SimpleBooleanProperty available ;
		
		public Book(String title,String id,String author,String publisher,Boolean available) {
			this.title=new SimpleStringProperty(title);
			this.id=new SimpleStringProperty(id);
			this.author=new SimpleStringProperty(author);
			this.publisher=new SimpleStringProperty(publisher);
			// TODO Auto-generated constructor stub
			this.available = new SimpleBooleanProperty(available);
		
			
		}
		
		public String getTitle() {
			return title.get();
		}
		public String getId() {
			return id.get();
		}
		public String getAuthor() {
			return author.get();
		}
		public String getPublisher() {
			return publisher.get();
		}

		public Boolean getAvailable() {
			return available.get();
		}

		

	}

}
