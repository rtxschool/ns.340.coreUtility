package com.rtxschool.zombies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.ZombieSpecificFragBinding;

import java.io.InputStream;

public class zombieSpecificFrag
        extends Fragment {

    private ZombieSpecificFragBinding
            cur_context;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cur_context = null;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        cur_context
                = ZombieSpecificFragBinding.
                inflate(inflater, container, false);
        return cur_context.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String r = getArguments().getString("movie"
        );

        zombie_p cur = zombie_list.get_by_movie(r);

        if (cur == null
        )
            return;

        cur_context.txtLoc.setText(cur.nomencl
        );

        cur_context.txtPublished.setText(cur.published
        );

        cur_context.txtManaged.setText(cur.managed_by
        );

        cur_context.txtSummari.setText(cur.summary
        );

        ImageView iv = cur_context.imgZombie;

        new get_net_img(iv).execute(cur.img_url
        );
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