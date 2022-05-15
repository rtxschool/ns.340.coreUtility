package com.rtxschool.zombies;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.rtxschool.zombies.databinding.PersistUserFragBinding;

import java.util.concurrent.Executor;

public class persistUserFrag
        extends Fragment {

    private PersistUserFragBinding context;

    FirebaseAuth mAuth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {

        context = PersistUserFragBinding
                .inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        return context.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context.cmdPersistLog
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOn();
                    }


                });
        get_shared_prefs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context = null;

    }

    //retreive the values from the current shared pref, & show them
    private void get_shared_prefs() {
        shared_pref sp = new shared_pref(getActivity()
        );

        context.txtNomencl.setText(sp.user);

        context.txtElectrM.setText(sp.electro);

        context.txtPCode.setText(sp.p_code);
    }

    //verify that the creds are proper.
    //if they are then
    //      store to the shared prefs
    //      try to verify creds to firebase
    //if not then show the error to the user
    private void logOn() {
        /*
        NavHostFragment.findNavController(persistUserFrag.this
        )
                .navigate(R.id.from_prim_to_cartog);
        if (true) return;
         */
        //TODO: remove this passcode
                text_format_proper tfp = new text_format_proper(context.txtNomencl.getText().toString(),
                context.txtElectrM.getText().toString(),
                context.txtPCode.getText().toString()
        );

        if (!tfp.results
        ) {
            //TODO: show error
            return;
        }

        shared_pref sp = new
                shared_pref
                (context.txtNomencl.getText().toString(),
                        context.txtElectrM.getText().toString(),
                        context.txtPCode.getText().toString(),
                        getActivity()
                );

        sp.submit();

        go_firebase(sp.user, sp.electro, sp.p_code
        );
    }

    private void go_firebase(String client, String electro, String p_code
    )
        {
        try {
            Log.d("FIREBASE", "logOn");
            // 3 - sign into Firebase

            mAuth.signInWithEmailAndPassword(electro, p_code
            )
                    .addOnCompleteListener(  getActivity(),
                             new OnCompleteListener<AuthResult>()
                          {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d("FIREBASE", "signIn:onComplete:" +
                                    task.isSuccessful());
                            if (task.isSuccessful()) {
                                // update profile. displayname is the value entered from UI

                                FirebaseUser user =
                                        FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new
                                        UserProfileChangeRequest.Builder()
                                        .setDisplayName(client)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new
                                                                       OnCompleteListener<Void>() {
                                                                           @Override
                                                                           public void onComplete(@NonNull Task<Void>
                                                                                                          task)
                                                                           {
                                                                               if (task.isSuccessful()) {
                                                                                   Log.d("FIREBASE", "User profile updated.");
                                                                                   // Go to FirebaseActivity

//This seems to close the entire tool, yet if remarked, the toast at L80 is fine.
                                                                                   startActivity(new
                                                                                           Intent(getActivity(),
                                                                                            FirebaseActivity.class));

                                                                                   // Go to FirebaseActivit  y
                                                                       /*
                                                                                   NavHostFragment.findNavController(persistUserFrag.this
                                                                                                                      )
                                                                                           .navigate(R.id.prim_to_persist_users
                                                                                           );
                                                                                   return;
                                                                                   */

                                                                         //        /
                                                                         //        startActivity(new
                                                                         //                Intent(getActivity(),
                                                                         //                FirebaseActivity.class));
                                                                         //
                                                                         //
                                                                                   Toast.makeText(getActivity(), "Here", LENGTH_SHORT).show();
                                                                               }
                                                                           }
                                                                       });

                                /*
            NavHostFragment.findNavController(persistUserFrag.this
            )
                    .navigate(R.id.prim_to_persist_users
                    );
           */
        } else {
                                Log.d("FIREBASE", "sign-in failed");
                                Toast.makeText(getActivity(),
                                        "Sign In Failed",
                                        LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception r) {
            String u = r.getMessage();  Toast.makeText(getActivity(), u, LENGTH_SHORT).show();
        }

    }
}