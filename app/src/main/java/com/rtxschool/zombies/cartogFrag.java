//AD 340
//Project.. Street Camera Maps

//Requirements
//detects the user's location
//provide the map, centered to the user's coords
//provide blue marker for the user's coords
//provide red marker for each traffic camera found from the prior assignment
//provide the camera label if the marker tapped

//E.C.
//provide the geocoded of the user's coords

//Bonus
//Provide the photo of the camera if marker is tapped

package com.rtxschool.zombies;

import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.ConstraintSet.GONE;
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
import com.rtxschool.zombies.databinding.CartogFragBinding;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class cartogFrag
        extends Fragment implements OnMapReadyCallback {
    private CartogFragBinding
            binding;

    //for the locate cycle
    private long UPDATE_INTERVAL = 10 * 1000;  // 10s
    private long FASTEST_INTERVAL = 2000; // 2s

    private LocationRequest mLocationRequest;

    int request_status = 0;  //1 = request approved, 0 = not
    private MapView mMapView;
    private GoogleMap g_mehp = null;

    LatLng cur_loc = null;

    int mehp_to_loc = 0;

    int got_cameras = 0;

    Marker cur_mrk = null;

    LatLng prev_cam_loc = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle content
    ) {
        binding = CartogFragBinding
                .inflate(inflater, container, false);

        //is this app permitted to get location?
        //If it is, then set the request status to 1
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            request_status = 1;
            refresh_request();
        }
        //else if ???????
        else if (shouldShowRequestPermissionRationale("SHould you choose")
        ) {
        }
        //else ask to be permitted
        else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}
                    , 1);

        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cartogReturn
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.contCargotLocateIssues.setVisibility(GONE);
                    }

                });
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        cartog_static.prev_cam_loc = g_mehp.getCameraPosition().target;
    }

    //tie the mapview to the map object
    @Override
    public void onActivityCreated(Bundle content
    ) {
        mehp_to_loc = 0;
        super.onActivityCreated(content);
        mMapView = binding.theMehp;

        mMapView.onCreate(content);

        mMapView.onResume();

        //create the map
        mMapView.
                getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //1nce the user replies to the permit locate request..
    @Override
    public void onRequestPermissionsResult
    (int requestCode,
     @NonNull String[] permissions,
     @NonNull int[] res
    ) {
        //if permitted the set the request status to 1 & run the appropriate code
        if (res[0] == 0
        ) {
            request_status = 1;
            refresh_request();
        }
        //if not, the show the error message.
        else {
            gps_issues();
        }
    }

    //start the locate process & get the most recent
    void refresh_request() {
        if (request_status == 1
        ) {

            startLocationUpdates();
            getLastLocation();

        }
    }

    GoogleMap tmp_mehp = null;

    //1nce the map is ready then
    //   * set the tap event for the markers
    //   * create the marker for the current locate if found
    //   * get the list of cameras
    @Override
    public void onMapReady(GoogleMap cur_mehp
    ) {
        if (cur_mehp == null
        )
            return;  //map is not ready yet

        g_mehp = cur_mehp;

        if (tmp_mehp == null
        )
            tmp_mehp = g_mehp;

        if (g_mehp != tmp_mehp) {
            mehp_to_loc = 0;

            tmp_mehp = g_mehp;

            got_cameras = 0;

        }

        //set marker tap events
        g_mehp.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mrk) {
                if (mrk.getTag() == null
                )
                    return !true;

                if (cartog_static.bonus == 0
                )
                    return !true;

                Object tag = mrk.getTag();

                int cam = (int) tag;

                Bundle b = new Bundle();

                b.putInt("c", cam
                );

                NavHostFragment.findNavController(cartogFrag.this
                )
                        .navigate(R.id.cartog_to_street, b
                        );

                cartog_static.zoom = g_mehp.getCameraPosition().zoom;

                cartog_static.prev_cam_loc = g_mehp.getCameraPosition().target;


                return !true;

            }
        });

        LatLng null_is = new LatLng(-42.0, -151.0);

        if (cur_mehp.getCameraPosition().equals(null_is)
        ) {
            mehp_to_loc = 0;
        }
        //if there is no locate ..
        if (cur_loc == null
        ) {
        }

        //if there is locate
        else if (mehp_to_loc == 0
        ) {
            mehp_to_loc =
                    1;

            if (cartog_static.prev_cam_loc == null
            )
                //move to current locate
                g_mehp.moveCamera(CameraUpdateFactory.newLatLng(cur_loc)
                );
            else

                g_mehp.animateCamera(CameraUpdateFactory.newLatLngZoom(cartog_static.prev_cam_loc,
                        cartog_static.zoom),
                        2, null);

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

        //if the cameras as not retrieved yet then retrieve them
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

    //the cameras are retireved.  show them.
    void cameras_to_mrks
    (ArrayList<cam_p> list) {

        for (int i = 0; i < list.size(); i++
        ) {
            cam_p cur_cam = list.get(i);
            if (!
                    (cur_cam.type.toLowerCase().equals("sdot") ||
                            cartog_static.bonus == 0
                    )
            ) {
            } else {
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

                if (cur_loc == null
                ) {
                    g_mehp.animateCamera(CameraUpdateFactory.newLatLngZoom(cur,
                            10),
                            2, null);
                }
            }
        }
    }

    //show the locate not permitted issue
    void gps_issues() {
        binding.contCargotLocateIssues.setVisibility(VISIBLE);
    }

    //start the locate cycle
    protected void startLocationUpdates() {

        // create the request to recieve updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // are the location settings proper?
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity()
        );
        settingsClient.checkLocationSettings(locationSettingsRequest);

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

    //if there is locate change from prior locate then,
    // TODO: utilizeis to move the marker & show the user's current locate
    public void onLocationChanged(Location location) {

        cur_loc =
                new LatLng(location.getLatitude(), location.getLongitude());
        cartog_static.the_cur_loc = cur_loc;
        onMapReady(g_mehp
        );

        binding.txtCurLt.setText("lt.. " + cur_loc.latitude
        );

        binding.txtCurLg.setText("lg.. " + cur_loc.longitude
        );
    }

    //look to see the most recent locate
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