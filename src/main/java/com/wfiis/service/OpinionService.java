package com.wfiis.service;

import static java.util.Optional.of;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfiis.dao.OpinionDao;
import com.wfiis.model.Opinion;

@Service
public class OpinionService {

    private final OpinionDao opinionDao;

    @Autowired
    public OpinionService(OpinionDao opinionDao) {
        this.opinionDao = opinionDao;
    }

    public List<Opinion> findAllOpinions() {
        return opinionDao.findAll();
    }

    public Optional<Opinion> findById(String id) {
        return opinionDao.findById(id);
    }

    public List<Opinion> findByDishRefId(String id) {
        return opinionDao.findByDishRefId(id);
    }

    public Opinion saveOpinion(Opinion restaurant) {
        return opinionDao.save(restaurant);
    }

    public Opinion updateOpinion(Opinion restaurant) {
        return opinionDao.save(restaurant);
    }

    public void deleteOpinionById(String id) {
        opinionDao.deleteById(id);
    }

    public void deleteAllOpinions() {
        opinionDao.deleteAll();
    }

}
