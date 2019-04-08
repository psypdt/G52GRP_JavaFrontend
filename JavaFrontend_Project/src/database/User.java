package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	static Connection conn;    
    static Statement st;    
/**
 * @param con connection class   
 * @return con connection
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
/* inserts a data record and outputs the number of inserted data records*/
/**
 * 
 * @param lastname user's last name
 * @param firstname user's first name
 * @param userID user's ID
 */
public void insert(String lastname,String firstname,int userID) {          
	conn = getConnection(); // The first step is to get a connection, that is, to the database  
    try {    
        String sql = "INSERT INTO user(Lastname, Firstname, UserID)"    
                + " VALUES ('"+lastname+"','"+firstname+"','"+userID+"')";  // SQL statement to insert data
        st = (Statement) conn.createStatement();    // Create a Statement object for executing static SQL statements   
        st.executeUpdate(sql);  // SQL statement that performs the insert operation and returns the number of inserted data    
        conn.close();   //Close the database connection      
    } catch (SQLException e) {    
        System.out.println("insert fail" + e.getMessage());    
    }    
}
/**
 * 
 * @param firstname user's first name
 * @param userID user's ID
 */
public void update(String firstname , int userID) {    
    conn = getConnection(); //Again, get the connection first, that is, connect to the database    
    try {    
        String sql = "update user set UserID = '"+userID+"' where Firstname ='"+firstname+"'";// SQL statement that updates data      
        st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable         
        st.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates     
        conn.close();   //Close the database connection            
    } catch (SQLException e) {    
        System.out.println("update fail");    
    }    
} 
public String query() {    
    String searchresult =null;
    conn = getConnection(); //Again, get the connection first, that is, connect to the database    
    try {    
        String sql1 = "select * from user";// SQL statements that query data   
        st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable        
        ResultSet rs1 = st.executeQuery(sql1);  //Execute the SQL query statement and return the result set of the query data  
        System.out.println("search result：");    
        while (rs1.next()) {
        	String Ln = rs1.getString("Lastname");  
            String Fn = rs1.getString("Firstname"); 
            String UID = rs1.getString("UserID");
            searchresult = Fn+" "+Ln + " " + UID;
            System.out.println(searchresult);  
        }
        conn.close();   //Close the database connection 
        return searchresult;   
    } catch (SQLException e) {    
        System.out.println("search information fail");    
    }
	return null;    
}
/**
 * 
 * @param userID user's ID
 */
public void delete(int userID) {    
    conn = getConnection(); //Again, get the connection first, that is, connect to the database  
    try {    
        String sql = "delete from user  where UserID = '"+userID+"'";// SQL statement to delete data    
        st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable           
        st.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data        
        conn.close();   //Close the database connection       
    	} catch (SQLException e) {    
        System.out.println("delete fail");    
    		}    
		}
}
