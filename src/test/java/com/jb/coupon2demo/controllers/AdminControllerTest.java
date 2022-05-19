package com.jb.coupon2demo.controllers;

import com.jb.coupon2demo.beans.ClientType;
import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.service.AdminService;
import com.jb.coupon2demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@WebAppConfiguration
@RunWith(SpringRunner.class)
@RequiredArgsConstructor
class AdminControllerTest {
    @Autowired
    private WebApplicationContext wac;


    private final String url = "http://localhost:8080/GYGNcoupons/";
    private LoginService loginService;
    @MockBean
    private AdminService adminService;
    Company company = new Company(0, "sonol", "sonol@delek.com", "sonoldelek", new HashSet<>());

    @BeforeClass
    public void init() throws CustomExceptions {
        loginService.login("admin@admin.com", "admin", ClientType.ADMIN);
    }

    @Test
    void addCompanyController() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        MvcResult result = mockMvc.perform(get("http://localhost:8080/GYGNcoupons/admin/addCompany")
                                    .accept(MediaType.ALL))
                                    .andExpect(status().isOk())
                                    .andReturn();
        String content = result.getResponse().getContentAsString();
    }

    /*
    @Test
    void updateCompany() {
    }

    @Test
    void deleteCompany() {
    }

    @Test
    void getAllCompanies() {
    }

    @Test
    void getOneCompany() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void getOneCustomer() {
    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void deleteCustomer() {
    }

     */
}