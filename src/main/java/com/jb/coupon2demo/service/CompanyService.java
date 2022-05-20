package com.jb.coupon2demo.service;

import com.jb.coupon2demo.beans.Category;
import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.beans.Coupon;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.exceptions.OptionalExceptionMessages;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Service
public class CompanyService extends ClientService{
    private int companyId = 0;

    public CompanyService() {
    }

    /**
     * With the help of this function you can connect to the system.
     * @param email
     * @param password
     * @throws CustomExceptions
     */
    @Override
    public boolean login(String email, String password) throws CustomExceptions {
        Integer id = companyRepo.findByEmailAndPassword(email, password).getId();
        if (id == null) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        companyId = id;
        if (companyId > 0) {
            System.out.println("Company connected.");
            return true;
        } else {
            throw new CustomExceptions(OptionalExceptionMessages.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    /**
     * this function will add a coupon to a specific company in the system.
     * The function checks if there is a coupon with the same title for the same company already listed in database.
     * @param coupon new coupon object of coupon entity
     * @throws CustomExceptions 1: in case the company id is not found in database.
     * this can happen if the requesting company did not enter the system through the login process
     * 2: if already exist a similar coupon in the company's database
     */
    public void addCoupon(Coupon coupon) throws CustomExceptions {
        if (companyId == 0){
            throw new CustomExceptions(OptionalExceptionMessages.LOGIN_EXCEPTION);
        }
        coupon.setCompanyId(companyId);
        validStartDate(coupon.getStartDate());
        validEndDate(coupon.getEndDate(), coupon);
        if (couponRepo.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
            System.out.println("This coupon title already exist for this company.");
            throw new CustomExceptions(OptionalExceptionMessages.CANT_ADD_COUPON);
        }
        couponRepo.save(coupon);
        System.out.println("Coupon added successfully");
    }

    /**
     * this function will update coupon's details in the system.
     * The function checks if this coupon exists in the system,
     * if the company requesting the update is the coupon's company listed in database
     * and if the end date of the coupon has not expired
     * @param coupon coupon object to be updated
     * @throws CustomExceptions 1: if the coupon for updating is not found in database
     *                          2: the company requesting the update is not the registered company in the coupon detail
     */
    public void updateCoupon(Coupon coupon) throws CustomExceptions {
        if (couponRepo.findById(coupon.getId()).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        if (coupon.getCompanyId()!=this.companyId){
            throw new CustomExceptions(OptionalExceptionMessages.CANT_CHANGE_COMPANY_ID);
        }
        validEndDate(coupon.getEndDate(), coupon);
        coupon.setCompanyId(this.companyId);
        couponRepo.save(coupon);
        System.out.println("Coupon updated successfully");
    }

    /**
     * this function will delete a coupon from the system.
     * The function checks if this coupon exists in the system
     * @param couponId the coupon object to be deleted from database
     * @throws CustomExceptions if the coupon for deleting is not found in database
     */
    public void deleteCoupon(int couponId) throws CustomExceptions {
        if (couponRepo.findById(couponId).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        couponRepo.deleteById(couponId);
        System.out.println("Coupon deleted successfully");
    }

    /**
     *This function returns all the coupons of a particular company.
     * @throws CustomExceptions if there are no coupons listed in the company's database
     */
    public Set<Coupon> getAllCompanyCoupons() throws CustomExceptions {
        if (companyRepo.findCompanyCoupons(companyId).isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.EMPTY_LIST);
        }else {
            return companyRepo.findCompanyCoupons(companyId);
        }
    }

    /**
     *This function returns one specific coupon of a particular company.
     * @throws CustomExceptions if the coupon id is not listed in the company's database
     */
    public Coupon getOneCoupon(int couponId) throws CustomExceptions {
        if (couponRepo.findById(couponId).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        return couponRepo.findById(couponId).get();
    }

    /**
     *This function returns all company coupons by a particular category.
     * @throws CustomExceptions if the category of the coupon requested is not found in database
     */
    public Set<Coupon> getCompanyCouponsByCategory(Category category) throws CustomExceptions {
        Set<Coupon> coupons = companyRepo.findCompanyCouponsByCategory(category, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_BY_CATEGORY);
        }
        return coupons;
    }

    /**
     *This function returns all company coupons by a defined maximum price.
     * @param maxPrice defines the maximum price of the requested coupons.
     * @throws CustomExceptions if there are no coupons under the specified max price
     */
    public Set<Coupon> getCompanyCouponByMaxPrice(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = companyRepo.findCompanyCouponsByMaxPrice(maxPrice, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_MAX_PRICE);
        }
        return coupons;
    }

    /**
     * This function returns the details about the company.
     * @throws CustomExceptions in case the company is not found in database
     */
    public String getCompanyDetails() throws CustomExceptions {
        Company company = companyRepo.findById(companyId).get();
        company.setCoupons(getAllCompanyCoupons());
        if (company == null) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        return company.toString() + company.getCoupons();
    }

    /**
     * The function checks that the start date of the coupon is not previous of the current day of coupon creation.
     * @param date date to be checked
     * @throws CustomExceptions if the start date of the coupon  is previous to current day of creation
     */
    private void validStartDate(Date date) throws CustomExceptions {
        Date currDate = Date.valueOf(LocalDate.now());
        if (date.before(currDate)) {
            throw new CustomExceptions(OptionalExceptionMessages.START_DATE_EXCEPTION);
        }
    }

    /**
     * The function checks that the end date of the coupon is correct.
     * @param date date to be checed
     * @throws CustomExceptions  if the end date of the coupon is previous to the start day;
     * or if the end date is previous to the current day of creation
     */
    private void validEndDate(Date date, Coupon coupon) throws CustomExceptions {
        Date StartDate = coupon.getStartDate();
        if (date.before(StartDate) || date.before(Date.valueOf(LocalDate.now()))) {
            throw new CustomExceptions(OptionalExceptionMessages.END_DATE_EXCEPTION);
        }
    }
}
