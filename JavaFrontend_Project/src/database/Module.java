package database;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;    
import java.sql.Statement;


/**
 * @implNote Any method that connects to a database MUST call {@link #initialiseConnection()} as its first operation.
 * @implNote Any method that connects to a database MUST call {@link Connection#close()} before returning.
 */
public class Module extends DatabaseManipulator
{
    // Create static global variables   
    static Connection m_Connection;
    static Statement m_Statement;



    /**
     * @implSpec Inserts a data record and outputs the number of inserted data records.
     * @param moduleID moduleID
     * @param modulename module's name
     */
    public void insert(String moduleID, String modulename)
    {
        m_Connection = initialiseConnection(); // The first step is to get a connection, that is, to the database

        try
        {
            String sql1 = "INSERT INTO module(ModuleID, Modulename)"    
                    + " VALUES ('"+moduleID+"','"+modulename+"')";  // SQL statement to insert data

            m_Statement = m_Connection.createStatement();    // Create a Statement object for executing static SQL statements
            m_Statement.executeUpdate(sql1);  // SQL statement that performs the insert operation and returns the number of inserted data
            m_Connection.close();   //Close the database connection

        }
        catch (SQLException e)
        {
            System.out.println("Insertion of Module: " + modulename + " with ID: "  + moduleID + " has failed: " + e.getMessage());
        }    
    }    
        
    /* Updates the required records and returns the number of updated records*/
    /**
     * 
     * @param newModuleName new module name
     * @param moduleID module's ID
     */
    public void update(String newModuleName , String moduleID)
    {
        m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database

        try
        {
            String sql = "update module set Modulename ='"+newModuleName+"' where ModuleID = '"+moduleID+"'";// SQL statement that updates data

            m_Statement = m_Connection.createStatement(); //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            m_Statement.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates
            m_Connection.close();   //Close the database connection
        }
        catch (SQLException e)
        {
            System.out.println("Updating the name of Module with ID: " + moduleID + " to: " + newModuleName + " has failed: " + e.getMessage());
        }    
    }    
    	
    /* Query the database to output the required records*/    
   public String query()
   {
       String searchresult = null;
       m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database

       try
       {
           String sql1 = "select * from module"; // SQL statements that queryGrades data
           m_Statement = m_Connection.createStatement(); // Create a Statement object for executing static SQL statements, m_Statement being a local variable
           ResultSet rs1 = m_Statement.executeQuery(sql1); // Execute the SQL queryGrades statement and return the result set of the queryGrades data

           //DEBUG:
           System.out.println("search result：");

           // Iterate through the table while there is data that follows.
           while (rs1.next())
           {
                // Get the value based on the field name.
            	String MI1 = rs1.getString("ModuleID");  
                String Mn1 = rs1.getString("Modulename");

                //Output the values of the fields and record that are being looked up.
                searchresult = MI1 + " " + Mn1;
                System.out.println(searchresult);                   
           }

           m_Connection.close();   //Close the database connection

           return searchresult;
       }
       catch (SQLException e)
       {
           System.out.println("search information fail");
       }
       return null;
    }




   /**
    * 
    * @param moduleID module's ID
    */
    public void delete(String moduleID)
    {
        m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database
        try
        {
            String sql = "delete from module  where ModuleID = '"+moduleID+"'";// SQL statement to deleteGradesForModule data

            m_Statement = m_Connection.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            m_Statement.executeUpdate(sql);// Execute the SQL deleteGradesForModule statement to return the number of deleted data
            m_Connection.close();   //Close the database connection
        }
        catch (SQLException e)
        {
            System.out.println("deleteGradesForModule fail");
        }      
    }    
}