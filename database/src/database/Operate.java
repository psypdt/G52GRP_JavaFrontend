package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement; 
public class Operate {
	static String name = "Yisong Wang";
	static Module module = new Module();
	static User user = new User();
	static Grades grades = new Grades();
	static CreateTable table = new CreateTable();
	public static void main(String[] args) {
		module.insert();   //insert data    
        module.update();   //update data    
        module.delete();   //delete data 
        module.query();//Query records and display 
        
        /*user.insert();
        user.update();
        user.delete();
        user.query();*/
        
        grades.insert(name);
        grades.update();
        grades.delete();
        grades.query();
        
        table.createtable();
	}

}