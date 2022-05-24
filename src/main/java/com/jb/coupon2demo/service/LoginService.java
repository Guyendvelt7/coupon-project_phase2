package com.jb.coupon2demo.service;

import com.jb.coupon2demo.beans.ClientType;
import com.jb.coupon2demo.beans.UserDetails;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.exceptions.OptionalExceptionMessages;
import com.jb.coupon2demo.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final JWTutil jwTutil;

    /**
     * With the help of this function you can connect to the server according to the type of client.
     * @param email client email input
     * @param password client password input
     * @param clientType client type input
     * @throws CustomExceptions if one of the params is wrong.
     */

    public String login (String email, String password, ClientType clientType) throws CustomExceptions {
        ClientService clientService = null;
        boolean isLogin = false;
        switch (clientType){
            case ADMIN:
                clientService=adminService;
                isLogin= clientService.login(email, password);
                break;
            case COMPANY:
                clientService = companyService;
                isLogin = clientService.login(email, password);
                break;
            case CUSTOMER:
                clientService = customerService;
                isLogin = clientService.login(email, password);
                break;
        }
        if(!isLogin){
            throw new CustomExceptions(OptionalExceptionMessages.LOGIN_EXCEPTION);
        }else{
            UserDetails userDetails = new UserDetails(clientType,email,password);
            return jwTutil.generateToken(userDetails);
        }
        //TODO: SINGLETON, double check, synchronisation?
    }
}
