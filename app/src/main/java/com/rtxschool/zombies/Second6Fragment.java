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

import com.rtxschool.zombies.databinding.FragmentSecond6Binding;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class Second6Fragment extends Fragment {

    private FragmentSecond6Binding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecond6Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int g = getArguments().getInt("c");

        cam_p cur =
                cameras_p.cameras.get(g);

        binding.txtCartogCamNomenc.setText(cur.nomencl
        );

        String url = "";

        if (cur.type.toLowerCase().equals("sdot")
        )
            url =
                "https://www.seattle.gov/trafficcams/images/";
        else if (cur.type.toLowerCase().equals("wsdot")
        )
            url =
                "https://images.wsdot.wa.gov/nw/";

        if (url.equals("")
        ) {
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private class get_net_img extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public get_net_img(ImageView imageView) {
            this.imageView = imageView;
            // Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                     String msg = e.getMessage();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            if (result == null)
                return;
            imageView.setImageBitmap(result);

        }
    }
}