package database;

import static org.junit.Assert.*;
import org.junit.Test;



public class GradesTest
{
	@Test
	public void test_grade_insertion()
	{
		Grades grades = new Grades();
		grades.insert(4315722,"Y","G52DSY","Distributed system",99,10);
		String testresult = grades.query();
		
		/*String Sn = null, MI2 = null, Mn2 = null, Grade = null, Credit = null;
		int SI = 0;
		try {
			while (result.next()) {
	        	SI = result.getInt("StudentID");  
	            Sn = result.getString("Studentname"); 
	            MI2 = result.getString("ModuleID");  
	            Mn2 = result.getString("Modulename"); 
	            Grade = result.getString("Grade");  
	            Credit = result.getString("Credit"); 
	            System.out.println("TEST:"+SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit);
			}
		} catch (SQLException e) {
			//System.out.println("search information fail");
			 System.out.println("TEST:"+SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit);
			e.printStackTrace();
		}*/
		assertEquals("4315722 Y G52DSY Distributed system 99 10", testresult);
		/*assertEquals("Y", Sn);
		assertEquals("G52DSY",MI2);
		assertEquals("Distributed system",Mn2);
		assertEquals("99",Grade);
		assertEquals("10",Credit);*/
	}

	@Test
	public void test_update() {
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
	
		
		
}