package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SampleController implements Initializable {
	public Stage stage;
	private Scene scene;
	private Parent root;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;
    
    @FXML
    private Button btnChat;
    
    @FXML
    private TableView<Books> tvBooks1;
    @FXML
    private TableColumn<Books,String> colAuthor1;

    @FXML
    private TableColumn<Books,Integer> colPages1;

    @FXML
    private TableColumn<Books,String> colTitle1;

    @FXML
    private TableColumn<Books,Integer> colYear1;

    @FXML
    private TableColumn<Books,Integer> colid1;

    @FXML
    private TextField tfAuthor1;

    @FXML
    private TextField tfPages1;

    @FXML
    private TextField tfTitle1;

    @FXML
    private TextField tfYear1;

    @FXML
    private TextField tfid1;



    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
    	if(event.getSource()==btnInsert) {
    		insertRecord();
    	}
    	else if(event.getSource()==btnUpdate){
    		updateRecord();
    		
    	}
    	else if(event.getSource()==btnChat){
    		chat(event);
    		
    	}
    	else {
    		deleteRecord();
    		
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
    public ObservableList<Books> getBooksList(){
    	ObservableList<Books> bookList=FXCollections.observableArrayList();
		Connection conn =getConnection();
		String query ="SELECT * FROM books";
		Statement st;
		ResultSet rs;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
			Books books ;
			while(rs.next()) {
				books = new Books(rs.getInt("id")
								,rs.getString("title")
								,rs.getString("author")
								,rs.getInt("year")
								,rs.getInt("pages"));
				bookList.add(books);
							}
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return bookList;
    }
    public void showBooks() {
    	ObservableList<Books> list = getBooksList();
    	
    	colid1.setCellValueFactory(new PropertyValueFactory<Books,Integer>("id"));	
    	colTitle1.setCellValueFactory(new PropertyValueFactory<Books,String>("title"));
    	colYear1.setCellValueFactory(new PropertyValueFactory<Books,Integer>("year"));	
    	colAuthor1.setCellValueFactory(new PropertyValueFactory<Books,String>("author"));	
    	colPages1.setCellValueFactory(new PropertyValueFactory<Books,Integer>("pages"));	
    	tvBooks1.setItems(list);
    	
    }
    
    private void insertRecord() {
    	String query = "INSERt INTO books VALUES (" +tfid1.getText()
    										+",'"+tfTitle1.getText()
									    	+"','"+tfAuthor1.getText()
									    	+"',"+tfYear1.getText()
									    	+","+tfPages1.getText()
									    	+")";
        executeQuery(query);
        showBooks();

    							}
    
    private void updateRecord(){
        String query = "UPDATE  books SET title  = '" + tfTitle1.getText() + "', author = '" + tfAuthor1.getText() + "', year = " +
                tfYear1.getText() + ", pages = " + tfPages1.getText() + " WHERE id = " + tfid1.getText() + "";
        executeQuery(query);
        showBooks();
    }
    private void deleteRecord(){
        String query = "DELETE FROM books WHERE id =" + tfid1.getText() + "";
        executeQuery(query);
        tfid1.setText("");
		tfTitle1.setText("");
		tfAuthor1.setText("");
		tfYear1.setText("");
		tfPages1.setText("");
        showBooks();
    }
	private void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st=conn.createStatement();
			st.executeUpdate(query);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    @FXML
    void handleMouseAction(MouseEvent event) {
		Books book = tvBooks1.getSelectionModel().getSelectedItem();
		tfid1.setText(""+book.getId());
		tfTitle1.setText(book.getTitle());
		tfAuthor1.setText(book.getAuthor());
		tfYear1.setText(""+book.getYear());
		tfPages1.setText(""+book.getPages());
    }	
    
    public void chat(ActionEvent event) throws IOException {
  	  root = FXMLLoader.load(getClass().getResource("Server.fxml"));
  	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  	  scene = new Scene(root);
  	  stage.setScene(scene);
  	  stage.show();
  	 }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showBooks();
	}
    }

