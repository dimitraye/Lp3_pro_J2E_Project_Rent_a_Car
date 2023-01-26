package com.example.demo.controller;

import com.example.demo.selection.CarNotFoundException;
import com.example.demo.repository.CarRepository;
import com.example.demo.model.Car;
import com.example.demo.model.Dates;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

/**
 * Manage the requests linked to a car
 */
@RestController
public class LocationControllerJPA {

    private Logger log = LoggerFactory.getLogger(LocationControllerJPA.class);
    @Autowired
    CarRepository carRepository;

    /**
     *Get all the cars
     * @return a list of cars
     */
    @GetMapping("/jpa/cars")
    public ResponseEntity<Iterable<Car>> getCars(){
        log.info("Retourne les voiture");
        //Retourne les voitures et le statut de la requête
        return new ResponseEntity<>(carRepository.findAll(), HttpStatus.CREATED);
    }

    /**
     * Get a car by its immat
     * http://localhost:8080/cars/11AABB
     * @param immat
     * @return a car
     */
    @GetMapping("/jpa/cars/{plaque}")
    public ResponseEntity<Car> getCar(@PathVariable("plaque") String immat) throws CarNotFoundException {
        Car car = carRepository.findByPlaque(immat);
        //Si on ne trouve pas la voiture
        if(car == null){
            throw new CarNotFoundException("Voiture non trouvée : " + immat);
        }

        //Sinon retourner la voiture
        log.info("Retourne la voiture");
        //Retourne la voiture et le statut de la requête
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }


    /**
     * Modify a car to show that now the car has been rented
     * http://localhost:8080/cars?louer=true
     */
    @PutMapping("/jpa/cars/{plaque}")
    public Car louer(@RequestParam("louer") boolean rent,
                     @PathVariable("plaque") String immat,
                     @RequestBody Dates dates) throws CarNotFoundException {

        Car car = carRepository.findByPlaque(immat);
        //S'il n'y a pas de voiture correspondante
        if(car == null){
            throw new CarNotFoundException("Voiture non trouvée : " + immat);
        }

        Dates dateFromDB = car.getDates();
        dateFromDB.setDebut(dates.getDebut());
        dateFromDB.setFin(dates.getFin());

        car.setRent(rent);
        car.setDates(dateFromDB);
        dates.setCar(car);
        carRepository.save(car);
        return car;
    }

    /**
     * Create a car
     * Transmettre des données dans la requête
     *
     * @return the car that has been created
     */
    @PostMapping("/jpa/cars")
    public ResponseEntity<Car> creerVoiture(@RequestBody Car car){
        System.out.println(car);
        car.getDates().setCar(car);
        // A faire : ajouter une voiture

        Car carFromDB = carRepository.findByPlaque(car.getPlaque());

        //si existe, envoie exception
        if(carFromDB != null) {
            log.error("Error : Plaque already exist in the Data Base.");
            //Retourne le statut de la requête
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //sinon creer person
        log.info("Saving a new car");
        //Sauvegarde la voiture et retourne le statut de la requête
        return new ResponseEntity<>(carRepository.save(car), HttpStatus.CREATED);
    }

    /**
     * Delete the car
     * @param plaque
     * @return
     * @throws CarNotFoundException
     */
    @DeleteMapping("/jpa/cars/{plaque}")
    public ResponseEntity<Object> supprimerVoiture(@PathVariable("plaque") String plaque) throws CarNotFoundException {

        Car car = carRepository.findByPlaque(plaque);
        if(car == null){
            log.error("Error : Plaque doesn't exist in the Data Base.");
            throw new CarNotFoundException("Voiture non trouvée : " + plaque);
        }

        try {
            carRepository.delete(car);
            log.info("Car deleted");
            //Retourne le statut de la requête
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            //Retourne le statut de la requête
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
