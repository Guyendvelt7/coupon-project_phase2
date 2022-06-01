package com.jb.coupon2demo.controllers;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.ClientType;
import com.jb.coupon2demo.beans.Coupon;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.security.JWTutil;
import com.jb.coupon2demo.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/GYGNcoupons/company")
/**
 * this class is used for the company API's methods implementation
 */
public class CompanyController {
    private final CompanyService companyService;
    private final JWTutil jwTutil;

    /**
     * this method is for adding a new coupon in to the database
     * @param coupon insertion of the new coupon information
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions and request status response
     * @throws CustomExceptions 1: in case the company id is not found in database.
     * this can happen if the requesting company did not enter the system through the login process
     * 2: if already exist a similar coupon in the company's database
     */
    @PutMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon,@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        companyService.addCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body("coupon " + coupon.getTitle() + " added");
    }

    /**
     *this method is for updating coupon information in to the database
     * using PutMapping allows the client to insert more information without revealing sensible data
     * @param coupon required information about the coupon to update
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions and request status response
     * @throws CustomExceptions 1: if the coupon for updating is not found in database
     *                          2: the company requesting the update is not the registered company in the coupon detail
     */
    @PutMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon,@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        companyService.updateCoupon(coupon);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body("coupon " + coupon.getTitle() + " updated");
    }

    /**
     * this method is for deleting a specific coupon from the company coupons list in database
     * @param couponId to identify the coupon to delete
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions and request status response
     * @throws CustomExceptions if the coupon id is not found in the company coupons list in database
     */
    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId,@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        companyService.deleteCoupon(couponId);
        return ResponseEntity.ok()
                .header("Authorization", token)
                .body("coupon deleted");
    }

    /**
     * this HTTP method is for retrieving all coupons listed by the requesting company
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return  new token for more company actions, the list of coupons requested and request status response
     * @throws CustomExceptions if the company has no coupons listed in database
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(companyService.getAllCompanyCoupons());
    }

    /**
     * this HTTP method is for retrieving information about a specific coupon of the requesting company
     * @param couponId to identify the requested coupon
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return  new token for more company actions, the details of the requested coupon and request status response
     * @throws CustomExceptions if the requested coupon is not found in the company coupons list in database
     */
    @GetMapping("/getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int couponId, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(companyService.getOneCoupon(couponId));
    }

    /**
     * this HTTP method is for retrieving requesting company's coupons filtered by coupons category
     * @param category the type of coupon defined by the company
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions, list of coupons by the requested category and request status response
     * @throws CustomExceptions in case the server did not found any coupons by the requested category
     */
    @GetMapping("/getCouponsByCategory/{category}")
    public ResponseEntity<?> getOneCouponByCategory(@RequestParam Category category, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(companyService.getCompanyCouponsByCategory(category));
    }

    /**
     * this HTTP method is for retrieving  coupons filtered by price (from maximum and below)
     * @param maxPrice defines the maximum price of the requested coupons
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions, list of coupons requested
     * (the response will be every coupon with a price below the maximum inserted)
     *            and request status response
     * @throws CustomExceptions  in case the server did not found any coupons by the requested price or below
     */
    @GetMapping("/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@PathVariable int maxPrice, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(companyService.getCompanyCouponByMaxPrice(maxPrice));
    }

    /**
     * this HTTP method is for retrieving a specific company details
     * the requesting company is identified in the {@link LoginController} process
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more company actions, company's details and request status response
     * @throws CustomExceptions in case the requesting company is not found in database.
     * this can happen if the requesting company did not enter the system through the login process
     */
    @GetMapping("/companyDetails")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.COMPANY);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(companyService.getCompanyDetails());
    }
}
