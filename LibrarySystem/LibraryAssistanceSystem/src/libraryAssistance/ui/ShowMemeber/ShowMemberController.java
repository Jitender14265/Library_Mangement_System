package libraryAssistance.ui.ShowMemeber;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import Database.Handler.DatabaseHandler;
import LibraryAssitance.ui.AddBook.AddBookController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import libraryAssistance.Setting.Preference.AlertMaker;
import libraryAssistance.Utils.LibraryAssistanceUitls;
import libraryAssistance.ui.ShowBook.showBookController.Book;
import libraryAssistance.ui.addMember.MemberAddController;

public class ShowMemberController implements Initializable {
ObservableList<Member> list=FXCollections.observableArrayList();
	
	@FXML
	private TableView<Member> tableView;
	@FXML
	private TableColumn<Member, String> nameCol;
	@FXML
	private TableColumn<Member, String> idCol;
	@FXML
	private TableColumn<Member, String> departmentCol;
	@FXML
	private TableColumn<Member, String> phoneCol;
	@FXML
	private TableColumn<Member, String> emailCol;
	//@FXML
//	private TableColumn<Member, Integer> numbercol;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initcol();
		loaddata();
		
	}
	
	private void initcol() {
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		
	}
	
	private void loaddata() {
		list.clear();
		DatabaseHandler handler=DatabaseHandler.getInstance();
		String quString="SELECT * FROM MEMBERS";
		ResultSet resultSet=handler.ExecQuery(quString);
		try {
			while(resultSet.next()) {
		
			String nameString= resultSet.getString("name");
			String idsString= resultSet.getString("id");
			String departString= resultSet.getString("department");
			String phoneString= resultSet.getString("mobile");
			String emailString= resultSet.getString("Email");
			
			list.add(new Member(nameString, idsString, departString, phoneString, emailString));
				System.out.println(phoneString);
				
			}
		} catch (Exception e) {
			//Logger.getLogger(BookAddController.class.getName()).log(Level.SEVERE,null,e);
			// TODO: handle exception
			e.printStackTrace();
		}
		tableView.getItems().setAll(list);
		
	}
	@FXML 
	private void RefereshMemberAction(ActionEvent e) {
		loaddata();
	}
	@FXML
	private void DeleteMemberAction(ActionEvent event)
	{
		
	}
	@FXML
	private void EditMemberAction(ActionEvent event) {
		 Member selectionForEditMember=tableView.getSelectionModel().getSelectedItem();
		 if (selectionForEditMember==null) 
		 {
			 AlertMaker.showsErrorAlert("No Book Selected", "Please Select The For Edit");
			 return;
		}
		
		 try {
			 FXMLLoader loader=new FXMLLoader(getClass().getResource("/libraryAssistance/ui/addMember/addmember.fxml"));
			Parent parent=loader.load();
			MemberAddController controller=loader.getController();
			controller.infiltrateShowMemberController(selectionForEditMember);
			
			Stage stage=new Stage(StageStyle.DECORATED);
			stage.setTitle("Edit Member");
			stage.setScene(new Scene(parent));
			stage.show();
			LibraryAssistanceUitls.Stageicon(stage);
			
			stage.setOnCloseRequest((e)->{
				RefereshMemberAction(new ActionEvent());
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	 }
	
	public static class Member{
		private final SimpleStringProperty name;
		private  final SimpleStringProperty id;
		private final SimpleStringProperty department;
		private final SimpleStringProperty phone;
		private  final SimpleStringProperty email ;
		
		  public Member(String name,String id,String department,String phone,String email) {
			this.name=new SimpleStringProperty(name);
			this.id=new SimpleStringProperty(id);
			this.department=new SimpleStringProperty(department);
			this.phone=new SimpleStringProperty(phone);
			this.email = new SimpleStringProperty(email);
		
			
		}
		

		    public String getName() {
		        return name.get();
		    }

		    public String getId() {
		        return id.get();
		    }

		    public String getDepartment() {
		        return department.get();
		    }

		    public String getPhone() {
		        return phone.get();
		    }

		    public String getEmail() {
		        return email.get();
		    }
		}

	}



