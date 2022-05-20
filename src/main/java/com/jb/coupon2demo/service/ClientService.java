package com.jb.coupon2demo.service;

import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.repositories.CompanyRepo;
import com.jb.coupon2demo.repositories.CouponRepo;
import com.jb.coupon2demo.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

/**
 * this class is to initialize the repositories use in the services.
 */
public abstract class ClientService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected CustomerRepo customerRepo;

    /**
     * to see explanations about this method:
     * {@link CompanyService#login(String, String)}
     * {@link CustomerService#login(String, String)}
     * {@link AdminService#login(String, String)}
     */
    public abstract boolean login(String mail, String password)throws CustomExceptions;
}
