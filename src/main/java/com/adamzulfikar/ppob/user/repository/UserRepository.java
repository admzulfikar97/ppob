package com.adamzulfikar.ppob.user.repository;

import com.adamzulfikar.ppob.common.exception.BadRequestException;
import com.adamzulfikar.ppob.user.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepository {
    private final DataSource ds;
    public UserRepository(DataSource ds) {
        this.ds = ds;
    }
    public Long createUser(String firstname, String lastname, String email, String password) {
        String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }
        return null;
    }
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(1) FROM users WHERE email = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }catch (SQLException ex) {
//                throw new RuntimeException("Database error: " + ex.getMessage(), ex);
                throw new RuntimeException("Database error: " + ex.getMessage());

            }
        }catch (SQLException ex) {
//            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
            throw new RuntimeException("Database error: " + ex.getMessage());
        }
    }
    public User findByEmail(String email) {
        String sql = "SELECT id, first_name as firstname, last_name as lastname, email, password, filename, filepath, created_at FROM users WHERE email = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("filename"),
                            rs.getString("filepath"),
                            rs.getTimestamp("created_at").toLocalDateTime().toString()
                    );
                }else {
                    return null;
                }
            }catch (SQLException ex) {
                throw new RuntimeException("Database error: " + ex.getMessage());
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage());
        }
    }

    public Long updateUser(String firstname, String lastname, String email) {
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE email= ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }catch (SQLException ex) {
                throw new RuntimeException("Database error: " + ex.getMessage(), ex);
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }
        return null;
    }

    public Long saveImage(String email, String filename, String filepath) {
        String sql = "UPDATE users SET filename = ?, filepath = ? WHERE email = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, filename);
            ps.setString(2, filepath);
            ps.setString(3, email);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }
        return null;
    }

}
