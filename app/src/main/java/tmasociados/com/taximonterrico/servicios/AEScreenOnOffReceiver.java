package tmasociados.com.taximonterrico.servicios;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tmasociados.PermisosActivity;
import com.tmasociados.notificaciones.TMAsociadosBroadcastReceiver;

public class AEScreenOnOffReceiver extends BroadcastReceiver {

    private boolean screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {

if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

screenOff = true;

    Intent i = new Intent(context, PermisosActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);

} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            screenOff = false;

        NotificationManager nManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();

        Intent serviceIntentx = new Intent(context, LocationService.class);
        context.startService(serviceIntentx);

        Intent serviceIntenta = new Intent(context, TMAsociadosBroadcastReceiver.class);
        context.startService(serviceIntenta);



}

        NotificationManager nManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();

        Intent serviceIntentx = new Intent(context, LocationService.class);
        context.startService(serviceIntentx);

        Intent serviceIntenta = new Intent(context, TMAsociadosBroadcastReceiver.class);
        context.startService(serviceIntenta);
}
}