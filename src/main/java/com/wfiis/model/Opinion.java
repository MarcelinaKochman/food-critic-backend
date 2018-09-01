package com.wfiis.model;

import org.springframework.data.annotation.Id;

public class Opinion {
    @Id
    private String id;
    private String dishRefId;
    private String userRefId;
    private int rate;
    private String review;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishRefId() {
        return dishRefId;
    }

    public void setDishRefId(String dishRefId) {
        this.dishRefId = dishRefId;
    }

    public String getUserRefId() {
        return userRefId;
    }

    public void setUserRefId(String userRefId) {
        this.userRefId = userRefId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
