package com.example.consultants.week6daily1.client;

import android.util.Log;

import com.example.consultants.week6daily1.model.placesdata.PlacesResponse;
import com.example.consultants.week6daily1.utils.NetworkHelper;
import com.example.consultants.week6daily1.utils.PlacesCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpHelper {
    private static final String TAG = OkhttpHelper.class.getSimpleName() + "_TAG";

    OkHttpClient client;
    private Request request;

    public OkhttpHelper(String location, String type) {

        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=
        //-33.8670522,151.1957362&radius=1500&type=restaurant&key=
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(NetworkHelper.BASE_URL)
                .addPathSegment("maps")
                .addPathSegment("api")
                .addPathSegment("place")
                .addPathSegment("nearbysearch")
                .addPathSegment("json")
                .addQueryParameter("location", location)
                .addQueryParameter("radius", "1500")
                .addQueryParameter("type", type)
                .addQueryParameter("key", NetworkHelper.API_KEY)
                .build();

        Log.d(TAG, "OkhttpHelper: " + url.toString());

        client = new OkHttpClient.Builder()
                .build();

        request = new Request.Builder()
                .url(url)
                .build();

    }

    public void enqueue(final PlacesCallback placesCall) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                placesCall.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                PlacesResponse placesResponse = gson.fromJson(response.body().string(), PlacesResponse.class);

                Log.d(TAG, "onResponse: num of places: " + placesResponse.getResults().size());

                placesCall.onSuccess(placesResponse);
            }
        });

    }
}
