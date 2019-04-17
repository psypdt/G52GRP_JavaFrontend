package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
 * @apiNote Currently all polymorphic {@link #createTable()} methods will fail if the respective table exists already.
 *          There are no checks to prevent this from happening by default.
 */
public class DatabaseManipulator implements iDatabaseInterface
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


    /**
     * @implNote The effects of this method can't be undone, it is expected that this method is used at the
     *          developers own risk.
     * @implSpec The default implementation of this method doesn't check if the table exists or not, it just sends the query.
     *          subclasses are expected to have their own implementation of this method.
     * @param tableName The name of the table that is to be deleted from the database.
     * @throws SQLException If the specified table can't be deleted form the database.
     */
    @Override
    public void dropTable(String tableName) throws SQLException
    {
        Connection connection = initialiseConnection(); // Initialize connection as specified by super class.

        try
        {
            String sqlQuery = "";

            // Check that tableName is a non-empty strings.
            if(!tableName.isEmpty())
            {
                sqlQuery = "drop table " + tableName;
            }

            Statement statement = connection.createStatement();
            statement.execute(sqlQuery); // Execute the delete query.
            connection.close(); // Close the connection as instructed by the super class.
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to delete table " + tableName + ": " + e.getMessage());
        }
    }


    /**
     * @implSpec This method has no default implementation, it must be implemented in the subclasses since each table can
     *          have a different set of parameters.
     * @throws SQLException If the table creation fails.
     */
    @Override
    public void createTable() throws SQLException {}
}
