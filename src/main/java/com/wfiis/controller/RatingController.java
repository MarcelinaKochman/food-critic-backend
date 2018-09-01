package com.wfiis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wfiis.model.Rating;
import com.wfiis.model.Restaurant;
import com.wfiis.service.RatingService;

@CrossOrigin
@RestController
@RequestMapping("/rate")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(value = "/dish/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rating> getDishRate(@PathVariable("id") String id) {
        System.out.println("Calculating rate of Dish with id " + id);
        Rating rating = ratingService.findDishRate(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rating> getRestaurantRate(@PathVariable("id") String id) {
        System.out.println("Calculating rate of Dish with id " + id);
        Rating rating = ratingService.findRestaurantRate(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

}
