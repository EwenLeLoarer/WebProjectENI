package fr.eni.ENI_enchere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EniEnchereApplication {

	public static void main(String[] args) {
		SpringApplication.run(EniEnchereApplication.class, args);
	}

}
