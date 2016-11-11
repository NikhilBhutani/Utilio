package com.github.nikhilbhutani.utilio.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Nikhil Bhutani on 11/10/2016.
 */

public class UtilioService extends Service {

    Timer mTimer;
    Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
         mTimer = new Timer();
         mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
          if (intent.getBooleanExtra("service",true)) {
              onPhoneUnlocked();
              return Service.START_NOT_STICKY;
          }
         return onStartCommand(intent, flags, startId);

    }

    private void onPhoneUnlocked() {
        Toast.makeText(this, "**** SERVICE RUNNING ****** ", Toast.LENGTH_SHORT).show();

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
                        final String strDate = simpleDateFormat.format(calendar.getTime());

                        Toast.makeText(UtilioService.this, strDate, Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }, 20000L, 20000L);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
