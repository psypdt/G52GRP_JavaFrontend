package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class CreateTable {
	static Connection conn;    
    static Statement st;
    /**
     * 
     * @return con database connection
     */
    public static Connection getConnection() {    
        Connection con = null;  //Create a Connection object to connect to the database 
        try {    
            Class.forName("com.mysql.cj.jdbc.Driver");// load Mysql driver   
                
            con = DriverManager.getConnection(    
                    "jdbc:mysql://localhost:3306/database", "root", "sh279000");// create database connection  
                
        } catch (Exception e) {    
            System.out.println("connect to the database fail" + e.getMessage());    
        }    
        return con; //Returns the established database connection
    }  
    /**
     * 
     * @param tablename the name for the table created
     */
    public void createtable(String tablename) {
    	conn = getConnection();
    	try {
    		String sql = "create table "+tablename+"(UserId int(10),Username varchar(30));";
    		st = (Statement) conn.createStatement(); 
    		int count = st.executeUpdate(sql);
    		System.out.println("create " + count + "new table");
    		conn.close();
    	}catch (SQLException e) {    
            System.out.println("create fail" + e.getMessage());    
        }    
}

}
