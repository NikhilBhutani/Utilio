package com.github.nikhilbhutani.utilio.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nikhil Bhutani on 11/9/2016.
 */

public class UtilioReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null) {


            /*
            if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){

                Intent localIntentUsrPrsnt = new Intent(context, UtilioService.class);
                localIntentUsrPrsnt.putExtra("service", true);
                context.startService(localIntentUsrPrsnt);
            }

            if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                System.out.println("****** STOPING SERVICE BABY *************");
                context.startService(new Intent(context, UtilioService.class));

                Intent intentScreenOff = new Intent(context, UtilioService.class);
                intentScreenOff.putExtra("screenoff",true);
                context.startService(intentScreenOff);

            }

            */

        }


    }

}
