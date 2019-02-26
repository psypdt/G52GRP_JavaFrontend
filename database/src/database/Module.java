package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement;    
public class Module {    
    // Create static global variables   
    static Connection conn;    
    
    static Statement st;    
    
    /*public static void main(String[] args) {    
        insert();   //insert data    
        update();   //update data    
        delete();   //delete data 
        query();    //Query records and display    
    }    
    /* Function to get a database connection*/    
    public static Connection getConnection() {    
        Connection con = null;  //Create a Connection object to connect to the database 
        try {    
            Class.forName("com.mysql.cj.jdbc.Driver");// load Mysql driver   
                
            con = DriverManager.getConnection(    
                    "jdbc:mysql://localhost:3306/database", "root", "sh279000");// create database connection  
                
        } catch (Exception e) {    
            System.out.println("connect to the database fail" + e.getMessage());    
        }    
        return con; //Returns the established database connection
    }  
    /* inserts a data record and outputs the number of inserted data records*/    
    public void insert() {    
            
        conn = getConnection(); // The first step is to get a connection, that is, to the database  
    
        try {    
            String sql1 = "INSERT INTO module(ModuleID, Modulename)"    
                    + " VALUES ('G52OSC','Opreating system')";  // SQL statement to insert data
            String sql2 = "INSERT INTO grades(StudentID,Studentname,ModuleID,Modulename,Grade,Credit)" 
            		+ " VALUES ('4315722','Yisong Wang','G52OSC','Opreating system','100','10')";
            st = (Statement) conn.createStatement();    // Create a Statement object for executing static SQL statements   
                
            int count1 = st.executeUpdate(sql1);  // SQL statement that performs the insert operation and returns the number of inserted data    
            int count2 = st.executeUpdate(sql2);
            System.out.println("insert into module " + count1 + " data"); //Outputs the processing results of the insert operation   
            System.out.println("insert into grades " + count2 + " data");     
            conn.close();   //Close the database connection   
                
        } catch (SQLException e) {    
            System.out.println("insert fail" + e.getMessage());    
        }    
    }    
        
    /* Updates the required records and returns the number of updated records*/    
    public void update() {    
        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
        try {    
            String sql = "update module set Modulename ='language and computation' where ModuleID = 'G52LAC'";// SQL statement that updates data   
                
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable  
                
            int count = st.executeUpdate(sql);//SQL statement that performs the update operation and returns the number of updates   
                
            System.out.println("update the module data  " + count + " data");      //Outputs the processing results of the update operation  
                
            conn.close();   //Close the database connection    
                
        } catch (SQLException e) {    
            System.out.println("update fail");    
        }    
    }    
    	
    /* Query the database to output the required records*/    
   public void query() {    
            
        conn = getConnection(); //Again, get the connection first, that is, connect to the database    
        try {    
            String sql1 = "select * from module";// SQL statements that query data   
            /*String sql2 = "select * from grades"; */
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable  
                
            ResultSet rs1 = st.executeQuery(sql1);  //Execute the SQL query statement and return the result set of the query data  
            /*ResultSet rs2 = st.executeQuery(sql2);  */
            System.out.println("search result：");    
           /*while (rs1.next()) { // Determine if there is another piece of data
                    
                // Gets the value based on the field name  
            	String MI1 = rs1.getString("ModuleID");  
                String Mn1 = rs1.getString("Modulename");        
                    
                //Output the values of the fields of the record being looked up 
                System.out.println(MI1+" "+Mn1);    
                
            } */
            while (rs1.next()) {
            	String SI = rs1.getString("StudentID");  
                String Sn = rs1.getString("Studentname"); 
                String MI2 = rs1.getString("ModuleID");  
                String Mn2 = rs1.getString("Modulename"); 
                String Grade = rs1.getString("Grade");  
                String Credit = rs1.getString("Credit"); 
                System.out.println(SI+" "+Sn + " " + MI2+ " "+ Mn2 + " "+ Grade +" "+Credit);  
            }
            conn.close();   //Close the database connection   
                
        } catch (SQLException e) {    
            System.out.println("search information fail");    
        }    
    }    
    
    /* Delete the required records, output*/    
    public void delete() {    
    
        conn = getConnection(); //Again, get the connection first, that is, connect to the database  
        try {    
            String sql = "delete from module  where ModuleID = 'G52ACE'";// SQL statement to delete data    
            st = (Statement) conn.createStatement();    //Create a Statement object for executing static SQL statements, st being a local variable 
                
            int count = st.executeUpdate(sql);// Execute the SQL delete statement to return the number of deleted data  
                
            System.out.println("delete from the module " + count + " data\n");    //Outputs the result of the delete operation  
                
            conn.close();   //Close the database connection    
                
        } catch (SQLException e) {    
            System.out.println("delete fail");    
        }    
            
    }    
}