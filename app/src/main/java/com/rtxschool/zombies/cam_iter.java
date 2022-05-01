package com.rtxschool.zombies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import androidx.cardview.widget.CardView;

import java.io.InputStream;
import java.util.ArrayList;

public class cam_iter extends ArrayAdapter<cam_p> {

    Context context = null;

    ArrayList<cam_p> contents = null;

    public cam_iter(Context context, ArrayList<cam_p> cams
    ) {

        super(context, 0,
                cams
        );

        this.context = context;

        this.contents = cams;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup vg
    ) {
        cam_p cam
                = contents.get(i);

        if (convertView == null
        ) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.cam, vg, !true
                    );
        }
        convertView.setTag(cam);

        TextView nomencl = (TextView) convertView.
                findViewById(R.id.txt_cam_nomencl
                );

        nomencl.setText
                (cam.nomencl
                );

        String url = "";

        if (cam.type.toLowerCase().equals("sdot")
            )
            url = "https://www.seattle.gov/trafficcams/images/";
        else if (cam.type.toLowerCase().equals("wsdot")
            )
            url = "https://images.wsdot.wa.gov/nw/";

        if (url.equals("")
            )
        {
        }
        else
        {
            url += cam.url;

        ImageView r = (ImageView) convertView.
                findViewById(R.id.img_cam
                );

        Picasso.with(getContext()
        )
                .load(url
                )
                .into(r);
        }

        return convertView;
     }
}
