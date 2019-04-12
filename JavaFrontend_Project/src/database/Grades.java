package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @implNote Any method that connects to a database MUST call {@link #initialiseConnection()} as its first operation.
 * @implNote Any method that connects to a database MUST call {@link Connection#close()} before returning.
 * @implNote This is a specific case and is intended to illustrate how a class that manipulates grades is to be implemented.
 * @implSpec The {@link #m_FallbackQuery} is intended to be a safety measure, should a queryGrades seem incomplete, this statement
 * will be executed with the intention that nothing will be done to the database, hence why it is an empty string.
 */
public class Grades extends DatabaseManipulator
{
	static Connection m_Connection; // The connection to the database that stores grades.
    static Statement m_Statement; // The SQL statement that will be executed.
	private final String m_FallbackQuery = ""; // By default nothing will be done to the database.

	/**
	 * Constructor for {@link Grades} class.
	 * @implSpec By default, the constructor does not initialize {@link #m_Connection}.
	 */
	public Grades() { super(); }


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
		m_Connection = initialiseConnection();
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
	 	m_Connection = initialiseConnection();
	 	String sqlQuery = m_FallbackQuery;

	 	// Verify that the given arguments are acceptable.
		if(!newModuleName.isEmpty() && !moduleID.isEmpty())
		{
			// SQL statement that updates data in the database.
			sqlQuery = "update  grades set Modulename ='" + newModuleName +
					"' where ModuleID = '" +moduleID+"'";
		}

	 	try
		{
			m_Statement = m_Connection.createStatement(); // Create Statement object to execute static SQL statements.
			m_Statement.executeUpdate(sqlQuery);// SQL statement that performs the update operation and returns the number of updates.
			m_Connection.close();   // Close the database connection.
		}
	 	catch (SQLException e)
		{
			System.out.println("Grade update failed: " + e.getMessage());
		}
	 }



	/**
	 * @implSpec This method will return a {@link String} containing every element in the "grades" table.
	 * @implSpec By default this method executes the following SQL query: {@code "select * from grades"}.
	 * @return The {@link String} conversion of the {@link ResultSet} which contains all elements in the "grades" table.
	 * Returns {@code null} if there is nothing to select.
	 */
	public String queryGrades()
	 {
		 m_Connection = initialiseConnection();
		String searchResult = null;

		 try
		 {
			 String  sqlQuery = "select * from grades"; // SQL statements that queryGrades data
			 m_Statement = m_Connection.createStatement(); // Create Statement object to execute static SQL.
			 ResultSet resultSet = m_Statement.executeQuery(sqlQuery); // Execute the SQL queryGrades & return result.

			 //DEBUG: Used to validate that the search returned the correct elements;
			 //System.out.println("search result：");

			 // Iterate through the database table and collect all rows.
			 while (resultSet.next())
			 {
			 	String SI = resultSet.getString("StudentID");
				String Sn = resultSet.getString("Studentname");
				String MI2 = resultSet.getString("ModuleID");
				String Mn2 = resultSet.getString("Modulename");
				String Grade = resultSet.getString("Grade");
				String Credit = resultSet.getString("Credit");

				searchResult = SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit;

				//DEBUG: Used to verify that the selected row is correct.
				//System.out.println(searchresult);
			 }

			 m_Connection.close(); // Close the database connection.

			 return searchResult;
		 }
		 catch (SQLException e)
		 {
			System.out.println("Querying all fields from Grades has failed: " + e.getMessage());
		 }
		 return null;
	 }


   /**
	* @implSpec By default this method will not do anything if the constraints of the function argument is violated.
	* @param moduleID The ID for the module which will have all the student's grades removed.
	*                    Constraint: Can't be an empty {@link String}.
	*/
	public void deleteGradesForModule(String moduleID)
	{
		String sqlQuery = m_FallbackQuery;

		if(!moduleID.isEmpty())
		{
			sqlQuery = "delete from grades  where ModuleID = '" + moduleID + "'"; // SQL statement to delete all grades.
		}

		try
		{
			m_Statement = m_Connection.createStatement(); // Create Statement object to execute a static SQL statement.
			m_Statement.executeUpdate(sqlQuery); // Execute the SQL delete statement to return the number of deleted data.
			m_Connection.close(); // Close the database connection
		}
		catch (SQLException e)
		{
			System.out.println("Deleting Grades from " + moduleID + " has failed: " + e.getMessage());
		}
	}
}
