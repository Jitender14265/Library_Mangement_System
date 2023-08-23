package libraryAssistance.Setting;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javafx.scene.control.Alert;

public class Preference {
	
	public static final String CONFIG_FILE="config.txt";
	
	int withoutfine;
	float dayfine;
	String	username;
	String password	;
	
  public Preference() {
  
  withoutfine=14;
  dayfine=2;
  username="admin";
  setPassword("admin");
	  
  }
  public class AlertMaker{
  
  public static void  showsSimplesAlert(String title,String content) {
	  Alert alert=new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(title);;
		alert.setContentText(content);
		alert.showAndWait();
  }

  public static void  showsErrorAlert(String title,String content) {
	  Alert alert=new Alert(Alert.AlertType.ERROR);
	  	//alert.setTitle(title);
		alert.setHeaderText(title);;
		alert.setContentText(content);
		alert.showAndWait();
  }
  
  }

  
public int getWithoutfine() {
	return withoutfine;
}

public void setWithoutfine(int withoutfine) {
	this.withoutfine = withoutfine;
}

public float getDayfine() {
	return dayfine;
}

public void setDayfine(float dayfine) {
	this.dayfine = dayfine;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = DigestUtils.sha256Hex(password);
}

  	

public static void initConfig() {
	Writer writer=null;
	
	try {
		Preference preference =new Preference();
		Gson gson=new Gson();
		writer=new FileWriter(CONFIG_FILE);
		gson.toJson(preference, writer);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			writer.close();
			
		} catch (Exception e2) {
			e2.printStackTrace();
			// TODO: handle exception
		}
	}
}


public static Preference getPreference() {
	Gson gson=new Gson();
	Preference preference=new Preference();
	try {
		 preference=gson.fromJson(new FileReader(CONFIG_FILE), Preference.class);
		
	} catch (FileNotFoundException e) {
		initConfig();
		e.printStackTrace();
	}
	return preference;
}

public static void  writePreferenceofFile(Preference preference) {
	Writer writer=null;
	Gson gson=new Gson();
	try {
		writer=new FileWriter(CONFIG_FILE);
		gson.toJson(preference,writer);
		
		AlertMaker.showsSimplesAlert("SUCCESS", "Setting Updated");
		
	} catch (IOException e) {
		AlertMaker.showsErrorAlert("ERROR", "Can't save configuration file");
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally {
		try {
			writer.close();
		} catch (Exception e2) {
			e2.printStackTrace();
			// TODO: handle exception
		}
	}
	
}
}
