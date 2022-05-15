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