package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @implSpec By default the query sent to create a new table is as follows: {@code "create table newTable (column1 int(10), column2  varchar(30))"}
 * @implNote Any method that connects to a database MUST call {@link #initialiseConnection()} as its first operation.
 * @implNote Any method that connects to a database MUST call {@link Connection#close()} before returning.
 */
public class CreateTable extends DatabaseManipulator
{
	private static Connection m_Connection; // The connection to the database.
    private static Statement m_Statement; // The SQL query to be executed.
    private String m_SqlDefaultQuery = "create table "+
            "newTable"+ // Name given to the new table is "newTable"
            "(column1" + " int(10),"+ // Column 1 of the new table is "column1".
            "column2" + " varchar(30));"; // Column 2 of the new table is "column2".


    /**
     * Constructor for {@link CreateTable} class.
     * @implSpec The constructor will not initialise {@link #m_Connection} by default, it only calls the constructor of
     *           the super class {@link DatabaseManipulator}.
     */
    public CreateTable() { super(); }


    /**
     * @implSpec This method will not create duplicate tables, each table name must be unique.
     * @implSpec This method requires that 2 columns are created with the table.
     * @implSpec This method will return {@code null} if the table name already exists.
     * @implSpec The columns that are created default to the types {@code int(10)} and {@code varchar(30)} respectively.
     * @implNote If more methods are required regarding table creation, follow the structure of this method.
     * @param tableName The name of the table created that will be created.
     * @param column1 Name of the first column.
     * @param column2 Name of the second column.
     * @return The {@code tableName} if the table creation succeeds, return {@code null} if table already exists.
     */
    public String createTable(String tableName, String column1, String column2)
    {
        m_Connection = initialiseConnection(); // Initialize connection as specified by super class.

    	try
        {
            String sqlQuery = m_SqlDefaultQuery;

            // Check that all arguments are non-empty strings.
            if(!tableName.isEmpty() && !column1.isEmpty() && !column2.isEmpty())
            {
                sqlQuery = "create table "+
                        tableName+ // Name given to the table
                        "("+column1+" int(10),"+ // Column 1 of the new table.
                        column2+" varchar(30));"; // Column 2 of the new table.
            }

    		m_Statement = m_Connection.createStatement();
    		m_Statement.executeUpdate(sqlQuery);
    		m_Connection.close(); // Close the connection as instructed by the super class.

    		return tableName;
    	}
    	catch (SQLException e)
        {
            System.out.println("create fail" + e.getMessage());
            return null;
        }
    }


    /**
     * Setter for {@link #m_SqlDefaultQuery}, allows developer to set a default query.
     * @param newSqlQuery the new query that will be assigned to {@link #m_SqlDefaultQuery}.
     */
    public void setSqlDefaultQuery(String newSqlQuery)
    {
        this.m_SqlDefaultQuery = newSqlQuery;
    }
}