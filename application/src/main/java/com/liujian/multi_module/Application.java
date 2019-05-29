package com.liujian.multi_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages= {"com.liujian"})
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
}
