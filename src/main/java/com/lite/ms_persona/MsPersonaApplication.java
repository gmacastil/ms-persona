package com.lite.ms_persona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MsPersonaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPersonaApplication.class, args);
	}

}
