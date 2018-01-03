package tmsystem.com.tmsystemdriver.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.presentation.main.PermisosActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NetworkChangeReceiver extends BroadcastReceiver {

    Context ctx;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        //Toast.makeText(context, status, Toast.LENGTH_LONG).show();

        if (status.equals("Not connected to Internet"))
        {
            Intent intents = new Intent(context.getApplicationContext(), PermisosActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intents, 0);
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context.getApplicationContext())
                            .setSmallIcon(android.support.v4.R.drawable.notification_bg_low)
                            .setColor(context.getResources().getColor(R.color.colorAccent))
                            .setContentTitle("Notificacion")
                            .setContentIntent(pIntent)
                            .setDefaults(Notification.DEFAULT_VIBRATE)
                            .setContentText("Verifica tu Conexion a Internet")
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, builder.build());

            Log.d("configuracioninternet","sin internet");
        }
        else
        {
            Log.d("configuracioninternet","con internet");

            NotificationManager nManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
            nManager.cancelAll();

           // Intent serviceIntentx = new Intent(context, LocationService.class);
          //  context.startService(serviceIntentx);

          //  Intent serviceIntenta = new Intent(context, TMAsociadosBroadcastReceiver.class);
          //  context.startService(serviceIntenta);
        }


    }




}