package com.jb.coupon2demo.repositories;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    //crud repository - 50%
    //smart dialect - 40%
    Company findByName(String name);
    Company findByEmail (String email);
    Company getByName(String name);
    Company findByEmailAndPassword(String email,String password);
    boolean existsByEmailAndPassword(String email,String password);
    boolean existsByEmailOrName(String email,String name);
    boolean existsByEmail(String email);
    boolean existsByName(String name);

    //SQL queries - 10%

    /**
     * find coupons that belong to single company by company id;
     * @param companyId the id of the company
     * @return list of coupons that belong to this company
     */
    @Query(value = "select c from Coupon c where companyId = ?1")
    Set<Coupon> findCompanyCoupons(int companyId);

    /**
     * find coupons that belong to single company by company id and a specific category;
     * @param category the category of the coupons
     * @param companyId the id of the company
     * @return list of coupons that belong to this company and in this category
     */
    @Query(value = "select c from Coupon c where category = ?1 AND companyId =?2")
    Set<Coupon> findCompanyCouponsByCategory(Category category, int companyId);

    /**
     * find coupons that belong to single company by company id and under defined maximum price
     * @param maxPrice the top price desired for the search
     * @param companyId the id of the company
     * @return list of coupons that belong to this company and under max price
     */
    @Query(value = "select c from Coupon c where price < ?1 AND companyId =?2")
    Set<Coupon> findCompanyCouponsByMaxPrice(double maxPrice, int companyId);
}
