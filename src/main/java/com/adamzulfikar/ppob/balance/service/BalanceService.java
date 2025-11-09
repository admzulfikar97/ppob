package com.adamzulfikar.ppob.balance.service;

import com.adamzulfikar.ppob.balance.dto.request.TopUpRequest;
import com.adamzulfikar.ppob.balance.model.Balance;
import com.adamzulfikar.ppob.balance.repository.BalanceRepository;
import com.adamzulfikar.ppob.common.exception.BadRequestException;
import com.adamzulfikar.ppob.common.exception.NotFoundException;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class BalanceService {
    private final DataSource ds;
    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;
    public BalanceService(DataSource ds, BalanceRepository balanceRepository, UserRepository userRepository) {
        this.ds = ds;
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }
    public Long createBalance(Long userId, Long balance) {
        String sql = "INSERT INTO user_balance (user_id, balance) VALUES (?, ?)";
        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setLong(2, balance);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }
        return null;
    }
    public Balance getBalanceByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("Email tidak ditemukan");
        Balance balance = balanceRepository.findByUserId(user.getId());
        if (balance == null) throw new RuntimeException("Balance not found");
        return balance;
    }
    @Transactional
    public Balance updateBalance(TopUpRequest topUpRequest, String email) {
        Long id;
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("Email tidak ditemukan");
        Balance balance = balanceRepository.findByUserId(user.getId());
        // Hitung balance existing + top_up_amount
        Long totalBalance = balance.getBalance() + topUpRequest.top_up_amount;

        id = balanceRepository.updateBalance(totalBalance, email);
        if (id == null) throw new RuntimeException("failed create update balance");
        try {
            balance = getBalanceByEmail(email);
        }catch (Exception e) {
            throw new NotFoundException("Data tidak ditemukan");
        }
        return balance;
    }
}
