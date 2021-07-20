package com.yukz.daodaoping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.yukz.daodaoping.*.dao")
@EnableCaching
@SpringBootApplication
public class DaodaopingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaodaopingApplication.class, args);
	}

}
