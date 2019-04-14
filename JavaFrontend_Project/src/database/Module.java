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
    private static Connection m_Connection; // The connection to the database.
    private static Statement m_Statement; // The SQL statement object that will execute on the database.
    private final String m_FallbackQuery = ""; // This is a safeguard, doesn't change database, replaces odd queries.


    /**
     * Constructor for the {@link Module} class.
     * @implSpec By default this constructor will only call the super class {@link DatabaseManipulator}, it will not
     *          initialise the {@link #m_Connection}.
     */
    public Module()
    {
        super();
    }


    /**
     * @implSpec This method will fail if the table already exists.
     * @implSpec This method will create the following table columns:
     *          {@code ModuleID}. Type is {@code varchar(15)}.
     *          {@code ModuleName}. Type is {@code varchar(100)}.
     * @throws SQLException If the table creation fails.
     */
    public void createTabel() throws SQLException
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as described in super class.

        String sqlQuery = "create table module " +
                "(ModuleID " + "varchar(15)," +
                "ModuleName " +  "varchar(100))";

        m_Statement = m_Connection.createStatement();
        m_Statement.execute(sqlQuery);
        m_Connection.close(); // Close connection as described in super class.
    }




    /**
     * @implSpec This method inserts a new module into the Modules table in the database.
     * @param newModuleID The ID of the new module. Constraint: Can't be an empty {@link String}.
     * @param newModuleName The name of the new module. Constraint: Can't be an empty {@link String}.
     */
    public void insert(String newModuleID, String newModuleName)
    {
        m_Connection = initialiseConnection(); // Initialize connection to the database as specified by the super class.
        String sqlQuery = m_FallbackQuery; // If the constraints of the function arguments are disregarded, use this.

        // Ensure that constraints are not violated.
        if(!newModuleID.isEmpty() && !newModuleName.isEmpty())
        {
            sqlQuery = "INSERT INTO module(ModuleID, ModuleName)"
                    + " VALUES ('"+newModuleID+"','"+newModuleName+"')";  // SQL statement to inserts data into Module table.
        }

        try
        {
            m_Statement = m_Connection.createStatement(); // Create a Statement object to execute a static SQL query.
            m_Statement.executeUpdate(sqlQuery); // SQL statement that performs the insert operation.
            m_Connection.close(); // Close the database connection as instructed by the super class.
        }
        catch (SQLException e)
        {
            System.out.println("Insertion of: " + newModuleName + " with ID: "  + newModuleID + " has failed: " + e.getMessage());
        }    
    }    
        


    /**
     * @implSpec This method will update the {@code ModuleName} to {@code newModuleName}
     *          for the module with the specified {@code moduleID}.
     * @implSpec This method will only except non-empty strings, on violated of this {@link #m_FallbackQuery} is sent.
     * @implSpec There is currently no way of informing the developer if a query was deemed "non-compliant"/ empty.
     * @param newModuleName The new name for the module with ID {@code moduleID}. Constraint: Can't be empty {@link String}.
     * @param moduleID ID of the module that's getting renamed. Constraint: Can't be empty {@link String}.
     */
    public void update(String newModuleName , String moduleID)
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as described by super class.
        String sqlQuery = m_FallbackQuery;

        // Check that argument constraints haven't been violated, construct the update query string.
        if(!newModuleName.isEmpty() && !moduleID.isEmpty())
        {
            sqlQuery =  "update module set ModuleName ='" + newModuleName +
                    "' where ModuleID = '" + moduleID + "'";
        }

        try
        {
            m_Statement = m_Connection.createStatement(); // Create a Statement object that can execute the update query.
            m_Statement.executeUpdate(sqlQuery); // SQL statement that performs the update operation.
            m_Connection.close(); // Close the database connection as detailed in the super class.
        }
        catch (SQLException e)
        {
            System.out.println("Updating the name of Module with ID: " +
                    moduleID + " to: " + newModuleName + " has failed: " + e.getMessage());
        }    
    }    



    /**
     * @implSpec This method returns all rows from the Module table in the database.
     * @implSpec By default this method executes the following query: {@code "select * from module"}.
     * @return {@link String} containing the query result, returns {@code null} if an issue occurs.
     */
   public String query()
   {
       m_Connection = initialiseConnection(); // Initialize connection to database as instructed by the super class.
       String searchresult = null;

       try
       {
           String sqlQuery = "select * from module"; // SQL statement that queries data
           m_Statement = m_Connection.createStatement(); // Create a Statement object to execute the sql query.
           ResultSet resultSet = m_Statement.executeQuery(sqlQuery); // Execute SQL query statement and save the returned data.

           //DEBUG: Used to validate that the correct search result has been returned.
           //System.out.println("search result：");

           // Iterate through the resulting set while there is data that follows the current position.
           while (resultSet.next())
           {
                // Get the value based on the field name.
            	String MI1 = resultSet.getString("ModuleID");
                String Mn1 = resultSet.getString("ModuleName");

                //Output the values of the fields and record that are being looked up.
                searchresult = MI1 + " " + Mn1;

                //DEBUG: Used to see what pair has been selected.
                //System.out.println(searchresult);
           }
           m_Connection.close(); // Close the database connection since the super class demands this.

           return searchresult;
       }
       catch (SQLException e)
       {
           System.out.println("search information fail");
       }
       return null;
    }




   /**
    * @param moduleID The moduleID of the module that will be deleted. Constraint: Can't be an empty {@link String}.
    */
    public void delete(String moduleID)
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as instructed by the super class.
        String sqlQuery = m_FallbackQuery;

        if(!moduleID.isEmpty())
        {
            sqlQuery = "delete from module  where ModuleID = '"+moduleID+"'"; // SQL statement to delete data
        }

        try
        {
            m_Statement = m_Connection.createStatement(); // Create a Statement object to send the delete query.
            m_Statement.executeUpdate(sqlQuery); // Execute the SQL delete operation.
            m_Connection.close(); // Close the database connection as specified in the super class.
        }
        catch (SQLException e)
        {
            System.out.println("Failed to delete Module with ID: " + moduleID + " " + e.getMessage());
        }      
    }    
}