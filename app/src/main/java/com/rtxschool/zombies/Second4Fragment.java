package com.rtxschool.zombies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.FragmentSecond4Binding;

public class Second4Fragment extends Fragment {

    private FragmentSecond4Binding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecond4Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}