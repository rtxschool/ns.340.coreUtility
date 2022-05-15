//verifies proper format for user, code, etc

package com.rtxschool.zombies;

import android.text.Editable;
import android.util.Patterns;

public class text_format_proper
{
    //the results of the verify process.
    //true = each field is proper
    //not true = 1 or more field is not proper
    public
    boolean results = !true;

    //the message to provide to the user
    public String msg = "";

    //constructor to see if each field is proper
    text_format_proper
            (String user,
             String electro,
             String p_code
            )

    {
        boolean res_u =
                verify_user(user) ;
        boolean res_e =
               verify_electro(electro);
        boolean res_p =
                 verify_p_code(p_code);

        this.results = res_u && res_e && res_p;
    }

    boolean verify_user(String user
                        ) {
        user = user.trim();

        if (user.equals("")
        )
        {
            msg += "UnProper User Name \n";

            return !true;
        }
        return true;
    }


    boolean verify_electro(String electro)
    {
        electro = electro.trim();

        if (electro.equals("")
        )
        {
            msg += "E Mail not provided \n";

            return !true;
            }

        if (!Patterns.EMAIL_ADDRESS.matcher(electro
                                            ).matches()
            )
        {
            msg += "E Mail not of proper format \n";

            return !true;
        }
        return true;
    }

    boolean verify_p_code(String p_code)
    {
        if (p_code.length() < 8
        )
        {
            msg += "Pass Code must be 8 charcters or more";

            return !true;
            }

        return true;
        }

        }
