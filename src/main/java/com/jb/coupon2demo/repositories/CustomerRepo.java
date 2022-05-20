package com.jb.coupon2demo.repositories;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.Coupon;
import com.jb.coupon2demo.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    //crud repository - 50%

    //smart dialect - 40%
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Customer findByFirstNameAndLastName(String fName, String lname);
    Customer findByEmailAndPassword(String email,String password);
    Customer findByEmail (String email);

    //SQL queries - 10%

    /**
     * Addition of a coupon purchased by a customer into database
     * @param customer_id the id of the customer
     * @param coupon_id the id of the coupon
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `customer_vs_coupons` (customer_id, coupon_id) VALUES (?,?)", nativeQuery = true)
    void addCouponToCustomer(int customer_id, int coupon_id);

    /**
     * check if a coupon is already been purchased by the customer in question
     * @param customer_id id of the customer
     * @param coupon_id id of the coupon
     * @return list of coupons that match to customer id and coupon id,
     * result: it should be 1 or 0,
     * if 1 - the coupon has already been purchased
     * if 0 - no coupons by the coupon id inserted were founded for the requested customer
     */
    @Query(value = "SELECT * FROM customer_vs_coupons WHERE customer_id =? and coupon_id=?", nativeQuery = true)
    List<Coupon> isCouponPurchased(int customer_id, int coupon_id);

    /**
     * find one specific customer's coupons
     * @param custId the customer id
     * @return the coupon that customer had purchased
     */
    @Query("SELECT coup FROM Coupon coup WHERE coup.id IN " +
            "(SELECT coup.id FROM coup.customers cust WHERE cust.id = ?1 and coup.id = ?2)")
    Coupon findCustomerCoupon(int custId, int cpnId);

    /**
     * find all customer's coupons
     * @param custId the customer id
     * @return the list of coupons that customer had purchased
     */
    @Query("SELECT coup FROM Coupon coup WHERE coup.id= any " +
            "(SELECT coup.id FROM coup.customers cust WHERE cust.id = ?1)")
    Set<Coupon> findAllCustomerCoupons(int custId);

    /**
     * find all customer's coupons under this category
     * @param custId the customer id
     * @param category the category that we want to check
     * @return the list of coupons that customer had purchased and under this category
     */
    @Query("SELECT coup FROM Coupon coup join coup.customers cust where cust.id =?1 and coup.category = ?2")
    public Set<Coupon> findAllCustomerCouponsByCategory(int custId, Category category);

    /**
     * Find all customer's coupons under this price
     * @param custId the customer id
     * @param max_price the price that we check
     * @return the list of coupons that customer had purchased and under this price
     */
    @Query("SELECT coup FROM Coupon coup join coup.customers cust where cust.id =?1 and coup.price < ?2")
    public Set<Coupon> findAllCustomerCouponsMaxPrice(int custId, double max_price);
}
