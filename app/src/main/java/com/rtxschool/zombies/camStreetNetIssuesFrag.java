//AD 340
//

package com.rtxschool.zombies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtxschool.zombies.databinding.CartogFragBinding;
import com.rtxschool.zombies.databinding.FragmentCamStreetNetIssuesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link camStreetNetIssuesFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class camStreetNetIssuesFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentCamStreetNetIssuesBinding context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public camStreetNetIssuesFrag() {
    }

    public static camStreetNetIssuesFrag newInstance(String param1, String param2) {
        camStreetNetIssuesFrag fragment = new camStreetNetIssuesFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle content
    ) {
        context = FragmentCamStreetNetIssuesBinding
                .inflate(inflater, container, false);


        return context.getRoot();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}