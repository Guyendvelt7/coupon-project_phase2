package com.jb.coupon2demo.clr;

import com.jb.coupon2demo.beans.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Component
@RequiredArgsConstructor
public class Stam implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8080/GYGNcoupons/";

    @Override
    public void run(String... args) throws Exception {
        String token = restTemplate.postForObject(
                "http://localhost:8080/GYGNcoupons/login/admin@admin.com/admin/ADMIN", ClientType.ADMIN,String.class);
        System.out.println(token);
        ResponseEntity<List> companies = restTemplate.postForEntity(
                "http://localhost:8080/GYGNcoupons/admin/allCompanies",token,List.class);
        System.out.println(companies);
    }
}
