/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Model.Student;
import Model.StudentClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class StudentDatabase {

    ConnectToDB connectToDB = new ConnectToDB();
    String dbURL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=STUDENT_MANAGEMENT";
    String user = "sa";
    String passWord = "12345";

    public boolean checkExistStudentbyStudentID(int studentID) {
        String query = "SELECT * FROM Student WHERE [StudentID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, studentID);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistStudent(String identification) {

        String query = "SELECT * FROM Student WHERE [CitizenIdentification] = ? AND [Status] = 'Active'";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, identification);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Student findStudentbyStudentID(int studentID) {
        String query = "SELECT * FROM Student WHERE [StudentID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, studentID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                                rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public StudentClass findClassByClassID(int classID) {
        String query = "SELECT * FROM [dbo].[Class]"
                + "WHERE [ClassID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, classID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new StudentClass(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int findclassByNameAndSchoolYear(String claName, String schoolYear) {
        String query = "SELECT * FROM [dbo].[Class] AS C"
                + "WHERE C.ClassName = ? AND C.SchoolYear = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, claName);
                ps.setString(2, schoolYear);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public boolean createStudentDatabase(Student student) {
        int checkCreate = 0;
        String query = "INSERT INTO [dbo].[Student] ([Name], [Gender], [ClassID],[Address], [CitizenIdentification], [DateOfBirth], [PhoneNumber], [Email], [Status] ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, student.getName());
                ps.setString(2, student.getGender());
                ps.setInt(3, student.getClassID());
                ps.setString(4, student.getAddress());
                ps.setString(5, student.getIdentification());
                ps.setString(6, student.getDateOfBirth());
                ps.setString(7, student.getPhoneNumber());
                ps.setString(8, student.getEmail());
                ps.setString(9, student.getStatus());
                checkCreate = ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Transaction rolled back due to: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return checkCreate > 0;
    }

    public boolean updateStudentDatabase(Student student) {
        int checkUpdate = 0;
        String query = "UPDATE [dbo].[Student]"
                + "SET [Name] = ?, [Gender] = ?, [ClassID] = ?, [Address] = ?, [CitizenIdentification] = ?, [DateOfBirth] = ?,"
                + "[PhoneNumber] = ?, [Email] = ?"
                + "WHERE [StudentID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, student.getName());
                ps.setString(2, student.getGender());
                ps.setInt(3, student.getClassID());
                ps.setString(4, student.getAddress());
                ps.setString(5, student.getIdentification());
                ps.setString(6, student.getDateOfBirth());
                ps.setString(7, student.getPhoneNumber());
                ps.setString(8, student.getEmail());
                ps.setInt(9, student.getStudentID());
                checkUpdate = ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Transaction rolled back due to: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return checkUpdate > 0;
    }

    public boolean deleteStudentDatabase(int studentID, String reason) {
        int checkDelete = 0;
        String query = "UPDATE [dbo].[Student]"
                + "SET [Status] = 'Inactive'"
                + "WHERE [StudentID] = ?";
        String setSession = "EXEC sys.sp_set_session_context @key = N'DeleteReason', @value = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement ps = connection.prepareStatement(setSession)) {
                    ps.setNString(1, reason);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setInt(1, studentID);
                    checkDelete = ps.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Transaction rolled back due to: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
        return checkDelete > 0;
    }

}
