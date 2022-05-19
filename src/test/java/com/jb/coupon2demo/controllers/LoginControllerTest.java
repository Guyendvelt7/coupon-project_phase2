package com.jb.coupon2demo.controllers;

import com.jb.coupon2demo.beans.ClientType;
import com.jb.coupon2demo.beans.UserDetails;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@WebMvcTest(LoginController.class)
@RequiredArgsConstructor
class LoginControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private LoginService loginService;
    private UserDetails userDetails = new UserDetails(ClientType.ADMIN, "admin@admin.com", "ADMIN");

    @Test
    void login() throws CustomExceptions {
        when(
                loginService.login(userDetails.getEmail(), userDetails.getPass(), userDetails.getClientType()))
                .thenReturn(ResponseEntity.accepted().toString());
    }
}