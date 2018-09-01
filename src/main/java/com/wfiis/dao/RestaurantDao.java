package com.wfiis.dao;

import com.wfiis.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantDao extends MongoRepository<Restaurant, String> {

    Restaurant findByName(String name);

}
