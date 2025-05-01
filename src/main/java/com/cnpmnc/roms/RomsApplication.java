package com.cnpmnc.roms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

@RestController
// @ComponentScan(basePackages = "com.cnpmnc.roms")
public class RomsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RomsApplication.class, args);
	}


}
