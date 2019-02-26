package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement; 
public class Operate {
	static Connection conn;    
	static Statement st; 
	static Module Op = new Module();
	public static void main(String[] args) {
		Op.insert();   //insert data    
        Op.update();   //update data    
        Op.delete();   //delete data 
        Op.query();    //Query records and display    
	}

}