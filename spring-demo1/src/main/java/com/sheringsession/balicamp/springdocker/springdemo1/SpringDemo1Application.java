package com.sheringsession.balicamp.springdocker.springdemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringDemo1Application {

    @RequestMapping("/spring/demo1/")
    public String hello(){
        return "Hello Docker World from Spring-Demo-1";
    }
    
	public static void main(String[] args) {
		SpringApplication.run(SpringDemo1Application.class, args);
	}
}
