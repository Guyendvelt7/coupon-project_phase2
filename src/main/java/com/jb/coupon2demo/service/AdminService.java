package com.jb.coupon2demo.service;

import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.beans.Customer;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.exceptions.OptionalExceptionMessages;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */


@Service
public class AdminService extends  ClientService{

    public AdminService() {
    }

    /**
     * With the help of this function you can connect to the system.
     * @param email input by administrator
     * @param password input by administrator
     * @throws CustomExceptions if one of the params does not match the hard-coded specifications
     */
    @Override
    public boolean login(String email, String password) throws CustomExceptions {
        if (email.equals("admin@admin.com") && password.equals("admin")) {
            System.out.println("Admin Connected.");
            return true;
        }
        throw new CustomExceptions(OptionalExceptionMessages.LOGIN_EXCEPTION);
    }

    /**
     * this function will add a new company to the database.
     * The function will check if the company does not already exist in the database,
     * if there is no company listed with the same name or email.
     * @param company a new object of company entity to be created
     * @throws CustomExceptions in case the method found in database another company by the same name or email address already listed
     */
    public void addCompany(Company company) throws CustomExceptions {
        if (companyRepo.existsByName(company.getName()) || companyRepo.existsByEmail(company.getEmail())) {
            throw new CustomExceptions(OptionalExceptionMessages.EMAIL_OR_NAME_EXISTS);
        }
        companyRepo.save(company);
        System.out.println("Company added successfully");
    }

    /**
     * this function will update company's details in the system.
     * @param company the object of company entity to update
     * @throws CustomExceptions in case the data to be updated is the company name
     */
    public void updateCompany(Company company) throws CustomExceptions {
        if(!companyRepo.existsById(company.getId())) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        if (!Objects.equals(company.getName(), companyRepo.findById(company.getId()).get().getName())) {
            throw new CustomExceptions(OptionalExceptionMessages.CANT_UPDATE_COMPANY_NAME);
        }
        companyRepo.save(company);
        System.out.println("Company updated successfully");
    }

    /**
     * this function will delete company from the system.
     * The function checks if this company exists in the system
     * @param companyId the company id to be deleted
     * @throws CustomExceptions in case the company id has not been found in database
     */
    public void deleteCompany(int companyId) throws CustomExceptions {
        if (!companyRepo.existsById(companyId)) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        companyRepo.deleteById(companyId);
        System.out.println("Company deleted successfully");
    }

    /**
     *This function returns all the companies listed in database.
     * @throws CustomExceptions if companies table in database is empty
     */
    public List<Company> getAllCompanies() throws CustomExceptions {
        if(companyRepo.findAll().isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.EMPTY_LIST);
        }
        return companyRepo.findAll();
    }

    /**
     *This function returns a single company.
     * @param companyId the id of the requested company
     * @throws CustomExceptions in case the company id is not found in the database
     */
    public Company getOneCompany(int companyId) throws CustomExceptions {
        if (!companyRepo.existsById(companyId)) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        return companyRepo.findById(companyId).get();
    }

    /**
     * this function will add a new customer in the system.
     * The function checks if there is no similar customer already in database with the same  email.
     * @param customer new customer object from customer entity
     * @throws CustomExceptions in case a customer object is found in database with the same email
     */
    public void addCustomer(Customer customer) throws CustomExceptions {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new CustomExceptions(OptionalExceptionMessages.EMAIL_EXISTS);
        }
        customerRepo.save(customer);
        System.out.println("Customer added successfully");
    }

    /**
     * this function will update customer's details in the system.
     * The function checks if this customer id exists in the system
     * @param customer customer object to be updated
     * @throws CustomExceptions if the customer's id is not found in database
     */
    public void updateCustomer(Customer customer) throws CustomExceptions {
        if (!customerRepo.existsById(customer.getId())) {
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        }
        customerRepo.save(customer);
        System.out.println("Customer updated successfully");
    }

    /**
     *This function returns a single customer.
     * @param customerId the id of the customer to be found
     * @throws CustomExceptions if the customer's id is not found in database
     */
    public Customer getOneCustomer(int customerId) throws CustomExceptions {
        if(!customerRepo.existsById(customerId)){
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        } else{
            return customerRepo.findById(customerId).get();
        }
    }
    /**
     *This function returns all the customers in the database.
     */
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    /**
     * this function will delete a customer from the system.
     * The function checks if this customer exists in the system
     * @param customerId the customer to be deleted
     * @throws CustomExceptions if the customer's id is not found in database
     */
    public void deleteCustomer(int customerId) throws CustomExceptions {
        if(!customerRepo.existsById(customerId)){
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        } else{
            customerRepo.deleteById(customerId);
            System.out.println("Customer deleted successfully");
        }
    }
}
