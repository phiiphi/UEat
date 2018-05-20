package com.example.phiiphiroberts.ueat.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.phiiphiroberts.ueat.Model.User;

/**
 * Created by phiiphiroberts on 30/03/2018.
 */

public class Common {
    public static User currentUser;

    public static final String DELETE = "Delete";
    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager !=null)
        {
            NetworkInfo[] info =  connectivityManager.getAllNetworkInfo();
            if (info !=null)
            {
                for (int i=0; i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }

        }
        return false;
    }

}
