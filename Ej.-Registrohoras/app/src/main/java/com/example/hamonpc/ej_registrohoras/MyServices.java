package com.example.hamonpc.ej_registrohoras;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by HamonPC on 06/10/2017.
 */

public class MyServices extends Service {
    private static final String TAG = "myservices";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Servicio Inicializado");

                try {
                    Thread.sleep(1000*60);
                    NotificationManager nManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder= new NotificationCompat.Builder(
                            getBaseContext())
                            .setSmallIcon(R.drawable.iconapp)
                            .setContentTitle("Registo de horas")
                            .setContentText("Llevas 48 horas sin registrar tus actividades.")
                            .setWhen(System.currentTimeMillis());

                    nManager.notify(12345,builder.build());
                    Log.d(TAG,"Tarea terminada");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        this.stopSelf();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Servicio destruido");
    }
}
