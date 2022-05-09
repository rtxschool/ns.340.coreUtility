package com.rtxschool.zombies;

import static android.view.View.VISIBLE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import java.util.ArrayList;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rtxschool.zombies.databinding.FragmentFirst8Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class camStreetFrag extends Fragment {

    private FragmentFirst8Binding binding;
    private RequestQueue mRequestQueue;

    cam_iter ci = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mRequestQueue = Volley.newRequestQueue(getActivity()
        );
        binding = FragmentFirst8Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ci = new cam_iter(getContext(),
                cameras_p.cameras
        );

        ListView lst = binding.lstCam;

        lst.setAdapter(ci);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        if (!is_net_found()
        )
            net_issu();
        else
            get_list();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    boolean is_net_found() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE
                );
        NetworkInfo i = cm.getActiveNetworkInfo();

        return (i != null && i.isConnected());
    }

    void get_list() {
        try {
            RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

            final String URL = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

            JsonObjectRequest req = new JsonObjectRequest(URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject content) {
                            try {
                                parseList(content
                                );
                            } catch (Exception e) {
                                e.printStackTrace();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
            mRequestQueue.add(req);

        } catch (Exception e) {

        }
    }

    void parseList(JSONObject content
    ) {
        JSONArray cameras = content.optJSONArray("Features");
        String r = "";
        for (int i = 0; i < cameras.length(); i++
        ) {
            JSONObject camera = cameras.optJSONObject(i);

            String camera_str = camera.optString("PointCoordinate"
            );

            JSONArray cur_cameras = camera.optJSONArray("Cameras"
            );

            int camera_count = cur_cameras.length();

            for (int i2 = 0; i2 < camera_count; i2++
            ) {
                cam_p cp = new cam_p();

                JSONObject cur_cam = cur_cameras.optJSONObject(i2);

                cp.nomencl = cur_cam.optString("Descripti" +
                        "on");

                cp.ID = cur_cam.optString("Id"
                );

                cp.url = cur_cam.optString("ImageUrl"
                );

                cp.type = cur_cam.optString("Type"
                );

                cameras_p.cameras.add(cp);

                String u = "rnd " + String.valueOf(i);

                ci.notifyDataSetChanged();
            }

        }
    }

    void net_issu() {
       ScrollView sv = new ScrollView(getActivity()
        );

        binding.camStreetFragCont.addView(sv);

        RelativeLayout re = new RelativeLayout(getActivity()
        );

        sv.addView(re);

        TextView txt = new TextView(getActivity()
        );

        String r = "We could not reach the network from your device.  Verify your options";

        txt.setText(r);

        txt.setTextColor(Color.parseColor("#000000"));

        re.addView(txt);

    }
}
