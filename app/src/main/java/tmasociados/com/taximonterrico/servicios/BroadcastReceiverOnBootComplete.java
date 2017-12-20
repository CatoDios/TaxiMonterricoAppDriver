package tmasociados.com.taximonterrico.servicios;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tmasociados.notificaciones.TMAsociadosBroadcastReceiver;

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, AndroidServiceStartOnBoot.class);
            context.startService(serviceIntent);
        }

        NotificationManager nManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();

        Intent serviceIntentx = new Intent(context, LocationService.class);
        context.startService(serviceIntentx);

        Intent serviceIntenta = new Intent(context, TMAsociadosBroadcastReceiver.class);
        context.startService(serviceIntenta);
    }
}
