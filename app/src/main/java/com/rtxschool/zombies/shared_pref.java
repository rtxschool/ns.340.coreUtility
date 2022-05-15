//manages the persistent shared prefs for firebase

package com.rtxschool.zombies;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class shared_pref
{
    String user;
    String electro;
    String p_code;
    Activity context;

    //overload to retrieve the current prefs
    shared_pref
            (Activity context
            )
    {
        SharedPreferences sp = context.getSharedPreferences("util", MODE_PRIVATE
        );

        this.context = context;

        this.user = sp.getString("user", ""
                                );

        this.electro = sp.getString("electro", ""
                                   );

        this.p_code = sp.getString("p_code", ""
                                  );
        }

        //overload to create pref
    shared_pref
            (String user,
             String electro,
             String p_code,
             Activity context
            )
    {
        this.context = context;

        this.user = user;

        this.electro = electro;

        this.p_code = p_code;
    }

    //set the prefs
    public void submit()
    {
        SharedPreferences s = context.getSharedPreferences("util", MODE_PRIVATE
                                                                  );
        SharedPreferences.Editor spe = s.edit();

        spe.putString("user", user
                         );

        spe.putString("electro", electro
                    );

        spe.putString("p_code", p_code
                      );

        spe.apply();
        }
        }