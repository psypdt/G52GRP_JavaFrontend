package database;
import java.sql.Connection;    
import java.sql.DriverManager;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
import java.sql.Statement;    
    
public class Connection_test {    
    // 创建静态全局变量    
    static Connection conn;    
    
    static Statement st;    
    
    public static void main(String[] args) {    

        Connection con = null;  //创建用于连接数据库的Connection对象    
        try {    
            Class.forName("com.mysql.cj.jdbc.Driver");// 加载Mysql数据驱动    
                
            con = DriverManager.getConnection(    
                    "jdbc:mysql://localhost:3306/database", "root", "sh279000");// 创建数据连接    

            System.out.println("数据库连接成功"); 
                
        } catch (Exception e) {    
            System.out.println("数据库连接失败" + e.getMessage());    
        }    
        return; //返回所建立的数据库连接   
    }    
}
