package database;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * @implSpec If subclasses are to have new default behaviour on creation, implement it in {@link #DatabaseManipulator()}.
 * @implSpec Every method which connects to a database MUST call {@link Connection#close()} before returning.
 * @implSpec Every method which connects to a database MUST call {@link #initialiseConnection()}, this is required since
 *          every method calls {@link Connection#close()} before it returns, hence the connection must be reinitialized.
 * @implSpec The current implementation of all these methods requires that a database already exists
 *          on a server such that it can be accessed and manipulated.
 * @apiNote This class was created since all database classes are a {@link DatabaseManipulator}, since their intention
 *         is to manipulate the database in some form, hence having this type of inheritance makes sense as all database
 *         classes must use the {{@link #initialiseConnection()}} method.
 */
public class DatabaseManipulator implements DatabaseInterface
{
    /**
     * Constructor for the {@link DatabaseManipulator} class.
     * @implSpec The constructor will not connect to a database by default, it will not initialize anything by default,
     *          it is there so that the third party extending this software may modify the default behaviour of the subclasses.
     */
    public DatabaseManipulator(){}


    /**
     * @implSpec This method initialises a connection to a predefined database {@code "jdbc:mysql://localhost:3306/database"}.
     * @implSpec There is currently no way to inform the user that their data can't be saved due to a database fault
     *           (not linked with {@link sample.gui.scraperScreen.ScraperscreenController}).
     * @return {@code Connection} object that was created after connecting with the Database successfully, returns
     *         {@code null} if the connection fails.
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
            System.out.println("Connect to the database failed: " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }


    /**
     * @implSpec This method assumes that the database link is as follows {@code "jdbc:mysql://localhost:3306/}{@code database"},
     *          where {@code database} is the input given to the function.
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
        String databaseUrl = "jdbc:mysql://localhost:3306/" + database; // Construct URL that points to database.

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
            System.out.println("Connect to the database has failed: " + e.getMessage());
        }
        return connection; // Returns the established database connection.
    }


    /**
     * @implSpec {@link DatabaseManipulator} doesn't implement a standard way of deleting a table.
     * @implSpec It is expected that default behaviour is implemented in subclasses of {@link DatabaseManipulator} via
     *          overloaded methods. Examples are {@link Grades#query()}, {@link Module#query()}, etc.
     * @return {@code null} because this is the default implementation.
     */
    @Override
    public String query() { return null; }
}
