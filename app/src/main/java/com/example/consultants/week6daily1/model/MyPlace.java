package com.example.consultants.week6daily1.model;

public class MyPlace {

    String name;
    String street;
    String rating;
    Double lat;
    Double lng;

    public MyPlace(String name, String street, String rating, Double lat, Double lng) {
        this.name = name;
        this.street = street;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
