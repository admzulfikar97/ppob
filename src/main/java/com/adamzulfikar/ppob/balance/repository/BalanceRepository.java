package com.adamzulfikar.ppob.balance.repository;

import com.adamzulfikar.ppob.balance.model.Balance;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class BalanceRepository {
    private final DataSource ds;
    public BalanceRepository(DataSource dataSource) {
        this.ds = dataSource;
    }

    public Balance findByUserId(Long userId) {
        String sql = "SELECT balance FROM user_balance WHERE user_id = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Balance(
                            rs.getLong("balance")
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
    public Long updateBalance(Long topUpAmount, String email) {
        String sql = "UPDATE user_balance SET balance = ? FROM users WHERE users.id = user_balance.user_id AND users.email= ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, topUpAmount);
            ps.setString(2, email);
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
}