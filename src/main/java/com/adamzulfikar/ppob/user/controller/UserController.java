package com.adamzulfikar.ppob.user.controller;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.user.dto.request.LoginRequest;
import com.adamzulfikar.ppob.user.dto.request.RegisterRequest;
import com.adamzulfikar.ppob.user.dto.request.UserRequest;
import com.adamzulfikar.ppob.user.dto.response.LoginResponse;
import com.adamzulfikar.ppob.user.dto.response.RegisterResponse;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.service.ImageFileService;
import com.adamzulfikar.ppob.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageFileService imageFileService;
    @PostMapping("registration")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        Long id = userService.register(req.first_name, req.last_name, req.email, req.password);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(id);
        registerResponse.setEmail(req.email);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Registrasi berhasil silahkan login", null));
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        String res = userService.login(req);
        LoginResponse logres = new LoginResponse();
        logres.setToken(res);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "success", logres));
    }
    @GetMapping("profile")
    public ResponseEntity<?> getUser(Authentication authentication) throws Exception {
        String email = authentication.getPrincipal().toString();
        User user = userService.getUserByEmail(email);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setFirst_name(user.getFirstname());
        registerResponse.setLast_name(user.getLastname());
        registerResponse.setEmail(user.getEmail());
        registerResponse.setProfile_image(user.getFilepath());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "registered", registerResponse));
    }
    @PutMapping("profile/update")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserRequest userRequest) throws Exception {
        String email = authentication.getPrincipal().toString();
        User user = userService.updateUser(userRequest.first_name, userRequest.last_name, email);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setFirst_name(user.getFirstname());
        registerResponse.setLast_name(user.getLastname());
        registerResponse.setEmail(user.getEmail());
        registerResponse.setProfile_image(user.getFilepath());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "registered", registerResponse));
    }
    @PutMapping("profile/image")
    public ResponseEntity<?> uploadUserImage(Authentication authentication, @RequestParam("file") MultipartFile file) {
        String email = (String) authentication.getPrincipal();
        try {
            User user = imageFileService.saveImage(file, email);
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setEmail(user.getEmail());
            registerResponse.setFirst_name(user.getFirstname());
            registerResponse.setLast_name(user.getLastname());
            registerResponse.setProfile_image(user.getFilepath());
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Upload success", registerResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }
}
