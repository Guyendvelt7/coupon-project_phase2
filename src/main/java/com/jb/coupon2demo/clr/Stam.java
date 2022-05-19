package com.jb.coupon2demo.clr;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

//@Component
@RequiredArgsConstructor
public class Stam implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final String url = "http://localhost:8080/GYGNcoupons/";

    @Override
    public void run(String... args) throws Exception {
        String token = restTemplate.getForObject(url + "login/admin@admin.com/ADMIN/ADMIN", String.class);
        System.out.println(token);
    }
}
