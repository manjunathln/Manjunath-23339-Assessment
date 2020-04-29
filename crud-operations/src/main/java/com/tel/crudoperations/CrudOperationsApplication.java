package com.tel.crudoperations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class CrudOperationsApplication {
	static Logger logger = LoggerFactory.getLogger(CrudOperationsApplication.class);
	public static void main(String[] args) {
		logger.info("Hi Welcome to Customer CURD Application..");
		SpringApplication.run(CrudOperationsApplication.class, args);
	}


}
