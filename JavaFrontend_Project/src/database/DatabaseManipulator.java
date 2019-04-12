package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * @implSpec The current implementation of all these methods requires that a database already exists
 * on a server such that it can be accessed and manipulated.
 * @apiNote This class was created since all database classes are of type {@link DatabaseManipulator}, since the intention
 * is to manipulate the database in some form, hence having this type of inheritance makes sense, since all database
 * classes must use the {{@link #initialiseConnection()}} method.
 */
public class DatabaseManipulator implements DatabaseInterface
{
    /**
     * Constructor for {@link DatabaseManipulator} class.
     * @implSpec The constructor will not connect to a database by default.
     */
    public DatabaseManipulator() {}


    /**
     * @implSpec This method initialises a connection to a predefined database {@code "jdbc:mysql://localhost:3306/database"}.
     * @implSpec There is currently no way to inform the user that their data can't be saved due to a database fault
     * (not linked with {@link sample.gui.scraperScreen.ScraperscreenController}).
     * @return {@code Connection} object that was created after connecting with the Database successfully, returns
     * {@code null} if the connection fails.
     */
    @Override
    public Connection initialiseConnection()
    {
        Connection connection = null; // Create a Connection object to connect to the database.

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
            // DEBUG: Notify the developer that the database connection has failed.
            System.out.println("Connect to the database failed " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }


    /**
     * @implSpec This method assumes that the database link is as follows {@code "jdbc:mysql://localhost:3306/}{@code database"},
     * where {@code database} is the input given to the function.
     * @implSpec Consider giving this method the ability to throw an exception if the connection fails.
     * @param database The name of the database that the connection should be made to.
     * @param username The username used to access the database.
     * @param password The password used to access the database.
     * @return {@code Connection} object that was created after connecting with the Database, {@code null} it fails.
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
            // DEBUG: Notify the developer that the database connection has failed.
            System.out.println("Connect to the database failed " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }
}
