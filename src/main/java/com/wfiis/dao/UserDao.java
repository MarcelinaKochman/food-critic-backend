package com.wfiis.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wfiis.model.User;

public interface UserDao extends MongoRepository<User, String> {

    User findByLogin(String name);

}
