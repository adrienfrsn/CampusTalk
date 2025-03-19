package modele;

import java.sql.Connection;
import java.sql.DriverManager;

public class DS {
    public static DS instance = new DS();

    private DS() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String pwd = "";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, login, pwd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}