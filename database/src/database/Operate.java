package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement; 
public class Operate {
	static String studentname = "Yisong Wang";
	static int studentID = 4315722;
	static int grade = 100;
	static int credit = 10;
	static String moduleID="G52OSC";
	static String modulename="Operating system";
	static String newmodulename = "Operating sysetem and concurrency";
	static String tablename = "new";
	static Module module = new Module();
	static User user = new User();
	static Grades grades = new Grades();
	static CreateTable table = new CreateTable();
	public static void main(String[] args) {
		module.insert(moduleID,modulename);   //insert data    
        module.update(newmodulename,moduleID);   //update data    
        module.delete(moduleID);   //delete data 
        module.query();//Query records and display 
        
        /*user.insert();
        user.update();
        user.delete();
        user.query();*/
        
        grades.insert(studentname,studentID, moduleID,modulename,grade,credit);
        grades.update(newmodulename,moduleID);
        grades.delete(moduleID);
        grades.query();
        
        table.createtable(tablename);
	}

}