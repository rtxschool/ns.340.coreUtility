package com.rtxschool.zombies;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.rtxschool.zombies.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding cur_context;

    boolean is_net_found() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE
                );
        NetworkInfo i = cm.getActiveNetworkInfo();

        return (i != null && i.isConnected());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        cur_context
                = FragmentFirstBinding.inflate(inflater, container, false);
        return cur_context.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cur_context.cmdSelfStat.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.from_prim_to_stat);
                    }

                });

        cur_context.cmdZombies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.from_log_to_movies);


            }

        });

        cur_context.cmdStreet
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(R.id.from_prim_to_cam
                                );
                    }

                });

        cur_context.cmdCartog
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int to = R.id.from_prim_to_cartog;

                        if (!is_net_found()
                        )
                            to = R.id.prim_to_cam_street_issues;
                        cartog_static.bonus = 0;
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(to
                                );
                    }
                });


        cur_context.cmdCartogBonus
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int to = R.id.from_prim_to_cartog;

                        if (!is_net_found()
                        )
                            to = R.id.prim_to_cam_street_issues;
                        cartog_static.bonus = 1;
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(to
                                );
                    }
                });

        cur_context.txtCmdTsk1.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int status = VISIBLE;

                        if (cur_context.crdSelfStat
                                .getVisibility() == VISIBLE
                        )
                            status = GONE;
                        hide_tsks();
                        cur_context.crdSelfStat
                                .setVisibility(status);
                    }
                });

        cur_context.crdTsk1.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdSelfStat
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk1();
                    }
                });


        cur_context.txtCmdTsk2.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_vis_status_crdTsk2();
                    }
                });

        cur_context.crdTsk2.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdBtns
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk2();
                    }
                });

        cur_context.txtCmdTsk3.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_vis_status_crdTsk3();
                    }
                });

        cur_context.crdTsk3.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdZombies
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk3();
                    }
                });
        cur_context.txtCmdTsk4.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_vis_status_crdTsk4();
                    }
                });

        cur_context.crdTsk4.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdStreet
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk4();
                    }
                });

        cur_context.txtCmdTsk42.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_vis_status_crdTsk4_2();
                    }
                });

        cur_context.crdTsk42.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdStreet
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk4_2();
                    }
                });


        cur_context.txtCmdTsk420.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        set_vis_status_crdTsk4_2_0();
                    }
                });

        cur_context.crdTsk420.
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cur_context.crdStreet
                                .getVisibility() == GONE
                        )
                            set_vis_status_crdTsk4_2_0();
                    }
                });
    }

    void set_vis_status_crdTsk1() {
        int status = VISIBLE;

        if (cur_context.crdSelfStat
                .getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.crdSelfStat
                .setVisibility(status);
    }

    void set_vis_status_crdTsk2() {
        int status = VISIBLE;
        if (cur_context.logPrim.getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.logPrim.setVisibility(status);
        cur_context.crdBtns.setVisibility(status);
    }

    void set_vis_status_crdTsk3() {
        int status = VISIBLE;

        if (cur_context.crdZombies
                .getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.crdZombies
                .setVisibility(status);
    }

    void set_vis_status_crdTsk4() {
        int status = VISIBLE;

        if (cur_context.crdStreet.
                getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.crdStreet
                .setVisibility(status);
    }

    void set_vis_status_crdTsk4_2() {
        int status = VISIBLE;

        if (cur_context.crdCartog.
                getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.crdCartog
                .setVisibility(status);
    }

    void set_vis_status_crdTsk4_2_0() {
        int status = VISIBLE;

        if (cur_context.crdCartogBonus.
                getVisibility() == VISIBLE
        )
            status = GONE;
        hide_tsks();
        cur_context.crdCartogBonus
                .setVisibility(status);
    }

    void hide_tsks() {
        cur_context.logPrim.setVisibility(GONE);
        cur_context.crdBtns.setVisibility(GONE);
        cur_context.crdZombies
                .setVisibility(GONE);
        cur_context.crdSelfStat
                .setVisibility(GONE);
        cur_context.crdStreet.setVisibility(GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cur_context = null;
    }
}