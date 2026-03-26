package database;
import java.sql.*;

public class database {
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "root123*";
    public void create() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create database if not exists student_db;");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createStudentTable() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists student "+
                                "(RegisterNo varchar(9) PRIMARY KEY, Name varchar(50), Department varchar(50));");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void createStudentMarkTable() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS marks (" +
                                    "RegisterNo VARCHAR(9) PRIMARY KEY, " +
                                    "mark1 DOUBLE, mark2 DOUBLE, mark3 DOUBLE, " +
                                    "cgpa VARCHAR(10) AS (" +
                                        "CASE " +
                                            "WHEN mark1 < 40 OR mark2 < 40 OR mark3 < 40 THEN 'FAIL' " +
                                            "ELSE CAST(ROUND((mark1 + mark2 + mark3) / 30, 2) AS CHAR) " +
                                        "END" +
                                    ") STORED" +
                                ");");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public int studentInsertion(String RegisterNo, String Name, String Department) {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("insert into student (RegisterNo, Name, Department) "+ 
                                                "values ('%s','%s','%s');",RegisterNo.toUpperCase(),Name.toUpperCase(),Department.toUpperCase()));
            return 0;
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            return 2;
        }
    }
    public int studentMarkInsertion(String RegisterNo, double mark1, double mark2, double mark3) {
        int rowsAffected;
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(String.format("insert into marks (RegisterNo, mark1, mark2, mark3) "+ 
                                                                "select '%s',%.1f,%.1f,%.1f "+ 
                                                                "where exists "+
                                                                "(select 1 from student where RegisterNo='%s');"
                                                                ,RegisterNo.toUpperCase(),mark1,mark2,mark3,RegisterNo.toUpperCase()));
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
        if (rowsAffected == 0) {
            // System.out.println(RegisterNo + " there is no student with this register number.");
            return 3;
        }
        return 0;
    }
    public int studentMarkUpdation(String RegisterNo, double mark1, double mark2, double mark3) {
        int rowsAffected;
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(String.format("update marks set mark1=%.1f, mark2=%.1f, mark3=%.1f where RegisterNo='%s';",
                                                        mark1,mark2,mark3,RegisterNo.toUpperCase()));
        } catch (Exception e) {
            return 1;
        }
        if (rowsAffected == 0) {
            // System.out.println(RegisterNo + " there is no student with this register number.");
            return 2;
        }
        return 0;
    }
    public int studentDeletion(String RegisterNo) {
        int rowsAffected;
        try {
            Connection conn = DriverManager.getConnection(url+"student_db",user,password);
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(String.format("delete s, m from student s "+
                                                "left join marks m on s.RegisterNo = m.RegisterNo "+
                                                "where s.RegisterNo = '%s';",RegisterNo));
            if(rowsAffected == 0) {
                return 2;
            }
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 1;
        }
    }
    public ResultSet studentDisplay() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db",user,password);
            String query = "select * from student";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public ResultSet studentMarkDisplay() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db",user,password);
            String query = "select s.RegisterNo, s.Name, s.Department, m.mark1, m.mark2, m.mark3, m.cgpa "+
                                "from student s "+
                                "left join marks m on s.RegisterNo = m.RegisterNo;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
