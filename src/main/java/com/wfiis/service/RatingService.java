package com.wfiis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfiis.dao.OpinionDao;
import com.wfiis.dao.RestaurantDao;
import com.wfiis.model.Opinion;
import com.wfiis.model.Rating;
import com.wfiis.model.Restaurant;

@Service
public class RatingService {

    private final RestaurantDao restaurantDao;
    private final OpinionDao opinionDao;

    @Autowired
    public RatingService(OpinionDao opinionDao, RestaurantDao restaurantDao) {
        this.opinionDao = opinionDao;
        this.restaurantDao = restaurantDao;
    }

    public Rating findDishRate(String dishId) {
        List<Opinion> opinionsAboutDishe = opinionDao.findByDishRefId(dishId);

        double rate = opinionsAboutDishe.stream()
                .map(Opinion::getRate)
                .mapToDouble(a -> a)
                .average()
                .orElse(0);

        Rating rating = new Rating();
        rating.setRate(rate);
        rating.setId(dishId);

        return rating;
    }

    public Rating findRestaurantRate(String restaurantId) {

        Optional<Restaurant> restaurant = restaurantDao.findById(restaurantId);
        List<Opinion> dishesOpinions = extractDishesIds(restaurant);

        double rate = dishesOpinions.stream()
                .map(Opinion::getRate)
                .mapToDouble(a -> a)
                .average()
                .orElse(0);

        Rating rating = new Rating();
        rating.setRate(rate);
        rating.setId(restaurantId);

        return rating;
    }

    private List<Opinion> extractDishesIds(Optional<Restaurant> restaurant) {
        List<Opinion> dishesOpinions = new ArrayList<>();

        restaurant
                .map(Restaurant::getMenu)
                .orElse(new ArrayList<>())
                .forEach(e -> dishesOpinions.addAll(opinionDao.findByDishRefId(e)));

        return dishesOpinions;
    }

}
