package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Node;


public class LoginController {
	public Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button btnlogin;

    @FXML
    private Button btnsignup;

    @FXML
    private TextField tfpassword;

    @FXML
    private TextField tfusername;

    @FXML
    public void LoginButtonOnAction(ActionEvent event) throws IOException {
    	if(event.getSource()==btnlogin) {
    		
    	if(validateLogin()) {
    		Login(event);
    	}
    }
    }
    public Connection getConnection() {
    	Connection conn;
		String drivername="com.mysql.jdbc.Driver";
		try {
			Class.forName(drivername);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
    		conn=DriverManager.getConnection("jdbc:mysql://localhost/library","root","");
    		return conn;
    	}
    	catch(Exception e) {
    		System.out.println(" DB Error:"+e.getMessage());
    	}
		return null;
    	
    }
    public  boolean validateLogin() {
	Connection conn =getConnection();
	String query="select count(1) from users where username='"+tfusername.getText()+"' AND password='"+tfpassword.getText()+"'";
	Statement st;
	ResultSet rs;
	boolean bool = false;
	try {
		st = conn.createStatement();
		rs = st.executeQuery(query);
		while(rs.next()) {
			if(rs.getInt(1)==1) {
				System.out.println("worked");
				return !bool ;
			}
			else {
				System.out.println("failed");	
			}
			}
		
	
	}catch(Exception e) {
		e.printStackTrace();
		
	}
	return bool;
    }
    
    public void Login(ActionEvent event) throws IOException {
    	  root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	  scene = new Scene(root);
    	  stage.setScene(scene);
    	  stage.show();
    	 }
    	 
    

}
