package com.rtxschool.zombies;

import static android.view.View.VISIBLE;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rtxschool.zombies.databinding.FragmentSecond2Binding;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Second2Fragment extends Fragment implements OnMapReadyCallback {
    private FragmentSecond2Binding binding;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private LocationRequest mLocationRequest;

    int request_status = 0;  //1 = request approved, 0 = not
    private MapView mMapView;
    private GoogleMap g_mehp = null;

    LatLng cur_loc = null;

    int mehp_to_loc = 0;

    int got_cameras = 0;

    Marker cur_mrk = null;


    @Override
    public void onCreate(Bundle content
    ) {
        super.onCreate(content);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecond2Binding.inflate(inflater, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            request_status = 1;
        } else if (shouldShowRequestPermissionRationale("SHould you choose")
        ) {
            String msg = "why not";
            Toast.makeText
                    (getActivity(),
                            msg, Toast.LENGTH_SHORT).show();
        } else {
            // You can directly ask for the permission.
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}
                    , 1);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle content
    ) {
        super.onActivityCreated(content);
        mMapView = binding.theMehp;

        mMapView.onCreate(content);

        mMapView.onResume();

        mMapView.
                getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
/*
    @Override
    public void onResume() {
        int request_status = 0;  //1 = request approved, 0 = not

        cur_loc = null;

        mehp_to_loc = 0;

        got_cameras = 0;

        cur_mrk = null;

        mMapView.
                getMapAsync(this);
    }
*/
    @Override
    public void onRequestPermissionsResult
            (int requestCode,
             @NonNull String[] permissions,
             @NonNull int[] res
            ) {
        if (res[0] == 0
        ) {
            request_status = 1;
            refresh_request();
        } else {
            gps_issues();
        }
    }

    void refresh_request() {
        if (request_status == 1
        ) {

            startLocationUpdates();
            getLastLocation();

        }
    }


    @Override
    public void onMapReady(GoogleMap cur_mehp
    ) {
        if (cur_mehp == null
        )
            return;


        g_mehp = cur_mehp;

        g_mehp.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mrk) {
                if (mrk.getTag() == null
                )
                    return !true;

                Object tag = mrk.getTag();

                int cam = (int) tag;

                Bundle b = new Bundle();

                b.putInt("c", cam
                );

                NavHostFragment.findNavController(Second2Fragment.this
                )
                        .navigate(R.id.cartog_to_street, b
                        );

                return !true;

            }
        });

        if (cur_loc == null
        ) {
        } else if (mehp_to_loc == 0
        ) {
            mehp_to_loc =
                    1;

            g_mehp.moveCamera(CameraUpdateFactory.newLatLng(cur_loc)
            );

            MarkerOptions opts =
                    new
                            MarkerOptions()
                            .position(cur_loc)
                            .title("Your current"
                            )
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                            )
                            .zIndex(1.0f);
            cur_mrk =
                    g_mehp.addMarker(opts);
        }

        if (got_cameras == 0
        ) {
            got_cameras = 1;

            cameras_p cam_proc = new cameras_p() {
                @Override
                public void processed(ArrayList<cam_p> list) {
                    cameras_to_mrks(list);
                }
            };

            cam_proc.get_list(getActivity()
            );
        }
    }

    void cameras_to_mrks
            (ArrayList<cam_p> list) {

        for (int i = 0; i < list.size(); i++
        ) {
            cam_p cur_cam = list.get(i);
            if (cur_cam.type.toLowerCase().equals("sdot")
            )
            {
            LatLng cur = new LatLng(cur_cam.coor_x, cur_cam.coor_y
            );

            MarkerOptions opts =
                    new
                            MarkerOptions()
                            .position(cur)
                            .title(list.get(i).nomencl
                            )
                            .zIndex(0.0f);

            Marker cur_mrk =
                    g_mehp.addMarker(opts);

            cur_mrk.setTag(i
            );
            }
        }
    }

    void gps_issues() {
        binding.contCargotLocateIssues.setVisibility(VISIBLE);
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity()
        );
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient
                (getActivity()
                )
                .requestLocationUpdates(mLocationRequest, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                // do work here
                                onLocationChanged(locationResult.getLastLocation());
                            }
                        },
                        Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText
                (getActivity(),
                        msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        cur_loc =
                new LatLng(location.getLatitude(), location.getLongitude());
        onMapReady(g_mehp
        );

    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(getActivity()
        );
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }
}