import java.util.Scanner;
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
            stmt.executeUpdate("create table if not exists student "+
                                "(RegisterNo varchar(9) PRIMARY KEY, Name varchar(50), Department varchar(50));");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void createStudentMarkTable() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists marks "+
                                "(RegisterNo varchar(9) PRIMARY KEY, mark1 double, mark2 double, mark3 double);");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void studentInsertion(String RegisterNo, String Name, String Department) {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(String.format("insert into student (RegisterNo, Name, Department) "+ 
                                                "values ('%s','%s','%s');",RegisterNo,Name,Department));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void studentMarkInsertion(String RegisterNo, double mark1, double mark2, double mark3) {
        int rowsAffected;
        try {
            Connection conn = DriverManager.getConnection(url+"student_db", user, password);
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(String.format("insert into marks (RegisterNo, mark1, mark2, mark3) "+ 
                                                                "select '%s',%.1f,%.1f,%.1f "+ 
                                                                "where exists (select 1 from student where RegisterNo='%s');"
                                                                ,RegisterNo,mark1,mark2,mark3,RegisterNo));
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println("The Marks of " + RegisterNo + " is already entered.");
            return;
        } catch (Exception e) {
            return;
        }
        if (rowsAffected == 0) {
            System.out.println(RegisterNo + " there is no student with this register number.");
        }
    }
    void studentDisplay() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db",user,password);
            String query = "select * from student";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("=".repeat(51));
            System.out.printf("| %-11s | %-20s | %-10s |","RegisterNo", "Name", "Department");
            System.out.println();
            System.out.println("=".repeat(51));
            while (rs.next()) {
                String RegisterNo = rs.getString("RegisterNo");
                String Name = rs.getString("Name");
                String Department = rs.getString("Department");

                System.out.printf("| %-11s | %-20s | %-10s |",RegisterNo,Name,Department);
                System.out.println();
            }
            System.out.println("=".repeat(51));
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
    void studentMarkDisplay() {
        try {
            Connection conn = DriverManager.getConnection(url+"student_db",user,password);
            String query = "select s.RegisterNo, s.Name, s.Department, m.mark1, m.mark2, m.mark3 "+
                                "from student s "+
                                "left join marks m on s.RegisterNo = m.RegisterNo;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("=".repeat(75));
            System.out.printf("| %-11s | %-20s | %-10s | %-5s | %-5s | %-5s |",
                                "RegisterNo","Name","Department","mark1","mark2","mark3");
            System.out.println();
            System.out.println("=".repeat(75));
            while (rs.next()) {
                String RegisterNo = rs.getString("RegisterNo");
                String Name = rs.getString("Name");
                String Department = rs.getString("Department");
                Object mark1 = rs.getObject("mark1");
                Object mark2 = rs.getObject("mark2");
                Object mark3 = rs.getObject("mark3");

                System.out.printf("| %-11s | %-20s | %-10s | %-5s | %-5s | %-5s |",
                                    RegisterNo,Name,Department,mark1,mark2,mark3);
                System.out.println();
            }
            System.out.println("=".repeat(75));
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
}

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        database db = new database();
        db.create();
        db.createStudentTable();
        db.createStudentMarkTable();
        int loop = 1;
        while (loop == 1) {
            System.out.print("1.Enter a student\n2.Enter student marks\n"+
                                "3.Display of students\n4.Display of marks\n"+
                                "5.Exit\nEnter choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Register Number: ");
                    String RegisterNo = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String Name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String Department = sc.nextLine();
                    db.studentInsertion(RegisterNo, Name, Department);
                    break;
                case 2:
                    System.out.print("Enter Register Number: ");
                    RegisterNo = sc.nextLine();
                    System.out.print("Enter Mark1: ");
                    double mark1 = sc.nextDouble();
                    System.out.print("Enter Mark2: ");
                    double mark2 = sc.nextDouble();
                    System.out.print("Enter Mark3: ");
                    double mark3 = sc.nextDouble();
                    db.studentMarkInsertion(RegisterNo, mark1, mark2, mark3);
                    break;
                case 3:
                    db.studentDisplay();
                    break;
                case 4:
                    db.studentMarkDisplay();
                    break;
                case 5:
                    loop = 0;
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }
}
