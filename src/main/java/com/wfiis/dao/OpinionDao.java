package com.wfiis.dao;

import java.util.List;

import com.wfiis.model.Opinion;
import com.wfiis.model.Restaurant;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpinionDao extends MongoRepository<Opinion, String> {
    List<Opinion> findByDishRefId(String dishRefId);
}
