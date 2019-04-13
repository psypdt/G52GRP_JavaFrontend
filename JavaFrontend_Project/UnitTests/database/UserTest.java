package database;


import static org.junit.Assert.*;
import org.junit.Test;



public class UserTest
{
	@Test
	public void testinsert()
	{
		User user = new User();
		user.insert("Wang", "Y",4315722);
		String testresult = user.query();

		assertEquals("Y Wang 4315722",testresult);
	}


	@Test
	public void testupdate()
	{
		User user = new User();
		user.update("Y", 14375122);
		String testresult = user.query();

		assertEquals("Y Wang 14375122",testresult);
	}


	@Test
	public void testxdelete()
	{
		User user = new User();
		user.delete(14375122);
		String testresult = user.query();

		assertEquals(null,testresult);
	}

}
