package login;

import java.sql.*;
import static commons.Constants.*;

/**
 * Created by Iulia-PC on 6/12/2016.
 */
public class LoginDAO {


    public static String validate(String username, String password) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connection con = getConnection();
        String stm = "Select * from users where username='" + username + "'";
        User userDatabase = new User();
        try {
            pst = con.prepareStatement(stm);
            pst.execute();
            rs = pst.getResultSet();

            while (rs.next()) {
                userDatabase.setUsername(rs.getString(1));
                userDatabase.setPassword(rs.getString(2));
                userDatabase.setAdmin(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (username.equals(userDatabase.getUsername()) && password.equals(userDatabase.getPassword())) {
            if (userDatabase.isAdmin().equals("1")) {
                return ADMIN;
            }
            return STUDENT;
        }
        return INVALID;
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getException());
        }

        Connection con = null;

        String url = "jdbc:postgresql://localhost/users";
        String user = "postgres";
        String password = "postgres";
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection completed.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
        }
        return con;
    }
}
