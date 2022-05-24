package com.jb.coupon2demo.service;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.Coupon;
import com.jb.coupon2demo.beans.Customer;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.exceptions.OptionalExceptionMessages;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Service
public class CustomerService extends ClientService{
    private int customerId;

    /**
     * With the help of this function the customers can connect to the server.
     * @param email customer email input
     * @param password customer password input
     * @throws CustomExceptions if one of the params is wrong.
     */
    @Override
    public boolean login(String email, String password) throws CustomExceptions {
        boolean connect = customerRepo.existsByEmailAndPassword(email, password);
        if(connect) {
            Integer id = customerRepo.findByEmailAndPassword(email, password).getId();
            customerId = id;
            System.out.println("Customer connected.");
            return true;
        } else {
            throw new CustomExceptions(OptionalExceptionMessages.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    /**
     * With the help of this function the customer can purchase coupons.
     * The function checks if the coupon has already been purchased by the customer and if the coupon exists in the system.
     * And when it is purchased the amount of coupons listed in database decreases by 1.
     * @param couponId the id of the coupon that the customer want to purchase.
     * @throws CustomExceptions if the coupon is not in the database.
     */
    public void purchaseCoupon(int couponId) throws CustomExceptions {
        if (!couponRepo.existsById(couponId)){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        Coupon coupon = couponRepo.findById(couponId).get();
        if(customerRepo.isCouponPurchased(customerId, coupon.getId()).isEmpty()){
            customerRepo.addCouponToCustomer(customerId, coupon.getId());
            coupon.setAmount(coupon.getAmount()-1);
            couponRepo.save(coupon);
            System.out.println("Coupon purchased.");
        }
        else{
            throw new CustomExceptions(OptionalExceptionMessages.CANT_ADD_COUPON);
        }
    }

    /**
     *This function returns all the coupons of a particular customer
     */
    public Set<Coupon> getAllCustomerCoupons(){
        return customerRepo.findAllCustomerCoupons(customerId);
    }

    /**
     * This function returns all the coupons of a particular customer by category.
     * @param category the requested category
     * @throws CustomExceptions if there was no coupons find by requested category
     */
    public Set<Coupon> getCustomerCoupons(Category category) throws CustomExceptions {
        Set<Coupon> coupons = customerRepo.findAllCustomerCouponsByCategory(customerId, category);
        if(coupons.isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_BY_CATEGORY);
        }
        return coupons;
    }

    /**
     * This function returns all the coupons of a particular customer by a defined maximum price.
     * @param maxPrice defines the maximum price of the requested coupons.
     * @throws CustomExceptions if there are no coupons under the specified max price
     */
    public Set<Coupon> getCustomerCoupons(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = customerRepo.findAllCustomerCouponsMaxPrice(customerId, maxPrice);
        if(coupons.isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_MAX_PRICE);
        }
        return coupons;
    }

    /**
     * This function returns the details about a particular customer.
     * @throws CustomExceptions in case the customer is not found in database.
     */
    public Customer getCustomerDetails() throws CustomExceptions {
        Customer customer = customerRepo.findById(customerId).get();
        if (customer == null){
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        }
        return customer;
    }
}
