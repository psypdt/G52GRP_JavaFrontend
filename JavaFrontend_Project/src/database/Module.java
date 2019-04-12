package database;


import java.sql.Connection;
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement;


public class Module
{
    // Create static global variables   
    static Connection conn;   
    static Statement st;
   

    /**
     * @implSpec Function to get a database connection.
     * @return con {@link Connection} object created through the database connection.
     */
    public static Connection getConnection()
    {
        Connection con = null;  //Create a Connection object to connect to the database 

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");// load Mysql driver.
            con = DriverManager.getConnection(    
                    "jdbc:mysql://localhost:3306/database", "root", "sh279000");// create database connection  
                
        } catch (Exception e) {    
            System.out.println("connect to the database fail" + e.getMessage());    
        }    
        return con; //Returns the established database connection
    }


    /**
     * @implSpec Inserts a data record and outputs the number of inserted data records.
     * @param moduleID moduleID
     * @param modulename module's name
     */
    public void insert(String moduleID, String modulename) {               
        conn = getConnection(); // The first step is to get a connection, that is, to the database    
        try {    
            String sql1 = "INSERT INTO module(ModuleID, Modulename)"    
                    + " VALUES ('"+moduleID+"','"+modulename+"')";  // SQL statement to insert data
            st = (Statement) conn.createStatement();    // Create a Statement object for executing static SQL statements              
            st.executeUpdate(sql1);  // SQL statement that performs the insert operation and returns the number of inserted data    
            conn.close();   //Close the database connection                 
        } catch (SQLException e) {    
            System.out.println("insert fail" + e.getMessage());    
        }    
    }    
        
    /* Updates the required records and returns the number of updated records*/
    /**
     * 
     * @param newmodulename new module name
     * @param moduleID module's ID
     */
    public void update(String newmodulename , String moduleID) {    
        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
        try {    
            String sql = "update module set Modulename ='"+newmodulename+"' where ModuleID = '"+moduleID+"'";// SQL statement that updates data                   
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            st.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates                                  
            conn.close();   //Close the database connection                  
        } catch (SQLException e) {    
            System.out.println("update fail");    
        }    
    }    
    	
    /* Query the database to output the required records*/    
   public String query() {    
         String searchresult = null;   
        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
        try {    
            String sql1 = "select * from module";// SQL statements that query data   
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            ResultSet rs1 = st.executeQuery(sql1);  //Execute the SQL query statement and return the result set of the query data  
            System.out.println("search result：");    
           while (rs1.next()) { // Determine if there is another piece of data                   
                // Gets the value based on the field name  
            	String MI1 = rs1.getString("ModuleID");  
                String Mn1 = rs1.getString("Modulename");                           
                //Output the values of the fields of the record being looked up 
                searchresult = MI1 + " " + Mn1;
                System.out.println(searchresult);                   
            } 
            conn.close();   //Close the database connection   
            return searchresult;    
        } catch (SQLException e) {    
            System.out.println("search information fail");    
        }
		return null;    
    }     
    /* Delete the required records, output*/ 
   /**
    * 
    * @param moduleID module's ID
    */
    public void delete(String moduleID) {        
        conn = getConnection(); //Again, get the connection first, that is, connect to the database  
        try {    
            String sql = "delete from module  where ModuleID = '"+moduleID+"'";// SQL statement to delete data    
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            st.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data                 
            //System.out.println("delete from the module " + count + " data\n");    //Outputs the result of the delete operation                  
            conn.close();   //Close the database connection                   
        } catch (SQLException e) {    
            System.out.println("delete fail");    
        }      
    }    
}