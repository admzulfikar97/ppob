package com.adamzulfikar.ppob.user.service;

import com.adamzulfikar.ppob.balance.service.BalanceService;
import com.adamzulfikar.ppob.common.config.JwtUtil;
import com.adamzulfikar.ppob.common.exception.BadRequestException;
import com.adamzulfikar.ppob.common.exception.GlobalExceptionHandler;
import com.adamzulfikar.ppob.common.exception.NotFoundException;
import com.adamzulfikar.ppob.common.exception.UnauthorizedException;
import com.adamzulfikar.ppob.user.dto.request.LoginRequest;
import com.adamzulfikar.ppob.user.dto.request.RegisterRequest;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final BalanceService balanceService;
    public UserService(UserRepository userRepo, JwtUtil jwtUtil, BalanceService balanceService) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.balanceService = balanceService;
    }
    @Transactional
    public Long register(String firstname, String lastname, String email, String password) {
        Long id;
        if (userRepo.existsByEmail(email)) throw new BadRequestException("Email exists");
//        String hashed = encoder.encode(password);
        id = userRepo.createUser(firstname, lastname, email, password);

        if (id == null) throw new RuntimeException("failed create user");
        balanceService.createBalance(id, 0L);
        return id;
    }
    public String login(LoginRequest req) {
        User user = userRepo.findByEmail(req.email);
//        String hashed = encoder.encode(req.password);
//        System.out.println(req.password +" , "+ user.getPassword());
        if (user == null || !(req.password.equalsIgnoreCase(user.getPassword()))) {
            throw new BadRequestException("Username atau password salah");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        return token;
    }
    public User getUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found with email: " + email);
        return user;
    }
    @Transactional
    public User updateUser(String firstname, String lastname, String email) {
        Long id;
        User user;
//        try {
//            user = getUserByEmail(email);
//        }catch (Exception e) {
//            throw new NotFoundException("Data tidak ditemukan");
//        }
        id = userRepo.updateUser(firstname, lastname, email);

        if (id == null) throw new RuntimeException("failed create user");
        try {
            user = getUserByEmail(email);
        }catch (Exception e) {
            throw new NotFoundException("Data tidak ditemukan");
        }
        return user;
    }
}
