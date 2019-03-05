package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Grades {
	static Connection conn;    
    static Statement st; 

    
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

	public void insert(String name) {    
    
    conn = getConnection(); // The first step is to get a connection, that is, to the database  

    try {      
        String sql1 = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)" 
        		+ " VALUES ('4315722','"+name+"','G52OSC','Opreating system','100','10')";
        String sql2 = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)" 
        		+ " VALUES ('4315722','"+name+"','G52ACE','Algorithm efficiency','95','20')";// SQL statement to insert data
        st = (Statement) conn.createStatement();    // Create a Statement object for executing static SQL statements   
            
        int count1 = st.executeUpdate(sql1);  // SQL statement that performs the insert operation and returns the number of inserted data    
        int count2 = st.executeUpdate(sql2);
        System.out.println("insert into grades " + count1 + " data"); //Outputs the processing results of the insert operation   
        System.out.println("insert into grades " + count2 + " data");
        conn.close();   //Close the database connection   
            
    } catch (SQLException e) {    
        System.out.println("insert fail" + e.getMessage());    
    }    
}
	 public void update() {    
	        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
	        try {    
	            String sql = "update  grades set Modulename ='Operating system and concurrency' where ModuleID = 'G52OSC'";// SQL statement that updates data   
	                
	            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable  
	                
	            int count = st.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates   
	                
	            System.out.println("update the grades data  " + count + " data");      //Outputs the processing results of the update operation  
	                
	            conn.close();   //Close the database connection    
	                
	        } catch (SQLException e) {    
	            System.out.println("update fail");    
	        }    
	    }
	   public void query() {    
           
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
	                System.out.println(SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit);  
	            }
	            conn.close();   //Close the database connection   
	                
	        } catch (SQLException e) {    
	            System.out.println("search information fail");    
	        }    
	    }  
	    public void delete() {    
	        
	        conn = getConnection(); //Again, get the connection first, that is, connect to the database  
	        try {    
	            String sql = "delete from grades  where ModuleID = 'G52OSC'";// SQL statement to delete data    
	            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable 
	                
	            int count = st.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data  
	                
	            System.out.println("delete from the grades " + count + " data\n");    //Outputs the result of the delete operation  
	                
	            conn.close();   //Close the database connection    
	                
	        } catch (SQLException e) {    
	            System.out.println("delete fail");    
	        }    
	            
	    }  	 
	 
	 
}
