import java.sql.*;

class database {
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "root123*";
    void create() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create database if not exists student_db;");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void createStudentTable() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists student (RegisterNo varchar(9) PRIMARY KEY, Name varchar(50), Department varchar(50));");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void studentInsertion(String RegisterNO, String Name, String Department) {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create database if not exists student_db");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
