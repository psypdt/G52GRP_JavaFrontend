package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement;    
public class Connection_test {    
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
                
            int count1 = st.executeUpdate(sql1);  // 执行插入操作的sql语句，并返回插入数据的个数    
            int count2 = st.executeUpdate(sql2);
            System.out.println("insert into module " + count1 + " data"); //输出插入操作的处理结果    
            System.out.println("insert into grades " + count2 + " data");     
            conn.close();   //关闭数据库连接    
                
        } catch (SQLException e) {    
            System.out.println("insert fail" + e.getMessage());    
        }    
    }    
        
    /* 更新符合要求的记录，并返回更新的记录数目*/    
    public void update() {    
        conn = getConnection(); //同样先要获取连接，即连接到数据库    
        try {    
            String sql = "update module set Modulename ='language and computation' where ModuleID = 'G52LAC'";// 更新数据的sql语句    
                
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量    
                
            int count = st.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数    
                
            System.out.println("update the module data  " + count + " data");      //输出更新操作的处理结果    
                
            conn.close();   //关闭数据库连接    
                
        } catch (SQLException e) {    
            System.out.println("update fail");    
        }    
    }    
    	
    /* 查询数据库，输出符合要求的记录的情况*/    
   public void query() {    
            
        conn = getConnection(); //同样先要获取连接，即连接到数据库    
        try {    
            String sql1 = "select * from module";// 查询数据的sql语句    
            /*String sql2 = "select * from grades"; */
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量    
                
            ResultSet rs1 = st.executeQuery(sql1);  //执行sql查询语句，返回查询数据的结果集   
            /*ResultSet rs2 = st.executeQuery(sql2);  */
            System.out.println("search result：");    
           /*while (rs1.next()) { // 判断是否还有下一个数据    
                    
                // 根据字段名获取相应的值    
            	String MI1 = rs1.getString("ModuleID");  
                String Mn1 = rs1.getString("Modulename");        
                    
                //输出查到的记录的各个字段的值    
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
            conn.close();   //关闭数据库连接    
                
        } catch (SQLException e) {    
            System.out.println("search information fail");    
        }    
    }    
    
    /* 删除符合要求的记录，输出情况*/    
    public void delete() {    
    
        conn = getConnection(); //同样先要获取连接，即连接到数据库    
        try {    
            String sql = "delete from module  where ModuleID = 'G52ACE'";// 删除数据的sql语句    
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量    
                
            int count = st.executeUpdate(sql);// 执行sql删除语句，返回删除数据的数量    
                
            System.out.println("delete from the module " + count + " data\n");    //输出删除操作的处理结果    
                
            conn.close();   //关闭数据库连接    
                
        } catch (SQLException e) {    
            System.out.println("delete fail");    
        }    
            
    }    
}