package com.jb.coupon2demo.repositories;

import com.jb.coupon2demo.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    //crud repository - 50%
    //smart dialect - 40%
    Coupon findByTitleAndCompanyId(String title, int companyID);
    Boolean existsByTitleAndCompanyId(String title, int companyID);

    //SQL queries - 10%

    /**
     * delete coupon that there are expired on the current date.
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM `coupons_project2demo`.`coupons` WHERE (end_Date) < curDate()", nativeQuery = true)
    void deleteCouponsByDate();
}
