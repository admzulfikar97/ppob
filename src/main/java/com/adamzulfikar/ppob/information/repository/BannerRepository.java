package com.adamzulfikar.ppob.information.repository;

import com.adamzulfikar.ppob.information.model.Banner;
import com.adamzulfikar.ppob.information.model.ServicePPOB;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BannerRepository {
    private final DataSource ds;
    public BannerRepository(DataSource dataSource) {
        this.ds = dataSource;
    }

    public List<Banner> listBanner() {
        String sql = "SELECT banner_name, banner_image, description FROM banner";

        List<Banner> banners = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banner banner = new Banner(
                        rs.getString("banner_name"),
                        rs.getString("banner_image"),
                        rs.getString("description")
                );
                banners.add(banner);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }

        return banners;
    }
    public List<ServicePPOB> listService() {
        String sql = "SELECT service_code, service_name, service_icon, service_tariff FROM service";

        List<ServicePPOB> servicePPOBList = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ServicePPOB servicePPOB = new ServicePPOB(
                        rs.getString("service_code"),
                        rs.getString("service_name"),
                        rs.getString("service_icon"),
                        rs.getInt("service_tariff")
                );
                servicePPOBList.add(servicePPOB);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Database error: " + ex.getMessage(), ex);
        }

        return servicePPOBList;
    }
}
