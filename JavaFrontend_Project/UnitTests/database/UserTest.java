package database;
import database.User;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class UserTest {
static Connection conn;  
	
	public static Connection getConnection() {    
        Connection con = null;  //Create a Connection object to connect to the database 
        try {    
            Class.forName("com.mysql.cj.jdbc.Driver");// load Mysql driver   
                
            con = DriverManager.getConnection(    
                    "jdbc:mysql://localhost:8080/test", "root", "");// create database connection
                
        } catch (Exception e) {    
            System.out.println("connect to the database fail" + e.getMessage());    
        }    
        return con; //Returns the established database connection
    }

	@Test
	public void testinsert() {
		User user = new User();
		user.insert("Wang", "Y",4315722);
		String testresult = user.query();
		assertEquals("Y Wang 4315722",testresult);
	}

	@Test
	public void testupdate() {
		User user = new User();
		user.update("Y", 14375122);
		String testresult = user.query();
		assertEquals("Y Wang 14375122",testresult);
	}

	@Test
	public void testxdelete() {
		User user = new User();
		user.delete(14375122);
		String testresult = user.query();
		assertEquals(null,testresult);
		
	}

}
