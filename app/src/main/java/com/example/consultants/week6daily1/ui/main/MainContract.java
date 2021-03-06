package com.example.consultants.week6daily1.ui.main;

import com.example.consultants.week6daily1.model.MyPlace;
import com.example.consultants.week6daily1.ui.base.BasePresenter;
import com.example.consultants.week6daily1.ui.base.BaseView;

import java.util.ArrayList;

public interface MainContract {
    interface View extends BaseView {

        void onNearbyPlaces(ArrayList<MyPlace> placesList);
    }

    interface Presenter extends BasePresenter<View> {

        void getNearbyPlaces(String latLng, String type);
    }
}
