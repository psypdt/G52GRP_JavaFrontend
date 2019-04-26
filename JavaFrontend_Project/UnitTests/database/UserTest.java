package database;


import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.SQLException;


public class UserTest
{
	@Test
	public void test_insert()
	{
		User user = new User();
		user.insert("Wang", "Y",4315722);
		String testresult = user.query();

		assertEquals("Y Wang 4315722",testresult);
	}


	@Test
	public void test_update()
	{
		User user = new User();
		user.update("Y", 14375122);
		String testresult = user.query();

		assertEquals("Y Wang 14375122",testresult);
	}


	@Test
	public void test_delete()
	{
		User user = new User();
		user.delete(14375122);
		String testresult = user.query();

		assertEquals(null,testresult);
	}


	/**
	 * @implNote This test is expected to fail if the table already exists within the database.
	 */
	@Test
	public void test_create()
	{
		boolean didFail = false;

		User userTable = new User();

		try
		{
			userTable.createTable();
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage());
			didFail = true;
		}

		assertFalse(didFail);
	}
}
