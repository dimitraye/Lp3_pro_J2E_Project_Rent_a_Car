package com.example.demo.controller;

import com.example.demo.selection.CarNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.model.Dates;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage the requests linked to a firestation
 */
@RestController
public class LocationController {

    ArrayList<Car> cars = new ArrayList<Car>();

    public LocationController(){
        Car car = new Car("Ferrari", 200, "AA11BB");
        cars.add(car);
    }

    @GetMapping("/cars")
    public List<Car> getCars(){
        return cars;
    }

    /**
     * http://localhost:8080/cars/11AABB
     * @param immat
     * @return
     */
    @GetMapping("/cars/{plaque}")
    public Car getCar(@PathVariable("plaque") String immat) throws CarNotFoundException {
        int i=0;
        while(i<cars.size() && cars.get(i).getPlaque().equals(immat)==false){
            i++;
        }
        if(i<cars.size()){
            return cars.get(i);
        } else {
            throw new CarNotFoundException("Voiture non trouvée : " + immat);
        }
    }
    /**
     * http://localhost:8080/cars/11AABB
     * @param immat
     * @return
     */
    /*@GetMapping("/cars/{plaque}")
    public Car getCar(@PathVariable("plaque") String immat) throws CarNotFoundException {
        try{
            Optional<Car> car = cars.stream().filter(c -> c.plaque.equals(immat)).findFirst();
            System.out.println(car);
            return car.get();
        } catch (NoSuchElementException e) {
            throw new CarNotFoundException();
        }
    }*/

    /**
     * http://localhost:8080/cars?louer=true
     */
    @PutMapping("/cars/{plaque}")
    public Car louer(@RequestParam("louer") boolean rent,
                     @PathVariable("plaque") String immat,
                     @RequestBody Dates dates) throws CarNotFoundException {

        int i=0;
        while(i<cars.size() && cars.get(i).getPlaque().equals(immat)==false){
            i++;
        }
        if(i<cars.size()){
            cars.get(i).setRent(rent);
            cars.get(i).setDates(dates);
            return cars.get(i);
        } else {
            throw new CarNotFoundException("Voiture non trouvée : " + immat);
        }
    }

    /**
     * Transmettre des données dans la requête
     */
    @PostMapping("/cars")
    public void creerVoiture(@RequestBody Car car){
        System.out.println(car);
        // A faire : ajouter une voiture
    }
}