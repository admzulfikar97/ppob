package com.adamzulfikar.ppob.transaction.repository;

import com.adamzulfikar.ppob.transaction.model.Services;
import com.adamzulfikar.ppob.transaction.model.Transaction;
import com.adamzulfikar.ppob.user.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepository {
    private final DataSource ds;
    public TransactionRepository(DataSource dataSource) {
        this.ds = dataSource;
    }

    public Transaction findByUserId(Long id) {
        String sql = "SELECT user_id, invoice_number, service_code, service_name, transaction_type, total_amount, created_at" +
                " FROM transaction WHERE id = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Transaction(
                            rs.getLong("user_id"),
                            rs.getString("invoice_number"),
                            rs.getString("service_code"),
                            rs.getString("service_name"),
                            rs.getString("transaction_type"),
                            rs.getLong("total_amount"),
                            rs.getString("created_at")
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
    public Services findByServiceCode(String serviceCode) {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM service WHERE service_code = ?";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serviceCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Services(
                            rs.getString("service_code"),
                            rs.getString("service_name"),
                            rs.getString("service_icon"),
                            rs.getLong("service_tariff")
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
    public Long createTransaction(Services services, String invoiceNumber, User user) {
        String sql = "INSERT INTO transaction (user_id, invoice_number, service_code, service_name, transaction_type, total_amount)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, user.getId());
            ps.setString(2, invoiceNumber);
            ps.setString(3, services.getService_code());
            ps.setString(4, services.getService_name());
            ps.setString(5, "PAYMENT");
            ps.setLong(6, services.getService_tariff());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }
        return null;
    }

    public List<Transaction> listTransactionByUserId(Long userId, int offset, int limit) {
        String sql = "SELECT user_id, invoice_number, service_code, service_name, transaction_type, total_amount, created_at " +
                "FROM transaction WHERE user_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?";

        List<Transaction> transactionList = new ArrayList<>();
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setLong(2, limit);
            ps.setLong(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                            rs.getLong("user_id"),
                            rs.getString("invoice_number"),
                            rs.getString("service_code"),
                            rs.getString("service_name"),
                            rs.getString("transaction_type"),
                            rs.getLong("total_amount"),
                            rs.getString("created_at")
                    );
                    transactionList.add(transaction);
                }
            }catch (SQLException ex) {
                throw new RuntimeException("Database error: " + ex.getMessage());
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage());
        }

        return transactionList;
    }
}