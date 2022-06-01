package com.jb.coupon2demo.exceptions;
/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

/**
 * list of possible reasons to a server side exception
 */
public enum OptionalExceptionMessages {
    CANT_PURCHASE_COUPON("cant purchase this coupon!"),
    CANT_ADD_COUPON("can't add this coupon!"),
    COUPON_NOT_FOUND("coupon not found!"),
    COUPON_NOT_FOUND_BY_CATEGORY("No coupons found for this category."),
    COUPON_NOT_FOUND_MAX_PRICE("No coupons found under this price."),
    CUSTOMER_NOT_FOUND("customer not found!"),
    COMPANY_NOT_FOUND("company not found!"),
    WRONG_EMAIL_OR_PASSWORD("you can not connect, there was a problem with your email or password"),
    EMAIL_OR_NAME_EXISTS("email or name already exists!"),
    EMAIL_EXISTS("can not add existing email"),
    CANT_UPDATE_COMPANY_NAME("can't change company name!"),
    LOGIN_EXCEPTION("Invalid user type"),
    TOKEN_EXCEPTION("Invalid authorization password"),
    START_DATE_EXCEPTION("Invalid start date"),
    END_DATE_EXCEPTION("Invalid end date, it must be older than the start date."),
    CANT_CHANGE_COMPANY_ID("can not change company id!!"),
    EMPTY_LIST("No data found in data base"),
    DONT_HAVE_PERMISSION("you dont have this permission!");

    private String message;

    private OptionalExceptionMessages(String message) {
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
