package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @implNote This is a specific case and is intended to illustrate how a class that manipulates grades is to be implemented.
 * @implSpec The {@link #m_FallbackQuery} is intended to be a safety measure, should a query seem incomplete, this statement
 * will be executed with the intention that nothing will be done to the database, hence why it is an empty string.
 */
public class Grades extends DatabaseManipulator
{
	static Connection m_Connection; // The connection to the database that stores grades.
    static Statement m_Statement; // The SQL statement that will be executed.
	private final String m_FallbackQuery = ""; // By default nothing will be done to the database

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
	 * @implNote This is an example of how a function that inserts student information should look like.
	 * @implSpec By default this method will execute {@link #m_FallbackQuery} if the supplied arguments don't conform
	 * to the constraints in respect to the function argument (see {@code @param} field for parameter constrains).
	 * @param studentID The student's ID. Constraint: Must be greater than 0 ({@code studentID > 0}).
	 * @param studentName The student's name. Constraint: Can't be an empty string.
	 * @param moduleID The module ID. Constraint: Can't be an empty string.
	 * @param moduleName The module name. Constraint: Can't be an empty string.
	 * @param grade The student's grade for this module. Constraint: Must be greater than/equal to 0 {@code grade >= 0}.
	 * @param credit The credits that the module is worth. Constraint: Must be greater than/equal to 0 {@code credit >= 0}.
	 */
	public void insert(int studentID,String studentName, String moduleID, String moduleName, int grade, int credit)
	{
		String sqlQuery = m_FallbackQuery; // Safeguard should any arguments be incomplete.

		try
		{
			// Verify that all arguments are "complete".
			if(studentID > 0 && !studentName.isEmpty() && !moduleID.isEmpty() && !moduleName.isEmpty() &&
					grade >= 0 && credit >= 0)
			{
				sqlQuery = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)"
						+ " VALUES ('"+studentID+"'," +
						"'"+studentName+"'," +
						"'"+moduleID+"'," +
						"'"+moduleName+"'," +
						"'"+grade+"'," +
						"'"+credit+"')";
			}

			m_Statement = m_Connection.createStatement(); // Create a Statement object for executing static SQL statements.
			m_Statement.executeUpdate(sqlQuery); // SQL statement that performs the insert operation and returns the number of inserted data.
			m_Connection.close(); //Close the database connection.
		}
		catch (SQLException e)
		{
			System.out.println("Grade insertion failed: " + e.getMessage());
		}
	}


	/**
	 * @implSpec By default this method will execute {@link #m_FallbackQuery} if the supplied arguments don't conform
	 * to the constraints listed with the respective argument.
	 * @param newModuleName Name of the new Module. Constraint: Can't be an empty string.
	 * @param moduleID Module's ID. Constraint: Can't be an empty string.
	 */
	 public void update(String newModuleName, String moduleID)
	 {
	 	String sqlQuery = m_FallbackQuery;

	 	// Verify that the given arguments are acceptable.
	 	if(!newModuleName.isEmpty() && !moduleID.isEmpty())
		{
			sqlQuery = "update  grades set Modulename ='"+
					newModuleName+
					"' where ModuleID = '"
					+moduleID+"'"; // SQL statement that updates data
		}

		 try
		 {
			m_Statement = m_Connection.createStatement(); // Create Statement object to execute static SQL statements.
			m_Statement.executeUpdate(sqlQuery);// SQL statement that performs the update operation and returns the number of updates.
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
