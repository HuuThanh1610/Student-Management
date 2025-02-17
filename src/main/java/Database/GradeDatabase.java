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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class GradeDatabase {

    ConnectToDB connectToDB = new ConnectToDB();
    String dbURL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=STUDENT_MANAGEMENT";
    String user = "sa";
    String passWord = "12345";

    public StudentClass findClassByStudentID(int studentID) {
        String query = "SELECT C.[ClassID], [ClassName], [SchoolYear], [HomeroomTeacherID]"
                + "FROM [dbo].[Class] AS C"
                + "INNER JOIN [dbo].[Student] AS T"
                + "ON C.ClassID = T.ClassID"
                + "WHERE T.StudentID = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, studentID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new StudentClass(rs.getInt("ClassID"), rs.getString("ClassName"), rs.getString("SchoolYear"), rs.getInt("HomeroomTeacherID"));
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

    public Subject findSubjectBySubjectID(int subjectID) {
        String query = "SELECT * FROM [dbo].[Subject] WHERE [SubjectID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, subjectID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Subject(rs.getInt(1), rs.getString(2));
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

    public Semester findSemesterBySemesterID(int semesterID) {
        String query = "SELECT * FROM [dbo].[Semester] "
                + "WHERE [SemesterID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, semesterID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Semester(rs.getInt(1), rs.getString(2));
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
    
    public Grade findGradeByMarkID(int markID) {
        String query = "SELECT * FROM [dbo].[Mark] WHERE [MarkID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, markID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Grade(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getDouble(5), rs.getString(6), rs.getString(7));
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

    public boolean checkExistGrade(int studentID, int subjectID, int semester) {
        String query = "SELECT * FROM [dbo].[Mark]"
                + "WHERE [StudentID] = ? AND [SubjectID] = ? AND [SemesterID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, studentID);
                ps.setInt(2, subjectID);
                ps.setInt(3, semester);
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

    public boolean checkExistGradeByMarkID(int markID) {
        String query = "SELECT * FROM [dbo].[Mark]"
                + "WHERE [MarkID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, markID);
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

    public boolean createGradeDatabase(Grade grade) {
        int checkCreate = 0;
        String query = "INSERT INTO [dbo].[Mark] ([StudentID], [SubjectID], [SemesterID], [Score])"
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, grade.getStudentID());
                ps.setInt(2, grade.getSubjectID());
                ps.setInt(3, grade.getSemesterID());
                ps.setDouble(4, grade.getMarkOfSubject());
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

    public boolean updateGradeDatabase(int markID, double score, String reason) {
        int checkUpdate = 0;
        String query = "UPDATE [dbo].[Mark]"
                + "SET [Score] = ?"
                + "WHERE [MarkID] = ?";
        String setSession = "EXEC sys.sp_set_session_context @key = N'UpdateReason', @value = ?";
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
                try (PreparedStatement psSetUpdate = connection.prepareStatement(query)) {
                    psSetUpdate.setDouble(1, score);
                    psSetUpdate.setInt(2, markID);
                    checkUpdate = psSetUpdate.executeUpdate();
                }
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
    
    public boolean deleteGradeDatabase(int markID) {
        int checkDelete = 0;
        String query = "DELETE FROM [dbo].[Mark] WHERE [MarkID] = ?";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, markID);
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
}
