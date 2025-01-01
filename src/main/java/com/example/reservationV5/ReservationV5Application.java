package com.example.reservationV5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
public class ReservationV5Application {

	public static void main(String[] args) {
		SpringApplication.run(ReservationV5Application.class, args);
	}

}
