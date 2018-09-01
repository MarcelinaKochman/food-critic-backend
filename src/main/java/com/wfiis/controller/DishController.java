package com.wfiis.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wfiis.model.Dish;
import com.wfiis.service.DishService;

@CrossOrigin
@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Dish>> listAllDish() {
        List<Dish> dish = dishService.findAllDishs();
        if(dish.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> getDish(@PathVariable("id") String id) {
        System.out.println("Fetching Dish with id " + id);
        Optional<Dish> dish = dishService.findById(id);
        if (!dish.isPresent()) {
            System.out.println("Dish with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dish.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> getDishByName(@PathVariable("name") String name) {
        System.out.println("Fetching Dish with name " + name);
        Optional<Dish> dish = dishService.findByName(name);
        if (!dish.isPresent()) {
            System.out.println("Dish with name " + name + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dish.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createDish(@RequestBody @Valid Dish dish, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Dish " + dish.getName());

        Dish createdDish = dishService.saveDish(dish);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/dish/{id}").buildAndExpand(createdDish.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Dish> updateDish(@PathVariable("id") String id, @RequestBody Dish dish) {
        System.out.println("Updating Dish " + id);

        Optional<Dish> currentDish = dishService.findById(id);

        if (!currentDish.isPresent()) {
            System.out.println("Dish with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Dish updatedDish = currentDish.get();

        if (dish.getName() != null) updatedDish.setName(dish.getName());
        if (dish.getPrice() != null) updatedDish.setPrice(dish.getPrice());
        if (dish.getPhotoUrl() != null) updatedDish.setPhotoUrl(dish.getPhotoUrl());

        dishService.updateDish(updatedDish);
        return new ResponseEntity<>(updatedDish, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Dish> deleteDish(@PathVariable("id") String id) {
        System.out.println("Fetching & Deleting Dish with id " + id);

        Optional<Dish> dish = dishService.findById(id);
        if (!dish.isPresent()) {
            System.out.println("Unable to delete. Dish with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        dishService.deleteDishById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Dish> deleteAllDishs() {
        System.out.println("Deleting All Dishs");

        dishService.deleteAllDishs();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
