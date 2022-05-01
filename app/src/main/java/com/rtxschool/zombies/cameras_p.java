package com.rtxschool.zombies;

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
}

