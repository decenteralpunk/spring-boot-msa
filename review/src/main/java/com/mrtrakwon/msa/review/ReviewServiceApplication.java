package com.mrtrakwon.msa.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.mrtrakwon.msa")
@SpringBootApplication
public class ReviewServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReviewServiceApplication.class, args);
	}
}
