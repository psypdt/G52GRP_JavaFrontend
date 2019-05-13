package database;


import static org.junit.Assert.*;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.Test;

import java.sql.SQLException;


public class ModuleTest
{

	@Test
	public void test_insert()
	{
		Module module = new Module();
		module.insert("G52LAC", "Language and Computation");
		String testresult = module.query();

		assertEquals("G52LAC Language and Computation",testresult);
	}


	@Test
	public void test_update()
	{
		Module module = new Module();
		module.update("Languages and Computations", "G52LAC");
		String testresult = module.query();

		assertEquals("G52LAC Languages and Computations",testresult);
	}


	@Test
	public void test_delete()
	{
		Module module = new Module();
		module.delete("G52LAC");
		String testresult = module.query();

		assertEquals(null,testresult);
	}



	@Test
	public void test_creation()
	{
		boolean didFail = false;
		Module moduleTable = new Module();

		try
		{
			moduleTable.createTabel();
		}
		catch (SQLException ex)
		{
			didFail = true;
		}

		assertFalse(didFail);
	}
}
