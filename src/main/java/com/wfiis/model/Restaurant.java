package com.wfiis.model;

import javax.validation.constraints.NotNull;

import com.google.maps.model.GeocodingResult;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurant")
public class Restaurant {
    @Id
    private String id;
    @NotNull
    private String name;
    private GeocodingResult address;
    @NotNull
    private int phoneNumber;
    private String webPage;
    private String photoUrl;
    private List<String> menu = new ArrayList<>();

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

    public GeocodingResult getAddress() {
        return address;
    }

    public void setAddress(GeocodingResult address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public boolean addToMenu(String dishRefId) {
        return menu.add(dishRefId);
    }

    public boolean removeFromMenu(String dishRefId) {
        return menu.remove(dishRefId);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
