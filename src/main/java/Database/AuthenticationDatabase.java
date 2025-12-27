/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Authentication.CommonFunctionAuthentication;
import Model.UserAccount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Nguyen Huu Thanh
 */
public class AuthenticationDatabase {

    CommonFunctionAuthentication comFunAuthen = new CommonFunctionAuthentication();
    ConnectToDB connectToDB = new ConnectToDB();
    String dbURL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=STUDENT_MANAGEMENT";
    String user = "sa";
    String passWord = "12345";

    public boolean checkLogin(String emailLogin, String passwordLogin) {
        String query = "SELECT password "
                + "FROM Registration "
                + "WHERE email = ? ";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, emailLogin);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String hashedPassword = rs.getString("password");
                        return comFunAuthen.checkHashPassword(passwordLogin, hashedPassword);
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
        return false;
    }

    public UserAccount getUserAccount(String email) {
        String query = "SELECT fullName, password, roleName "
                + "FROM Role AS RO "
                + "INNER JOIN Registration AS RE "
                + "ON RE.roleId = RO.roleId "
                + "WHERE RE.email = ? ";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new UserAccount(rs.getString("fullName"), email, rs.getString("password"), rs.getString("roleName"));
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

    public boolean createUserAccount(String fullName, String email, String password) {
        int checkCreate = 0;
        String query = "INSERT INTO Registration (fullName, email, password) "
                + "VALUES (?, ?, ?)";
        try (Connection connection = connectToDB.connectToDB(dbURL, user, passWord)) {
            if (connection == null) {
                throw new SQLException("Failed to establish a database connection");
            }
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, fullName);
                ps.setString(2, email);
                ps.setString(3, password);
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
}
