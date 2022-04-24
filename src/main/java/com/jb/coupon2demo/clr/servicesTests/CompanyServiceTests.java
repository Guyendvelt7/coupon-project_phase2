package com.jb.coupon2demo.clr.servicesTests;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.beans.Coupon;
import com.jb.coupon2demo.repositories.CompanyRepo;
import com.jb.coupon2demo.repositories.CouponRepo;
import com.jb.coupon2demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

//@Component
@Order(7)
@RequiredArgsConstructor
public class CompanyServiceTests implements CommandLineRunner {
    private final CompanyService companyService;
    private final CompanyRepo companyRepo;
    private final CouponRepo couponRepo;


    @Override
    public void run(String... args) throws Exception {
        Company company = companyRepo.getByName("samsung");

        //company login
        companyService.login(company.getEmail(), company.getPassword());
        //add coupon
        companyService.addCoupon(new Coupon(
                0, company.getId(), null, Category.HOUSEHOLD_SUPPLIES, "batteries", "rechargable 24 units pack",
                Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(30)), 2000, 50, ""));
        //update coupon
        Coupon coupon = companyRepo.findOneCompanyCoupon(company.getId());
        coupon.setAmount(1500);
        companyService.updateCoupon(coupon);
        //delete coupon
        companyService.deleteCoupon(coupon.getId());
        //2 new coupon`s addition to continue testings
        companyService.addCoupon(new Coupon(
                0, company.getId(), null, Category.HOUSEHOLD_SUPPLIES, "batteries", "rechargable 24 units pack",
                Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(30)), 2000, 50, ""));
        companyService.addCoupon(new Coupon(
                0, company.getId(), null, Category.ELECTRIC_APPLIANCE, "refrigerator", "double door 300 liters",
                Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(30)), 150, 8000, ""));
        //get company coupons
        Set<Coupon> companyCoupons = companyService.getAllCompanyCoupons();
        companyCoupons.forEach(System.out::println);
        //get company coupons by category
        Set<Coupon> companyCouponsByCategory = companyService.getCompanyCouponsByCategory(Category.HOUSEHOLD_SUPPLIES);
        companyCouponsByCategory.forEach(System.out::println);
        Set<Coupon> companyCouponsByMaxPrice = companyService.getCompanyCouponByMaxPrice(8500);
        companyCouponsByMaxPrice.forEach(System.out::println);
        //get company details
        System.out.println(companyService.getCompanyDetails());
    }


}
