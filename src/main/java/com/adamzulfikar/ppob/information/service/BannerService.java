package com.adamzulfikar.ppob.information.service;

import com.adamzulfikar.ppob.information.model.Banner;
import com.adamzulfikar.ppob.information.model.ServicePPOB;
import com.adamzulfikar.ppob.information.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }
    public List<Banner> getListBanner() throws Exception {
        List<Banner> banners = bannerRepository.listBanner();
        if (banners == null) throw new RuntimeException("Banner not found");
        return banners;
    }
    public List<ServicePPOB> getListService() throws Exception {
        List<ServicePPOB> servicePPOBS = bannerRepository.listService();
        if (servicePPOBS == null) throw new RuntimeException("Banner not found");
        return servicePPOBS;
    }
}
