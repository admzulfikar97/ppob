package com.adamzulfikar.ppob.information.controller;

import com.adamzulfikar.ppob.common.dto.ApiResponse;
import com.adamzulfikar.ppob.information.dto.response.BannerResponse;
import com.adamzulfikar.ppob.information.model.Banner;
import com.adamzulfikar.ppob.information.model.ServicePPOB;
import com.adamzulfikar.ppob.information.service.BannerService;
import com.adamzulfikar.ppob.user.service.UserService;
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
    @GetMapping("banner")
    public ResponseEntity<?> getBanner() throws Exception {
        List<Banner> banners = bannerService.getListBanner();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Sukses", banners));
    }
    @GetMapping("service")
    public ResponseEntity<?> getService(Authentication authentication) throws Exception {
        List<ServicePPOB> servicePPOBList = bannerService.getListService();

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Sukses", servicePPOBList));
    }
}
