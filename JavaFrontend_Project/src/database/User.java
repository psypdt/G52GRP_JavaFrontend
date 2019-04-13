package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *@implNote Any method that connects to a database MUST call {@link #initialiseConnection()} as its first operation.
 *@implNote Any method that connects to a database MUST call {@link Connection#close()} before returning.
 */
public class User extends DatabaseManipulator
{
	static Connection m_Connection; // Connection to the database.
    static Statement m_Statement; // Statement that will be executed.
    private final String m_Fallback = ""; // Safety measure, sent if query doesn't adhere to standard.


    /**
     * Constructor for {@link User} class.
     * @implSpec By default the constructor does not initialize {@link #m_Connection}, all it does is call {@code super()}.
     */
    public User() { super(); }



    /**
     * @implSpec This method
     * @param lastName Students last name. Constraint: Can't be an empty {@link String}.
     * @param firstName Students first name. Constraint: Can't be an empty  {@link String}.
     * @param userID Student's ID. Constraint: ID must be greater than 0, {@code userID > 0}.
     */
    public void insert(String lastName, String firstName, int userID)
    {
        m_Connection = initialiseConnection(); // The first step is to get a connection, that is, to the database

        try
        {
            String sql = "INSERT INTO user(Lastname, Firstname, UserID)"
                    + " VALUES ('"+lastName+"','"+firstName+"','"+userID+"')";  // SQL statement to insert data

            m_Statement = m_Connection.createStatement();    // Create a Statement object for executing static SQL statements
            m_Statement.executeUpdate(sql);  // SQL statement that performs the insert operation and returns the number of inserted data
            m_Connection.close();   //Close the database connection
        }
        catch (SQLException e)
        {
            System.out.println("Inserting a new user failed: " + e.getMessage());
        }
    }


    /**
     *
     * @param firstname user's first name
     * @param userID user's ID
     */
    public void update(String firstname , int userID) {
        m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database
        try {
            String sql = "update user set UserID = '"+userID+"' where Firstname ='"+firstname+"'";// SQL statement that updates data
            m_Statement = m_Connection.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            m_Statement.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates
            m_Connection.close();   //Close the database connection
        } catch (SQLException e) {
            System.out.println("update fail");
        }
    }
    public String query() {
        String searchresult =null;
        m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database
        try {
            String sql1 = "select * from user";// SQL statements that query data
            m_Statement = m_Connection.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            ResultSet rs1 = m_Statement.executeQuery(sql1);  //Execute the SQL query statement and return the result set of the query data
            System.out.println("search result：");
            while (rs1.next()) {
                String Ln = rs1.getString("Lastname");
                String Fn = rs1.getString("Firstname");
                String UID = rs1.getString("UserID");
                searchresult = Fn+" "+Ln + " " + UID;
                System.out.println(searchresult);
            }
            m_Connection.close();   //Close the database connection
            return searchresult;
        } catch (SQLException e) {
            System.out.println("search information fail");
        }
        return null;
    }



    /**
     *
     * @param userID user's ID
     */
    public void delete(int userID) {
        m_Connection = initialiseConnection(); //Again, get the connection first, that is, connect to the database
        try {
            String sql = "delete from user  where UserID = '"+userID+"'";// SQL statement to delete data
            m_Statement = m_Connection.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
            m_Statement.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data
            m_Connection.close();   //Close the database connection
            } catch (SQLException e) {
            System.out.println("delete fail");
                }
            }
}
