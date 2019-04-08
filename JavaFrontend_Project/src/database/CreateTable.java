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
     * @param con connection class
     * @return con 
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
     * @param column1 first column name
     * @param column2 second column name
     */
    public String createtable(String tablename, String column1, String column2) {
    	conn = getConnection();
    	String name = tablename;
    	try {
    		String sql = "create table "+tablename+"("+column1+" int(10),"+column2+" varchar(30));";
    		st = (Statement) conn.createStatement(); 
    		st.executeUpdate(sql);
    		//System.out.println("create " + count + "new table");
    		conn.close();
    		return name;
    	}catch (SQLException e) {    
            System.out.println("create fail" + e.getMessage());   
            return null;
        }    
}

}
