package com.liujian.multi_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {"com.liujian"})
@EntityScan(basePackages= {"com.liujian.order"})
@EnableJpaRepositories(basePackages= {"com.liujian"})
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
}
