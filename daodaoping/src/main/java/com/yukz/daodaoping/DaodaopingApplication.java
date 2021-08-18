package com.yukz.daodaoping;

import java.io.File;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@MapperScan("com.yukz.daodaoping.*.dao")
@EnableCaching
@SpringBootApplication
public class DaodaopingApplication {

//	@Bean
//	public StartupRunner startupRunner() {
//		return new StartupRunner();
//	}

	public static void main(String[] args) {
		SpringApplication.run(DaodaopingApplication.class, args);
	}
	

}
