package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Grades extends DatabaseManipulator
{
	static Connection m_Connection;
    static Statement m_Statement;

	/**
	 * Constructor for {@link Grades} class.
	 * @implSpec By default, the constructor calls the {@link #initialiseConnection()} method from {@link DatabaseManipulator}.
	 */
	public Grades()
	{
		super();
		m_Connection = initialiseConnection();
	}


	/**
	 * @implNote This is an example of how a function that inserts student information should look like
	 * @param studentName The student's name.
	 * @param studentID The student's ID.
	 * @param moduleID The module ID.
	 * @param moduleName The module name.
	 * @param grade The student's grade for this module.
	 * @param credit The credits that the module is worth.
	 */
	public void insert(int studentID,String studentName, String moduleID, String moduleName, int grade, int credit)
	{
		try
		{
			String sql1 = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)"
					+ " VALUES ('"+studentID+"'," +
					"'"+studentName+"'," +
					"'"+moduleID+"'," +
					"'"+moduleName+"'," +
					"'"+grade+"'," +
					"'"+credit+"')";

			m_Statement = m_Connection.createStatement(); // Create a Statement object for executing static SQL statements.
			m_Statement.executeUpdate(sql1); // SQL statement that performs the insert operation and returns the number of inserted data.
			m_Connection.close(); //Close the database connection.
		}
		catch (SQLException e)
		{
			System.out.println("Grade insertion failed " + e.getMessage());
		}
	}


	/**
	 * 
	 * @param newModuleName new module's name
	 * @param moduleID module's ID
	 */
	 public void update(String newModuleName, String moduleID)
	 {
		 try
		 {
			String sql = "update  grades set Modulename ='"+
					newModuleName+
					"' where ModuleID = '"
					+moduleID+"'"; // SQL statement that updates data
			m_Statement = m_Connection.createStatement(); // Create Statement object to execute static SQL statements.
			m_Statement.executeUpdate(sql);// SQL statement that performs the update operation and returns the number of updates
			m_Connection.close();   // Close the database connection.
		 }
		 catch (SQLException e)
		 {
		 	System.out.println("update fail");
		 }
	 }

	 public String query()
	 {
		String searchresult = null;

		 try
		 {
			 String  sql1 = "select * from grades"; // SQL statements that query data
			 m_Statement = m_Connection.createStatement(); // Create Statement object to execute static SQL.
			 ResultSet rs1 = m_Statement.executeQuery(sql1); // Execute the SQL query statement, return the query result.

			 System.out.println("search result：");

			 while (rs1.next())
			 {
			 	String SI = rs1.getString("StudentID");
				String Sn = rs1.getString("Studentname");
				String MI2 = rs1.getString("ModuleID");
				String Mn2 = rs1.getString("Modulename");
				String Grade = rs1.getString("Grade");
				String Credit = rs1.getString("Credit");
				searchresult = SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit;
				System.out.println(searchresult);
			 }

			 m_Connection.close();   //Close the database connection
			 return searchresult;
		 }
		 catch (SQLException e)
		 {
			System.out.println("search information fail");
		 }
		 return null;
	 }

   /**
	*
	* @param moduleID module's ID
	*/
	public void delete(String moduleID)
	{
		try {
			String sql = "delete from grades  where ModuleID = '"+moduleID+"'";// SQL statement to delete data
			m_Statement = (Statement) m_Connection.createStatement();    //Create a Statement object for executing static SQL statements, m_Statement being a local variable
			m_Statement.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data
			m_Connection.close();   //Close the database connection
		} catch (SQLException e) {
			System.out.println("delete fail");
		}
	}
}
