package tmasociados.com.taximonterrico.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.tmasociados.notificaciones.TMAsociadosBroadcastReceiver;

/**
 * Created by MD on 28/08/2017.
 */

public class AndroidServiceStartOnBoot extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // here you can add whatever you want this service to do


        startService(new Intent(getBaseContext(), AEScreenOnOffService.class));

        startService(new Intent(getBaseContext(), LocationService.class));

        startService(new Intent(getBaseContext(), TMAsociadosBroadcastReceiver.class));


    }

}