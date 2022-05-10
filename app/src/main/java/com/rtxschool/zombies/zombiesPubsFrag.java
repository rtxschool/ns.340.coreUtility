package com.rtxschool.zombies;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.ZombiesPubsFragBinding;

public class zombiesPubsFrag
        extends Fragment {

    private ZombiesPubsFragBinding
            binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ZombiesPubsFragBinding
                .inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        zombie_iter zi = new zombie_iter(getContext()
                , zombie_list.zombie_coll
        );

        ListView lst = binding.lst;

        lst.setAdapter(zi);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                zombie_p z = (zombie_p) view.getTag();

                Bundle b = new Bundle();
                b.putString("movie", z.nomencl
                );
                NavHostFragment.findNavController(zombiesPubsFrag.this
                                                  )
                        .navigate(R.id.from_movies_to_summary, b
                        );
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}