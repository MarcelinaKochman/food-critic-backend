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

import com.wfiis.model.Opinion;
import com.wfiis.service.OpinionService;

@CrossOrigin
@RestController
@RequestMapping("/opinion")
public class OpinionController {

    private final OpinionService opinionService;

    @Autowired
    public OpinionController(OpinionService opinionService) {
        this.opinionService = opinionService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Opinion>> listAllOpinion() {
        List<Opinion> opinion = opinionService.findAllOpinions();
        if(opinion.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opinion, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Opinion> getOpinion(@PathVariable("id") String id) {
        System.out.println("Fetching Opinion with id " + id);
        Optional<Opinion> opinion = opinionService.findById(id);
        if (!opinion.isPresent()) {
            System.out.println("Opinion with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(opinion.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/dish/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Opinion>> getAllOpinionForDish(@PathVariable("id") String id) {
        System.out.println("Fetching Opinions for Dish with id " + id);
        List<Opinion> opinion = opinionService.findByDishRefId(id);
        if(opinion.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(opinion, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> createOpinion(@RequestBody @Valid Opinion opinion, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Opinion " + opinion.getRate() + "for Dish with id " + opinion.getDishRefId());

        Opinion createdOpinion = opinionService.saveOpinion(opinion);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/opinion/{id}").buildAndExpand(createdOpinion.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Opinion> updateOpinion(@PathVariable("id") String id, @RequestBody Opinion opinion) {
        System.out.println("Updating Opinion " + id);

        Optional<Opinion> currentOpinion = opinionService.findById(id);

        if (!currentOpinion.isPresent()) {
            System.out.println("Opinion with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Opinion updatedOpinion = currentOpinion.get();

        if (opinion.getRate() != 0) updatedOpinion.setRate(opinion.getRate());
        if (opinion.getDishRefId() != null) updatedOpinion.setDishRefId(opinion.getDishRefId());
        if (opinion.getReview() != null) updatedOpinion.setReview(opinion.getReview());
        if (opinion.getUserRefId() != null) updatedOpinion.setUserRefId(opinion.getUserRefId());

        opinionService.updateOpinion(updatedOpinion);
        return new ResponseEntity<>(updatedOpinion, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Opinion> deleteOpinion(@PathVariable("id") String id) {
        System.out.println("Fetching & Deleting Opinion with id " + id);

        Optional<Opinion> opinion = opinionService.findById(id);
        if (!opinion.isPresent()) {
            System.out.println("Unable to delete. Opinion with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        opinionService.deleteOpinionById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Opinion> deleteAllOpinions() {
        System.out.println("Deleting All Opinions");

        opinionService.deleteAllOpinions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
