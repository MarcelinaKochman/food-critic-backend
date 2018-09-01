package com.wfiis.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class Dish {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    private String photoUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
