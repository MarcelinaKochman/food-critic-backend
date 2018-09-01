package com.wfiis.service;

import com.wfiis.dao.RestaurantDao;
import com.wfiis.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;

    @Autowired
    public RestaurantService(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantDao.findAll();
    }

    public Optional<Restaurant> findById(String id) {
        return restaurantDao.findById(id);
    }

    public Optional<Restaurant> findByName(String name) {
        return of(restaurantDao.findByName(name));
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantDao.save(restaurant);
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        return restaurantDao.save(restaurant);
    }

    public void deleteRestaurantById(String id) {
        restaurantDao.deleteById(id);
    }

    public void deleteAllRestaurants() {
        restaurantDao.deleteAll();
    }

}
