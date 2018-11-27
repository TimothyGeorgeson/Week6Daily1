package com.example.consultants.week6daily1.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.consultants.week6daily1.R;
import com.example.consultants.week6daily1.model.MyPlace;
import com.example.consultants.week6daily1.ui.fragments.MapViewFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    MainPresenter presenter;
    RecyclerView rvPlaces;
    private FusedLocationProviderClient mFusedLocationClient;
    FragmentManager fm = getSupportFragmentManager();
    //default lat long value for atlanta, will get overridden if getLastLocation is successful
    private String latLng = "33.749,-84.388";

    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    public static final int ALL_PERMISSIONS_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permissions to request
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        //initialize presenter and recyclerview
        presenter = new MainPresenter();
        rvPlaces = findViewById(R.id.rvPlaces);

        getLocationAndPlaces("restaurant");
    }

    private void getLocationAndPlaces(final String type) {
        //initialize fused location client, used to get device location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: no permission");
            return;
        }
        Log.d(TAG, "onCreate: got past permissions");
        //once permissions are OK (I had to run app twice initially to get here)
        //get last location, and call get nearby places
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d(TAG, "onSuccess: " + location.getLatitude() + "," + location.getLongitude());
                        latLng = location.getLatitude() + "," + location.getLongitude();
                        //call method to get nearby places (type restaurant initially)
                        presenter.getNearbyPlaces(latLng, type);
                    }
                });
    }

    @Override
    public void onNearbyPlaces(final List<MyPlace> placeList) {
        //this came from onSuccess callback from the network thread
        //so needed to specify that this runs on UI thread to update list views
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(placeList);
                rvPlaces.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //tried setting a horizontal line separator, but it's not showing up when I run the app
                DividerItemDecoration itemDecor = new DividerItemDecoration(rvPlaces.getContext(),
                        DividerItemDecoration.HORIZONTAL);
                rvPlaces.addItemDecoration(itemDecor);
                rvPlaces.setAdapter(adapter);

                Bundle bundle = new Bundle();
                MapViewFragment mapViewFragment = new MapViewFragment();
                mapViewFragment.setArguments(bundle);

                fm.beginTransaction().replace(R.id.mapHolder, mapViewFragment).commit();
            }
        });
    }

    //puts together permission array
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, which adds settings button
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //show settings when clicked
        if (item.getItemId() == R.id.action_settings) {
            Log.d(TAG, "onOptionsItemSelected: Settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("These permissions are mandatory to get your location.  You need to allow them.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected
                                                        .toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel", null).create().show();

                            return;
                        }
                    }
                }

                break;
        }
    }

    @Override
    public void showError(String error) {
        Log.d(TAG, "showError: " + error);
    }
}
