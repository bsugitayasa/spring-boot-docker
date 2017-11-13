package com.sheringsession.balicamp.springdocker.springdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDemo2Application {

	@RequestMapping("/spring/demo2/")
    public String hello(){
        return "Hello Docker World from Spring-Demo-2";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDemo2Application.class, args);
	}
}
