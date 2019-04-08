package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Grades {
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
 * @param studentname student's name
 * @param studentID student's ID
 * @param moduleID this module ID
 * @param modulename this module name
 * @param grade student's grade for this module
 * @param credit this module's credit
 */
	public void insert(int studentID,String studentname, String moduleID, String modulename, int grade, int credit   ) {        
    conn = getConnection(); // The first step is to get a connection, that is, to the database  
    try {      
        String sql1 = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)" 
        		+ " VALUES ('"+studentID+"','"+studentname+"','"+moduleID+"','"+modulename+"','"+grade+"','"+credit+"')";
        st = (Statement) conn.createStatement();    // Create a Statement object for executing static SQL statements   
        st.executeUpdate(sql1);  // SQL statement that performs the insert operation and returns the number of inserted data    
        conn.close();   //Close the database connection             
    } catch (SQLException e) {    
        System.out.println("insert fail" + e.getMessage());    
    }    
}
	/**
	 * 
	 * @param newmodulename new module's name
	 * @param moduleID module's ID
	 */
	 public void update(String newmodulename, String moduleID) {    
	        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
	        try {    
	            String sql = "update  grades set Modulename ='"+newmodulename+"' where ModuleID = '"+moduleID+"'";// SQL statement that updates data   	                
	            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable  	                
	            st.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates   	                               
	            conn.close();   //Close the database connection    	                
	        } catch (SQLException e) {    
	            System.out.println("update fail");    
	        }    
	    }
	   public String query() {    
            String searchresult = null;
	        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
	        try {    
	            String sql1 = "select * from grades";// SQL statements that query data   
	            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable            
	            ResultSet rs1 = st.executeQuery(sql1);  //Execute the SQL query statement and return the result set of the query data  
	            System.out.println("search result：");    
	            while (rs1.next()) {
	            	String SI = rs1.getString("StudentID");  
	                String Sn = rs1.getString("Studentname"); 
	                String MI2 = rs1.getString("ModuleID");  
	                String Mn2 = rs1.getString("Modulename"); 
	                String Grade = rs1.getString("Grade");  
	                String Credit = rs1.getString("Credit"); 
	                searchresult = SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit;
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
	    * @param moduleID module's ID
	    */
	    public void delete(String moduleID) {            
	        conn = getConnection(); //Again, get the connection first, that is, connect to the database  
	        try {    
	            String sql = "delete from grades  where ModuleID = '"+moduleID+"'";// SQL statement to delete data    
	            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable      
	            st.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data           
	            conn.close();   //Close the database connection           
	        } catch (SQLException e) {    
	            System.out.println("delete fail");    
	        }              
	    }  	  
}
