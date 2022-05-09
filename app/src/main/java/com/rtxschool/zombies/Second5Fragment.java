package com.rtxschool.zombies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.FragmentSecond5Binding;

public class Second5Fragment extends Fragment {

    private FragmentSecond5Binding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecond5Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}