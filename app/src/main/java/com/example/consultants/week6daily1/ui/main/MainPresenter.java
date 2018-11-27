package com.example.consultants.week6daily1.ui.main;

import android.content.Context;
import android.util.Log;

import com.example.consultants.week6daily1.client.OkhttpHelper;
import com.example.consultants.week6daily1.model.MyPlace;
import com.example.consultants.week6daily1.model.placesdata.PlacesResponse;
import com.example.consultants.week6daily1.utils.PlacesCallback;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    public static final String TAG = MainPresenter.class.getSimpleName() + "_TAG";

    Context context;
    MainContract.View view;
    OkhttpHelper okhttpHelper;

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void getNearbyPlaces(String latLng, String type) {

        okhttpHelper = new OkhttpHelper(latLng, type);

        okhttpHelper.enqueue(new PlacesCallback() {
            @Override
            public void onSuccess(PlacesResponse placesResponse) {
                Log.d(TAG, "onSuccess: num of places: " + placesResponse.getResults().size());

                ArrayList<MyPlace> placeList = new ArrayList<>();

                for (int i = 0; i < placesResponse.getResults().size(); i++) {
                    String name = placesResponse.getResults().get(i).getName();
                    String street = placesResponse.getResults().get(i).getVicinity();
                    String rating = Double.toString(placesResponse.getResults().get(i).getRating());
                    Double lat = placesResponse.getResults().get(i).getGeometry().getLocation().getLat();
                    Double lng = placesResponse.getResults().get(i).getGeometry().getLocation().getLng();
                    MyPlace place = new MyPlace(name, street, rating, lat, lng);

                    placeList.add(place);
                }

                view.onNearbyPlaces(placeList);
            }

            @Override
            public void onFailure(String error) {
                Log.d(TAG, "onFailure: " + error);
            }
        });
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }
}
