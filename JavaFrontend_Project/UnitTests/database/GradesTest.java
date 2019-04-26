package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.sql.SQLException;


public class GradesTest
{
	@Test
	public void test_grade_insertion()
	{
		Grades grades = new Grades();
		grades.insert(4315722,"Y","G52DSY","Distributed system",99,10);
		String testresult = grades.query();

		assertEquals("4315722 Y G52DSY Distributed system 99 10", testresult);
	}


	@Test
	public void test_update()
	{
		Grades grade = new Grades();
		grade.update("Introduction to Distributed system", "G52DSY");
		String testresult = grade.query();

		assertEquals("4315722 Y G52DSY Introduction to Distributed system 99 10",testresult);
	}


	@Test
	public void test_delete_dsy()
	{
		Grades grade = new Grades();
		grade.delete("G52DSY");
		String testresult = grade.query();

		assertEquals(null, testresult);
	}


	@Test
	public void test_delete_grades()
	{
		boolean didFail = false;
		Grades gradeTable = new Grades();

		try
		{
			gradeTable.dropTable("grades");
		}
		catch (SQLException e)
		{
			didFail = true;
		}

		assertFalse(didFail);
	}




	@Test
	public void test_table_creation()
	{
		boolean didFail = false;
		Grades gradeTable = new Grades();

		try
		{
			gradeTable.createTable();
		}
		catch (SQLException e)
		{
			didFail = true;
		}

		assertFalse(didFail);
	}
}