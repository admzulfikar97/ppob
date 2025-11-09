package com.adamzulfikar.ppob.information.controller;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.common.exception.NotFoundException;
import com.adamzulfikar.ppob.information.model.Banner;
import com.adamzulfikar.ppob.information.model.ServicePPOB;
import com.adamzulfikar.ppob.information.service.BannerService;
import com.adamzulfikar.ppob.user.model.User;
import com.adamzulfikar.ppob.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("banner")
    public ResponseEntity<?> getBanner() throws Exception {
        List<Banner> banners = bannerService.getListBanner();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Sukses", banners));
    }
    @GetMapping("service")
    public ResponseEntity<?> getService(Authentication authentication) throws Exception {
        User user = userRepository.findByEmail(authentication.getPrincipal().toString());
        if (user == null) throw new NotFoundException("Email tidak ditemukan");
        List<ServicePPOB> servicePPOBList = bannerService.getListService();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Sukses", servicePPOBList));
    }
}
