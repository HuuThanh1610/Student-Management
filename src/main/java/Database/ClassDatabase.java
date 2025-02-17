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

/**
 *
 * @author Nguyen Huu Thanh
 */
public class ClassDatabase {

    ConnectToDB connectToDB = new ConnectToDB();
    String dbURL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=STUDENT_MANAGEMENT";
    String user = "sa";
    String passWord = "12345";

    public StudentClass findclassByNameAndSchoolYear(String claName, String schoolYear) {
        String query = "SELECT * FROM Class WHERE ClassName = ? AND SchoolYear = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, claName);
                ps.setString(2, schoolYear);
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

    public boolean createClassDatabase(StudentClass stuClass) {
        int checkCreate = 0;
        String query = "INSERT INTO [dbo].[Class] ([ClassName], [SchoolYear], [HomeroomTeacherID])"
                + "VALUES (?, ?, ?)";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, stuClass.getClassName());
                ps.setString(2, stuClass.getSchoolyear());
                ps.setInt(3, stuClass.getTeacherID());
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

    public boolean updateClassDatabase(StudentClass stuClass) {
        int checkUpdate = 0;
        String query = "UPDATE [dbo].[Class]"
                + "SET [ClassName]= ?, [SchoolYear] = ?, [HomeroomTeacherID] = ?"
                + "WHERE [ClassID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, stuClass.getClassName());
                ps.setString(2, stuClass.getSchoolyear());
                ps.setInt(3, stuClass.getTeacherID());
                 ps.setInt(4, stuClass.getClassNameID());
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
    
        public boolean deleteClassDatabase(int classID) {
        int checkDelete = 0;
        String query = "DELETE FROM [dbo].[Class] WHERE [ClassID]= ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, classID);
                checkDelete = ps.executeUpdate();
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
        return checkDelete > 0;
    }

    public boolean checkExistClassbyClassID(int classID) {
        String query = "SELECT * FROM [dbo].[Class]"
                + "WHERE [ClassID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, classID);
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
}
