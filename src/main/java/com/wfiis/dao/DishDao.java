package com.wfiis.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wfiis.model.Dish;

public interface DishDao extends MongoRepository<Dish, String> {

    Dish findByName(String name);

}
