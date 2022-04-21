package com.ldblock.carid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.ldblock.carid.*")
public class CaridApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaridApplication.class, args);
	}

}
