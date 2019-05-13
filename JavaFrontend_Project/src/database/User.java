package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *@implNote Any method that connects to a database MUST call {@link #initialiseConnection()} as its first operation.
 *@implNote Any method that connects to a database MUST call {@link Connection#close()} before returning.
 */
public class User extends DatabaseManipulator
{
	private static Connection m_Connection; // Connection to the database.
    private static Statement m_Statement; // Statement that will be executed.
    private final String m_FallbackQuery = ""; // Safety measure, sent if query doesn't adhere to standard.



    /**
     * Constructor for the {@link User} class.
     * @implSpec By default the constructor does not initialize {@link #m_Connection}, all it does is call {@code super()}.
     */
    public User() { super(); }



    /**
     * @implSpec This method will fail if the table already exists.
     * @implSpec By default this method will create the following columns:
     *          {@code LastName}. Type is {@code varchar(100)}.
     *          {@code FirstName}. Type is {@code varchar(100)}.
     *          {@code UserID}. Type is {@code int(10)}.
     * @throws SQLException If table creation fails.
     */
    public void createTable() throws SQLException
    {
        m_Connection = initialiseConnection(); // Initialize connection like super class demands.

        String sqlQuery = "create table user " +
                "(LastName varchar(100)," +
                "FirstName varchar(100)," +
                "UserID int(10))";

        m_Statement = m_Connection.createStatement();
        m_Statement.execute(sqlQuery);
        m_Connection.close(); // Close connection like super class expects.
    }




    /**
     * @implSpec This method inserts a new student into the database.
     * @param lastName Students last name. Constraint: Can't be an empty {@link String}.
     * @param firstName Students first name. Constraint: Can't be an empty  {@link String}.
     * @param userID Student's ID. Constraint: ID must be greater than 0, {@code userID > 0}.
     */
    public void insert(String lastName, String firstName, int userID)
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as specified by super class.
        String sqlQuery = m_FallbackQuery;


        if(!lastName.isEmpty() && !firstName.isEmpty() && userID >0)
        {
             sqlQuery = "INSERT INTO user(LastName, FirstName, UserID)"
                    + " VALUES ('"+lastName+"','"+firstName+"','"+userID+"')";  // SQL statement to insert data
        }

        try
        {
            m_Statement = m_Connection.createStatement(); // Create a Statement object to execute the SQL statement.
            m_Statement.executeUpdate(sqlQuery); // SQL statement that performs the insert operation.
            m_Connection.close(); // Close the database connection as specified by the super class.
        }
        catch (SQLException e)
        {
            System.out.println("Inserting a new user failed: " + e.getMessage());
        }
    }


    /**
     * @implSpec The {@link #m_FallbackQuery} query will be executed of parameter constrains are violated.
     * @param firstName The new firstName given to the student. Constraint: Can't be an empty {@link String}.
     * @param userID The ID of the student who's name will be changed. Constraint: Must be greater than 0 {@code userID > 0}.
     */
    public void update(String firstName , int userID)
    {
        m_Connection = initialiseConnection(); //Create connection to database.
        String sqlQuery = m_FallbackQuery;

        if(!firstName.isEmpty() && userID > 0)
        {
            sqlQuery = "update user set UserID = '"+userID+"' where FirstName ='"+firstName+"'";// SQL updates statement.
        }

        try
        {
            m_Statement = m_Connection.createStatement(); //Create a Statement that will execute the update query.
            m_Statement.executeUpdate(sqlQuery);// SQL statement that performs the update operation.
            m_Connection.close(); // Close the database connection as specified by the super class.
        }
        catch (SQLException e)
        {
            System.out.println("update fail");
        }
    }


    /**
     * @return {@link String} containing all the users in the {@code user} table in the database.
     */
    public String query()
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as specified by super class.
        String searchResult = null;

        try
        {
            String sqlQuery = "select * from user";// SQL statements that query data

            m_Statement = m_Connection.createStatement(); // Create a Statement object to execute the sql query.
            ResultSet resultSet = m_Statement.executeQuery(sqlQuery); // Execute SQL query statement and save the returned data.

            //DEBUG: View what the query returned.
            //System.out.println("search result：");

            while (resultSet.next())
            {
                String Ln = resultSet.getString("LastName");
                String Fn = resultSet.getString("FirstName");
                String UID = resultSet.getString("UserID");
                searchResult = Fn+" "+Ln + " " + UID;

                //DEBUG: View what the search result was.
                //System.out.println(searchresult);
            }
            m_Connection.close(); // Close the database connection as specified by the super class.

            return searchResult;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to query user table " + e.getMessage());
        }
        return null;
    }



    /**
     * @implSpec The {@link #m_FallbackQuery} query will be executed of parameter constrains are violated.
     * @param userID ID of the user that will be deleted. Constraint: Must be greater than 0, {@code userID > 0}.
     */
    public void delete(int userID)
    {
        m_Connection = initialiseConnection(); // Initialize connection to database as specified by super class.
        String sqlQuery = m_FallbackQuery;

        if(userID > 0)
        {
            sqlQuery = "delete from user  where UserID = '"+userID+"'"; // SQL statement to delete the user.
        }

        try
        {
            m_Statement = m_Connection.createStatement(); //Create a Statement that will execute the delete query.
            m_Statement.executeUpdate(sqlQuery);// SQL statement that performs the delete operation.
            m_Connection.close(); // Close the database connection as specified by the super class.
        }
        catch (SQLException e)
        {
            System.out.println("Deleting user with ID: " + userID + " failed. " + e.getMessage());
        }
    }
}
