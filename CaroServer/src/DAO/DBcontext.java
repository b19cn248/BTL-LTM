package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hieu
 */
public class DBcontext {

    protected Connection connection;

    public DBcontext() {
        try {
            String dbUrl = "jdbc:mysql://localhost:3306/carodb";
            String username = "root";
            String password = "admin1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

}
