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

    /**
     * find if coupon specific is purchased by specific customer by searching in the table "customer_vs_coupon".
     * @param coupon_id the id of specific coupon
     * @param customer_id the id of the specific customer
     * @return list of coupons that meet these parameters, if the list is empty - this coupon didn't purchase by that customer
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM `customer_vs_coupons` WHERE coupon_id=?1 AND customer_id=?2", nativeQuery = true)
    List<Coupon> isCouponPurchased(int coupon_id, int customer_id);
    //todo: this query is duplicated in customer repo, here the only usage is in tests

}
