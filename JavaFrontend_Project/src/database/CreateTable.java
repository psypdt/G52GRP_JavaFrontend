package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable extends DatabaseManipulator
{
	static Connection m_Connection; // The connection to the database.
    static Statement m_Statement; // The SQL query to be executed.
    private String m_SqlQuery = "create table "+
            "newTable"+ // Name given to the new table is "newTable"
            "(column1" + " int(10),"+ // Column 1 of the new table is "column1".
            "column2" + " varchar(30));"; // Column 2 of the new table is "column2".


    /**
     * Constructor for {@link CreateTable} class.
     * @implSpec The constructor will initialise {@link #m_Connection} by default using {@link #initialiseConnection()}.
     */
    public CreateTable()
    {
        super();
        m_Connection = initialiseConnection();
    }


    /**
     * @implNote If more methods are required regarding table creation, follow the structure of this method.
     * @implSpec This method requires that 2 columns are created with the table.
     * @implSpec This method will return {@code null} if the table name already exists.
     * @implSpec The columns that are created default to the types {@code int(10)} and {@code varchar(30)} respectively.
     * @param tableName The name of the table created that will be created.
     * @param column1 Name of the first column.
     * @param column2 Name of the second column.
     * @return The {@code tableName} if the table creation succeeds, else {@code null} is returned.
     */
    public String createTable(String tableName, String column1, String column2)
    {
    	try
        {
            String sqlQuery = m_SqlQuery;

            // Check that all arguments are non empty strings
            if(!tableName.isEmpty() && !column1.isEmpty() && !column2.isEmpty())
            {
                sqlQuery = "create table "+
                        tableName+ // Name given to the table
                        "("+column1+" int(10),"+ // Column 1 of the new table.
                        column2+" varchar(30));"; // Column 2 of the new table.
            }

    		m_Statement = m_Connection.createStatement();
    		m_Statement.executeUpdate(sqlQuery);
    		m_Connection.close();

    		return tableName;
    	}
    	catch (SQLException e)
        {
            System.out.println("create fail" + e.getMessage());
            return null;
        }
    }


    /**
     * Setter for {@link #m_SqlQuery}, allows developer to set a default query.
     * @param newSqlQuery the new query that will be assigned to {@link #m_SqlQuery}.
     */
    public void setSqlQuery(String newSqlQuery)
    {
        this.m_SqlQuery = newSqlQuery;
    }
}
