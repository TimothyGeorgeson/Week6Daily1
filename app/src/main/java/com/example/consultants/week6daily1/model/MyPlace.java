package com.example.consultants.week6daily1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPlace implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(street);
        dest.writeString(rating);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public MyPlace createFromParcel(Parcel in) {
            return new MyPlace(in);
        }

        public MyPlace[] newArray(int size) {
            return new MyPlace[size];
        }
    };

    // "De-parcel object
    public MyPlace(Parcel in) {
        name = in.readString();
        street = in.readString();
        rating = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }
}
