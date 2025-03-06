package com.inditex.checkprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.inditex.checkprice")
public class CheckpriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckpriceApplication.class, args);
	}

}
