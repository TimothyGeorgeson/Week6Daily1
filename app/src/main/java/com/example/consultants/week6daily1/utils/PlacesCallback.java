package com.example.consultants.week6daily1.utils;

import com.example.consultants.week6daily1.model.placesdata.PlacesResponse;

public interface PlacesCallback {
    void onSuccess(PlacesResponse placesResponse);

    void onFailure(String error);
}
