/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Grade;
import Model.Semester;
import Model.Student;
import Model.StudentClass;
import Model.Subject;
import Model.Teacher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class ConnectToDB {

    String dbURL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=STUDENT_MANAGEMENT";
    String user = "sa";
    String passWord = "12345";

    public Connection connectToDB(String dbURL, String user, String passWord) throws SQLException {
        Connection connection = null;
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            connection = DriverManager.getConnection(dbURL, user, passWord);
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public List<Student> getListStudentFromDB() {
        List<Student> lstStudent = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Student]");
                while (rs.next()) {
                    lstStudent.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstStudent;
    }

    public List<StudentClass> getListClassFromDB() {
        List<StudentClass> lstClass = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Class]");
                while (rs.next()) {
                    lstClass.add(new StudentClass(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstClass;
    }

    public List<Teacher> getListTeacherFromDB() {
        List<Teacher> lstTeacher = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Teacher");
                while (rs.next()) {
                    lstTeacher.add(new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstTeacher;
    }

    public List<Grade> getListGradeFromDB() {
        List<Grade> lstGrade = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Mark]");
                while (rs.next()) {
                    lstGrade.add(new Grade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstGrade;
    }

    public List<Subject> getListSubjectFromDB() {
        List<Subject> lstSubject = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Subject]");
                while (rs.next()) {
                    lstSubject.add(new Subject(rs.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstSubject;
    }
    
    public List<Semester> getListSemesterFromDB() {
        List<Semester> lstSemester = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM [dbo].[Semester]");
                while (rs.next()) {
                    lstSemester.add(new Semester(rs.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstSemester;
    }
    
    public List<String> getClassNameForDropDownList() {
        List<String> lstClassName = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT ClassName FROM Class");
                while (rs.next()) {
                    lstClassName.add(rs.getString("ClassName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstClassName;
    }
    
    public List<String> getSchoolYearForDropDownList() {
        List<String> lstSchoolYear = new ArrayList<>();
        try {
            Connection connection = connectToDB(dbURL, user, passWord);
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT SchoolYear FROM Class");
                while (rs.next()) {
                    lstSchoolYear.add(rs.getString("SchoolYear"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return lstSchoolYear;
    }
}
