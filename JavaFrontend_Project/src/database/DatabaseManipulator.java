package database;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseManipulator implements DatabaseInterface
{
    /**
     * Constructor for {@link DatabaseManipulator}.
     * @implSpec The constructor will not connect to a database by default.
     */
    public DatabaseManipulator()
    {
    }

    /**
     * @implSpec This method is will initialise a connection to a predefined database.
     * @return {@code Connection} object that was created after connecting with the Database.
     */
    @Override
    public Connection initialiseConnection()
    {
        Connection connection = null;  // Create a Connection object to connect to the database.

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the Mysql driver.

            // Create a connection to the database.
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/database", // The database name is "database".
                    "root", // The username for the database login is "root".
                    "sh279000"); // The password for the database is "sh279000".
        }
        catch (Exception e)
        {
            //DEBUG: Notify the developer that the database connection has failed.
            System.out.println("Connect to the database failed " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }


    /**
     * @implSpec This method assumes that the database link is as follows {@code "jdbc:mysql://localhost:3306/"}{@code database},
     * where {@code database} is the input given to the function.
     * @implSpec Consider giving this method the ability to throw an exception if the connection fails.
     * @param database The name of the database that the connection should be made to.
     * @param username The username used to access the database.
     * @param password The password used to access the database.
     * @return {@code Connection} object that was created after connecting with the Database.
     */
    @Override
    public Connection setNewConnection(String database, String username, String password)
    {
        Connection connection = null;  // Create a Connection object to connect to the database.
        String databaseUrl = "jdbc:mysql://localhost:3306/" + database;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the Mysql driver.

            // Create a connection to the database.
            connection = DriverManager.getConnection(
                    databaseUrl, // The database name.
                    username, // The username for the database login.
                    password); // The password for the database.
        }
        catch (Exception e)
        {
            //DEBUG: Notify the developer that the database connection has failed.
            System.out.println("Connect to the database failed " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }
}
