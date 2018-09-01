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
import com.wfiis.model.Restaurant;
import com.wfiis.service.DishService;
import com.wfiis.service.RestaurantService;

@CrossOrigin
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final DishService dishService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> listAllRestaurant() {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        if(restaurants.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") String id) {
        System.out.println("Fetching Restaurant with id " + id);
        Optional<Restaurant> restaurant = restaurantService.findById(id);
        if (!restaurant.isPresent()) {
            System.out.println("Restaurant with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable("name") String name) {
        System.out.println("Fetching Restaurant with name " + name);
        Optional<Restaurant> restaurant = restaurantService.findByName(name);
        if (!restaurant.isPresent()) {
            System.out.println("Restaurant with name " + name + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurant.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createRestaurant(@RequestBody @Valid Restaurant restaurant, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Restaurant " + restaurant.getName());

        Restaurant createdRestaurant = restaurantService.saveRestaurant(restaurant);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/restaurant/{id}").buildAndExpand(createdRestaurant.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") String id, @RequestBody Restaurant restaurant) {
        System.out.println("Updating Restaurant " + id);

        Optional<Restaurant> currentRestaurant = restaurantService.findById(id);

        if (!currentRestaurant.isPresent()) {
            System.out.println("Restaurant with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Restaurant updatedRestaurant = currentRestaurant.get();

        if (restaurant.getName() != null) updatedRestaurant.setName(restaurant.getName());
        if (restaurant.getAddress() != null) updatedRestaurant.setAddress(restaurant.getAddress());
        if (restaurant.getMenu() != null) updatedRestaurant.setMenu(restaurant.getMenu());
        if (restaurant.getPhoneNumber() != 0) updatedRestaurant.setPhoneNumber(restaurant.getPhoneNumber());
        if (restaurant.getWebPage() != null) updatedRestaurant.setWebPage(restaurant.getWebPage());
        if (restaurant.getPhotoUrl() != null) updatedRestaurant.setPhotoUrl(restaurant.getPhotoUrl());

        restaurantService.updateRestaurant(updatedRestaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/addDish/{dishId}", method = RequestMethod.GET)
    public ResponseEntity<Restaurant> addToMenu(
            @PathVariable("id") String restaurantId,
            @PathVariable("dishId") String dishId) {
        System.out.println("Adding Dish " + dishId + " to menu of Restaurant " + restaurantId);

        Optional<Restaurant> currentRestaurant = restaurantService.findById(restaurantId);
        Optional<Dish> currentDish = dishService.findById(dishId);

        if (!currentRestaurant.isPresent()) {
            System.out.println("Restaurant with id " + restaurantId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!currentDish.isPresent()) {
            System.out.println("Dish with id " + restaurantId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Restaurant updatedRestaurant = currentRestaurant.get();
        updatedRestaurant.addToMenu(currentDish.get().getId());

        restaurantService.updateRestaurant(updatedRestaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/removeDish/{dishId}", method = RequestMethod.DELETE)
    public ResponseEntity<Restaurant> removeFromMenu(
            @PathVariable("id") String restaurantId,
            @PathVariable("dishId") String dishId) {
        System.out.println("Removing Dish " + dishId + " from menu of Restaurant " + restaurantId);

        Optional<Restaurant> currentRestaurant = restaurantService.findById(restaurantId);
        Optional<Dish> currentDish = dishService.findById(dishId);

        if (!currentRestaurant.isPresent()) {
            System.out.println("Restaurant with id " + restaurantId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!currentDish.isPresent()) {
            System.out.println("Dish with id " + restaurantId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Restaurant updatedRestaurant = currentRestaurant.get();
        System.out.println("OLDONE" + updatedRestaurant);
        updatedRestaurant.removeFromMenu(currentDish.get().getId());
        System.out.println("NEWONE" + updatedRestaurant);

        restaurantService.updateRestaurant(updatedRestaurant);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") String id) {
        System.out.println("Fetching & Deleting Restaurant with id " + id);

        Optional<Restaurant> restaurant = restaurantService.findById(id);
        if (!restaurant.isPresent()) {
            System.out.println("Unable to delete. Restaurant with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        restaurantService.deleteRestaurantById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Restaurant> deleteAllRestaurants() {
        System.out.println("Deleting All Restaurants");

        restaurantService.deleteAllRestaurants();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
