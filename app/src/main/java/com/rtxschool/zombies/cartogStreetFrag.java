package com.rtxschool.zombies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.CartogStreetFragBinding;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class cartogStreetFrag
        extends Fragment {

    private CartogStreetFragBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CartogStreetFragBinding
                .inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //this is where the cam_p is found from the cameras list
        int g = getArguments().getInt("c");

        cam_p cur =
                cameras_p.cameras.get(g);

        //text statement about the camera
        binding.txtCartogCamNomenc.setText(cur.nomencl
        );

        String url = "";

        //get the proper site from the camera type
        if (cur.type.toLowerCase().equals("sdot")
        )
            url =
                    "https://www.seattle.gov/trafficcams/images/";
        else if (cur.type.toLowerCase().equals("wsdot")
        )
            url =
                    "https://images.wsdot.wa.gov/nw/";

        //if the url is not good then
        if (url.equals("")
        ) {
        }
        //if the url is good then
        else {

            url += cur.url;

            ImageView r = binding.imgCartogCam;

            Picasso.with(getContext()
            )
                    .load(url
                    )
                    .into(r);


        }
        }
        }