//AD 340
//Assignment 6 | Persistent & Firebase

//Requirements
//Create function to verify user's text
//..see text_format_proper
//populate text fields with prior persistent values
//..see persistUser
//verify Firebase creds
//logon to Firebase
//Log on to Firebase
//start Firebase activity

package com.rtxschool.zombies;

import static android.view.View.GONE; import static android.view.View.VISIBLE;

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
    ) {

        context = PersistUserFragBinding
                .inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        return context.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context.cmdPersistReturn
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        return_();
                    }
                });

        context.cmdPersistLog
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOn();
                    }
                });
        //get the persisetent share preferences
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

    //return from the issues screen
    void return_()

    {
        context.persistIssues.setVisibility(GONE);

        context.persistPrim.setVisibility(VISIBLE);

        context.txtMsgPersist.setText("");
    }

    //communicate the issue
    void issues(String msg
                )
    {
        context.persistIssues.setVisibility(VISIBLE);

        context.persistPrim.setVisibility(GONE);

        context.txtMsgPersist.setText(msg);
   }


    //verify that the creds are proper.
    //if they are then
    //      store to the shared prefs
    //      try to verify creds to firebase
    //if not then show the error to the user
    private void logOn() {
        text_format_proper tfp = new text_format_proper(context.txtNomencl.getText().toString(),
                context.txtElectrM.getText().toString(),
                context.txtPCode.getText().toString()
        );

        if (!tfp.results
        ) {
            issues(tfp.msg);
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

    //take the client & try to get to Firebase
    private void go_firebase(String client, String electro, String p_code
    ) {
        try {
            mAuth.signInWithEmailAndPassword(electro, p_code
            )
                    .addOnCompleteListener(getActivity(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
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
                                                                                                                  task) {
                                                                                       if (task.isSuccessful()) {
                                                                                           int to = R.id.prim_to_firebase;

                                                                                           startActivity(new
                                                                                                   Intent(getActivity(),
                                                                                                   FirebaseActivity.class));

                                                                                       }
                                                                                   }
                                                                               });
                                    } else {
                                        String msg = "Could not log on to Firebase";

                                        issues(msg);

                                    }
                                }
                            });
        }
        catch (Exception r)
        {
            String msg = "Could not log on to Firebase";

            issues(msg);
         }
        }
        }