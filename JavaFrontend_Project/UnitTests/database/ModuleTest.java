package database;


import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;


public class ModuleTest {
static Connection conn;  
	
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

	@Test
	public void testinsert() {
		Module module = new Module();
		module.insert("G52LAC", "Language and Computation");
		String testresult = module.query();
		assertEquals("G52LAC Language and Computation",testresult);
	}
	@Test
	public void testupdate() {
		Module module = new Module();
		module.update("Languages and Computations", "G52LAC");
		String testresult = module.query();
		assertEquals("G52LAC Languages and Computations",testresult);
	}
	@Test
	public void testxdelete() {
		Module module = new Module();
		module.delete("G52LAC");
		String testresult = module.query();
		assertEquals(null,testresult);
		
	}	
}
