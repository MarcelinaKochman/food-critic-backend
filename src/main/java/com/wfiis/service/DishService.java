package com.wfiis.service;

import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfiis.dao.DishDao;
import com.wfiis.model.Dish;

@Service
public class DishService {

    private final DishDao dishDao;

    @Autowired
    public DishService(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    public List<Dish> findAllDishs() {
        return dishDao.findAll();
    }

    public Optional<Dish> findById(String id) {
        return dishDao.findById(id);
    }

    public Optional<Dish> findByName(String name) {
        return of(dishDao.findByName(name));
    }

    public Dish saveDish(Dish restaurant) {
        return dishDao.save(restaurant);
    }

    public Dish updateDish(Dish restaurant) {
        return dishDao.save(restaurant);
    }

    public void deleteDishById(String id) {
        dishDao.deleteById(id);
    }

    public void deleteAllDishs() {
        dishDao.deleteAll();
    }

}
