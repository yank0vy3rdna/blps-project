package ru.itmo.blps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.itmo.blps"})
public class BlpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlpsApplication.class, args);
	}

}
