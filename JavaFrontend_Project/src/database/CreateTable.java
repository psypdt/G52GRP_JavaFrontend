package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateTable extends DatabaseManipulator
{
	static Connection m_Connection;
    static Statement m_Statement;

    /**
     * Constructor for {@link CreateTable}.
     * @implSpec The constructor will initialise {@link #m_Connection} by default.
     */
    public CreateTable()
    {
        super();
        m_Connection = initialiseConnection();
    }


    /**
     * @implSpec This method requires that 2 columns are created with the table.
     * @param tableName The name of the table created that will be created.
     * @param column1 Name of the first column
     * @param column2 Name of the second column
     * @return The name of the {@code tableName} if the table creation succeeds
     */
    public String createTable(String tableName, String column1, String column2)
    {
    	m_Connection = setNewConnection();

    	try
        {
            System.out.println("Constructing SQL query");
    		String sql = "create table "+tableName+"("+column1+" int(10),"+column2+" varchar(30));";
    		m_Statement = (Statement) m_Connection.createStatement();
    		System.out.println("About to execute SQL");
    		m_Statement.executeUpdate(sql);
    		m_Connection.close();
    		return tableName;
    	}
    	catch (SQLException e) {
            System.out.println("create fail" + e.getMessage());
            return null;
        }
    }

}
