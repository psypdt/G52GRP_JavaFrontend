package database;

import static org.junit.Assert.*;
import org.junit.Test;


public class CreatetableTest
{
	@Test
	public void test_create_new_table()
    {
		CreateTable create = new CreateTable();
		String testresult = create.createTable("new","UserID","Username");
		assertEquals("new", testresult);
	}
}
