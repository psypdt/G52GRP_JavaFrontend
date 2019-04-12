package database;


import static org.junit.Assert.*;

import org.junit.Test;


public class ModuleTest
{

	@Test
	public void testinsert() {
		Module module = new Module();
		module.insert("G52LAC", "Language and Computation");
		String testresult = module.query();
		assertEquals("G52LAC Language and Computation",testresult);
	}
	@Test
	public void testupdate() {
		Module module = new Module();
		module.update("Languages and Computations", "G52LAC");
		String testresult = module.query();
		assertEquals("G52LAC Languages and Computations",testresult);
	}
	@Test
	public void testxdelete() {
		Module module = new Module();
		module.delete("G52LAC");
		String testresult = module.query();
		assertEquals(null,testresult);
		
	}	
}
