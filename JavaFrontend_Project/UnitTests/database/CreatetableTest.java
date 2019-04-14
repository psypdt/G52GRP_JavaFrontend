package database;

import static org.junit.Assert.*;
import org.junit.Test;

import java.sql.SQLException;


public class CreatetableTest
{
	@Test
	public void test_create_new_table()
    {
		CreateTable create = new CreateTable();
		String testresult = create.createTable("new","UserID","Username");
		assertEquals("new", testresult);
	}


	@Test
	public void test_drop_table()
	{
		boolean didFail = false;
		CreateTable createTable = new CreateTable();

		createTable.createTable("new","UserID","Username");

		try
		{
			createTable.dropTable("new");
		}
		catch (SQLException e)
		{
			didFail = true;
		}

		assertFalse(didFail);
	}
}
