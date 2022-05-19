package com.jb.coupon2demo;

import com.jb.coupon2demo.controllers.LoginController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Coupon2demoApplicationTests {

	@Autowired
	private LoginController loginController;

	@Test
	public void contextLoads() throws Exception{
		assertThat(loginController).isNotNull();
	}

}
