package com.example.demo;

import com.example.demo.model.Car;
import com.example.demo.model.Dates;
import com.example.demo.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Iterator;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarRepository carRepository) {
		return (args) -> {
			Car car = new Car("Ferrari", 1000, "AA11BB");
			Dates dates = new Dates("12/2/2023", "2/5/2023");
			car.setDates(dates);
			dates.setCar(car);
			carRepository.save(car);

			Iterator<Car> cars = carRepository.findAll().iterator();
			while(cars.hasNext()){
				Car c = cars.next();
				System.out.println(c.getMarque() + " " + c.getPrix() + " " + c.getPlaque());
			}

			car = carRepository.findByMarque("Ferrari");
			System.out.println(car);
		};
	}

}
