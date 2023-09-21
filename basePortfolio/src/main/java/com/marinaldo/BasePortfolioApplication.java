package com.marinaldo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.marinaldo.controller.UserInfoController;

@SpringBootApplication
public class BasePortfolioApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BasePortfolioApplication.class, args);

	}

}
