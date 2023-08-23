package Database.Handler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import libraryAssistance.ui.ShowBook.showBookController.Book;
import libraryAssistance.ui.addMember.MemberAddController;

public final class DatabaseHandler {
    private static DatabaseHandler handler=null;
    
    private static final String URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
    
    private DatabaseHandler() {
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssueTable();
    }
    public static DatabaseHandler getInstance() {
    	if(handler==null) {
    		handler=new DatabaseHandler();
    	}
    	return handler;
    }
    
    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(URL);
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"Can't load database","Databse Error",JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        	
			
            e.printStackTrace();
        }
    }
    
    void setupBookTable() {
        String table_name = "BOOKS";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbmData = conn.getMetaData();
            ResultSet tables = dbmData.getTables(null, null, table_name.toUpperCase(), null);
            
            if (tables.next()) {
                System.out.println("Table " + table_name + " already exists. Ready to go!");
            } else {
                stmt.execute("CREATE TABLE " + table_name + " ("
                	//	+"no int auto increment, "
                        + "id varchar(200) PRIMARY KEY, "
                        + "title varchar(200), "
                        + "author varchar(200), "
                        + "publisher varchar(200), "
                        + "isAvail boolean DEFAULT true"
                        + ")");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage() + ".........error in creating Booktable");
            e.printStackTrace();
        }finally {
			
		}
    }
    
    void setupMemberTable() {
        String table_name = "MEMBERS";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbmData = conn.getMetaData();
            ResultSet tables = dbmData.getTables(null, null, table_name.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + table_name + " already exists. Ready to go!");
            } else {
                String createTableSQL = "CREATE TABLE " + table_name + " ("
                        + "id VARCHAR(50) PRIMARY KEY, "
                        + "name VARCHAR(200), "
                        + "department VARCHAR(200), "
                        + "mobile VARCHAR(20), "
                        + "Email VARCHAR(100)"
                        + ")";
                stmt.execute(createTableSQL);
                System.out.println("Table " + table_name + " created successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error creating Membertable: " + e.getMessage());
        }finally {
			
		}
    }
    
    void setupIssueTable() {
        String table_name = "ISSUE";
        try {
            stmt = conn.createStatement();
            
            DatabaseMetaData dbmData = conn.getMetaData();
            ResultSet tables = dbmData.getTables(null, null, table_name.toUpperCase(), null);
            
            if (tables.next()) {
                System.out.println("Table " + table_name + " already exists. Ready to go!");
            } else {
                stmt.execute("CREATE TABLE " + table_name + " ("
                	+ "bookID varchar(200) PRIMARY KEY, "
                    + "memberID varchar(50), "
                    + "issuetime timestamp default CURRENT_TIMESTAMP, "
                    +"renew_count integer default 0, "
                    + "FOREIGN KEY (bookID) REFERENCES BOOKS(id), "
                    + "FOREIGN KEY (memberID) REFERENCES MEMBERS(id)"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + ".............Setup Issue DataBase");
//            e.printStackTrace();
        }
        finally {
			
		}
    }
    
    public ResultSet ExecQuery(String query) {
        ResultSet result;
        try {
            if (conn == null) {
                createConnection();
            }
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at ExecQuery: DataHandler" + e.getLocalizedMessage());
            return null;
        }
        return result;
    }
    
    public boolean execAction(String qu) {
        try {
            if (conn == null) {
                createConnection();
            }
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
            //System.out.println("Exception at execAction: DataHandler" + e.getLocalizedMessage());
          e.printStackTrace();
            return false;
        }
    }
    
    public boolean DeleteBook(Book book) {
  
		try {
			String deleteString="DELETE FROM BOOKS WHERE ID = ?";
	    	PreparedStatement stmt;
			stmt = conn.prepareStatement(deleteString);
			stmt.setString(1, book.getId());
			int res=stmt.executeUpdate();
			System.out.println(res);
			if (res==1) {
				return true; 
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
	}

    
    public boolean checkIssue(Book book) {
    	try {
			String deleteString="SELECT COUNT(*) FROM ISSUE WHERE bookID = ?";
	    	PreparedStatement stmt;
			stmt = conn.prepareStatement(deleteString);
			stmt.setString(1, book.getId());
			ResultSet rSet=stmt.executeQuery();
			if (rSet.next()) {
				int count=rSet.getInt(1);
				System.out.println(count);
				if (count>0) {
					
					return true;
					
				}
				else {
					return false;
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
		
	}
    
    public boolean updateBook(Book book) {
    	String update="UPDATE BOOKS SET TITLE=?,AUTHOR=?,PUBLISHER=? WHERE ID=?";
    	try {
			PreparedStatement stmt=conn.prepareStatement(update);
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getPublisher());
			stmt.setString(4, book.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return false;
		
	}
	

	public boolean updateMember(libraryAssistance.ui.ShowMemeber.ShowMemberController.Member member) {

    	String update="UPDATE MEMBERS SET NAME=?, DEPARTMENT=?,mobile=? , Email=? WHERE ID=?";
    	try {
			PreparedStatement stmt=conn.prepareStatement(update);
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getDepartment());
			stmt.setString(3, member.getPhone());
			stmt.setString(4, member.getEmail());
			stmt.setString(5, member.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    				
		return false;
	}
	}
