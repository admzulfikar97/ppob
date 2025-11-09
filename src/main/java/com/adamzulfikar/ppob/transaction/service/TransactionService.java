package com.adamzulfikar.ppob.transaction.service;

import com.adamzulfikar.ppob.balance.model.Balance;
import com.adamzulfikar.ppob.balance.repository.BalanceRepository;
import com.adamzulfikar.ppob.common.exception.BadRequestException;
import com.adamzulfikar.ppob.common.exception.NotFoundException;
import com.adamzulfikar.ppob.transaction.model.Services;
import com.adamzulfikar.ppob.transaction.model.Transaction;
import com.adamzulfikar.ppob.transaction.repository.TransactionRepository;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private static final AtomicInteger counter = new AtomicInteger(0);
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
                              BalanceRepository balanceRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public Transaction createTransaction(String serviceCode, String email){
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("Email tidak ditemukan");
        Services services = transactionRepository.findByServiceCode(serviceCode);
        if (services == null) throw new RuntimeException("Service not found with service_code: " + serviceCode);
        if (!services.getService_code().equals(serviceCode)) throw new BadRequestException("Service ataus Layanan tidak ditemukan");
        Balance balance = balanceRepository.findByUserId(user.getId());
        // Hitung balance - service tariff
        Long remainingBalance = balance.getBalance() - services.getService_tariff();
        if (remainingBalance < 0) throw new BadRequestException("Balance tidak cukup");
        balanceRepository.updateBalance(remainingBalance, email);
        Long id = transactionRepository.createTransaction(services, generateInvoiceNumber(), user);
        if (id == null) throw new BadRequestException("Transaksi Gagal");

        return transactionRepository.findByUserId(id);
    }
    public List<Transaction> getListTransaction(String email, int limit, int offset) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("Email tidak ditemukan");
        List<Transaction> servicePPOBS = transactionRepository.listTransactionByUserId(user.getId(), offset, limit);
        if (servicePPOBS == null) throw new RuntimeException("Banner not found");
        return servicePPOBS;
    }
    public static String generateInvoiceNumber() {
        String prefix = "INV";
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());

        int sequence = counter.incrementAndGet();
        String sequenceFormatted = String.format("%03d", sequence); // 001, 002, 003 ...

        return prefix + date + "-" + sequenceFormatted;
    }
}