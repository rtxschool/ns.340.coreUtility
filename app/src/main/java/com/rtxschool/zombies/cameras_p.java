package com.rtxschool.zombies;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class cameras_p {
    public static
    ArrayList<cam_p> cameras = new ArrayList<cam_p>();

    public static void create_cams(String content
    ) {
        cam_p c = new cam_p();

        c.nomencl = content;

        c.ID = content;
        c.type = content;
        c.url = content;

        cameras.add(c);
    }

    public
    void
    get_list(Activity src
                  )
    {
        try {
            RequestQueue mRequestQueue = Volley.newRequestQueue(src);

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

                String[] coords = camera_str.replace("[", ""
                )
                        .replace("]", ""
                        )
                        .split(",");

                cp.coor_x = Double.parseDouble(coords[0]
                                              );

                cp.coor_y = Double.parseDouble(coords[1]
                                             );
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
            }
        }

        processed(cameras_p.cameras
                 );
       }

    public void processed(ArrayList<cam_p> list)
    {
    }

}