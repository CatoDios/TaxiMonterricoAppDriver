package tmsystem.com.tmsystemdriver.services;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.AppsInstaladaDb;
import tmsystem.com.tmsystemdriver.data.models.GpsEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.presentation.main.PrincipalActivity;


public class LocationService extends Service  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, android.location.LocationListener, LocationContract.View{

    public static final long NOTIFY_INTERVAL = 10 * 1000;

    public static final long NOTIFY_INTERVAL_Zona = 200 * 1000;

    // run on another Thread to avoid crash
    private Handler yourHandler = new Handler();
    private Handler yourHandler_zona = new Handler();
    // timer handling
    private Timer yourTimer = null;
    private Timer yourTimer_zona = null;


    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private static final String LOGSERVICE = "#######";

    Double xlat = Double.valueOf(0);
    Double xlong = Double.valueOf(0);
    int velocidad = 0;


    PowerManager.WakeLock wakeLock;

    private String provider;
    private LocationManager locationManager;

    //Ruta de conexion
    String BASE_URL = "http://200.48.119.46:6668/Service1.svc/";
    String URL_CONSUMER="http://200.48.119.44:6001/monterrico.json";

    String METODO_POST_GPS_INSERTA = "postgpsinserta/";
    String METODO_POST_DATOS_EQUIPO = "postgpsdatosequipo/";
    String METODO_POST_ZONA = "postzona/";

    String MEOTOD_GET_APPSINSTALADA = "getappsinstalada/";

    String xmanufactura, xbrand, xmodel, xversion;

    String device_id;

    String deszonaOrg;

    public static final String ACTION = "com.parse.push.intent.RECEIVE";

    BroadcastReceiver mReceiver=null;


    String nombreapp = "", detalleapp = "";

    ArrayList<AppsInstaladaDb> AppsInstalada_Db = new ArrayList<>();

    String appsinstalada="";

    public static  Double xlatrec = Double.valueOf(0);
    public static Double xlonrec= Double.valueOf(0);

    float xlatgps, xlongps;

    private LocationContract.Presenter mPresenter;
    private SessionManager mSessionManager;


    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (yourTimer != null) {
            yourTimer.cancel();
        }
        // recreate new
        yourTimer = new Timer();

        // schedule task
        yourTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);


        /////////////////////////////
       /* if (yourTimer_zona != null) {
            yourTimer_zona.cancel();
        }
        // recreate new
        yourTimer_zona = new Timer();

        // schedule task
        yourTimer_zona.scheduleAtFixedRate(new TimeDisplayTimerTaskZona(), a0, NOTIFY_INTERVAL_Zona);*/


        buildGoogleApiClient();
        mSessionManager = new SessionManager(getApplicationContext());
        mPresenter = new LocationPresenter(this, getApplicationContext());

       // IntentFilter filter = new IntentFilter(ACTION);
       // mReceiver= new TMAsociadosBroadcastReceiver();
       // this.registerReceiver(mReceiver, filter);

    }

    @Override
    public void onStart(Intent intent, int startId) {

        String mesajeparse;

        try{
            // Get ON/OFF values sent from receiver ( AEScreenOnOffReceiver.
            mesajeparse= intent.getStringExtra("mensajeparse");
            Log.d("notifica", mesajeparse);

        }catch(Exception e){}


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (yourTimer != null) {
            yourTimer.cancel();
        }
        // recreate new
        yourTimer = new Timer();

        // schedule task
        yourTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);

        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "No cierre");

        //registerInternetCheckReceiver();

        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            ///latituteField.setText("Location not available");
            // longitudeField.setText("Location not available");
        }

        xmanufactura = Build.MANUFACTURER;
        xbrand = Build.BRAND;
        xmodel = Build.MODEL;
        xversion = Build.VERSION.RELEASE;

        String xver = "";
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            xver = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException ne) {

        }
        new UpdateDatosEquipoAsyncTask(xmanufactura.replace(" ", "%20"), xbrand.replace(" ", "%20"), xmodel.replace(" ", "%20"), xversion.replace(" ", "%20"), xver.replace(" ", "%20")).execute();

        //, xmanufactura, xbrand, xversion, xmodel.replace(" ", "%20")

        locationManager.requestLocationUpdates(provider, 400, 1, this);

        return START_STICKY;
    }

   /* class TimeDisplayTimerTaskZona extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            yourHandler_zona.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    Log.i("Zona", "Buscando");

                    getZonaOrigen(xlat, xlong);
                }

            });
        }
    }*/

    private Double getAngle(Double xlat, Double xlong) {

        double dLon = (xlong-  xlonrec);
        double y = Math.sin(dLon) * Math.cos(xlat);
        double x = Math.cos(xlatrec)*Math.sin(xlat) - Math.sin(xlatrec)*Math.cos(xlat)*Math.cos(dLon);
        double angle = Math.toDegrees((Math.atan2(y, x)));
        angle = ((angle + 360) % 360);
        if(angle !=0.0) angle = angle - 90;

        return angle;

    }

    @Override
    public void getGpsResponse(String msg) {
        Toast.makeText(this, "Enviado", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setPresenter(LocationContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            yourHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    Log.i("Probando", "Timer");

                    Double angulo = getAngle(xlat, xlong);


                  new UpdateGpsInsertaAsyncTask( xlat, xlong, angulo, velocidad ).execute();

                    xlatrec= xlat;

                    xlonrec=xlong;

                   // validaroot();

            /*        if (isMyServiceRunning(TMAsociadosBroadcastReceiver.class))
                    {
                        Log.i("servicio", "corriendo");
                    }
                    else
                    {
                        {
                            Log.i("servicio", "caido");
                        }
                    }
*/
                    //validamododesarrollador();
                    appsinstalada="";
                    AppsInstalada_Db.clear();
                    //getappsinstalada();
                }

            });
        }
    }

  /*  private void getappsinstalada() {
        Log.d("apps", BASE_URL + MEOTOD_GET_APPSINSTALADA);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + MEOTOD_GET_APPSINSTALADA, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("getappsinstaladaResult");
                    int valorn = jsonArray.length();
                    if (valorn == a0) {
                        MyToastInformation.show(getApplicationContext(), "Sin registro", true);
                    } else {
                        for (int i = a0; i <= jsonArray.length() - a1; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            nombreapp = jsonObject.getString("nombreapp");
                            detalleapp = jsonObject.getString("detalleapp");
                            AppsInstalada_Db.add(new AppsInstaladaDb(nombreapp, detalleapp));
                        }

                        for (int i = a0; i <= AppsInstalada_Db.size() - a1; i++) {
                            String detalle = AppsInstalada_Db.get(i).getDetalleapp();

                            boolean isAppInstalled = appInstalledOrNot(AppsInstalada_Db.get(i).getNombreapp());
                            if (isAppInstalled) {
                                appsinstalada = appsinstalada + "-" + detalle;
                            }
                        }

                        if (appsinstalada.length() == a0) {


                        } else
                            {
                            MyToastInformation.show(getApplicationContext(), "Verifica  cuentas con las siguientes aplicaciones instaladas" + " " + appsinstalada, true);

                            Intent dialogIntent = new Intent(getApplicationContext(), ErrorsAplicacionesInstaladasActivity.class);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dialogIntent);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
*/
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


   /* public String getIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        if (device_id == null) {
            device_id = "notavailable";
        } else {
            device_id = tm.getDeviceId();
        }
        return device_id;
    }*/

    private void addNotification() {
        Intent intents = new Intent(getApplicationContext(), PrincipalActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intents, 0);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.logo_tm)
                        .setColor(getResources().getColor(R.color.colorAccent))
                        .setContentTitle("Notificacion")
                        .setContentIntent(pIntent)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setContentText("Verifica tu GPS")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(LOGSERVICE, "onConnected" + bundle);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
            Log.i(LOGSERVICE, "lat " + l.getLatitude());
            Log.i(LOGSERVICE, "lng " + l.getLongitude());

        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(LOGSERVICE, "lat " + location.getLatitude());
        Log.i(LOGSERVICE, "lng " + location.getLongitude());
        LatLng mLocation = (new LatLng(location.getLatitude(), location.getLongitude()));

        xlat = location.getLatitude();
        xlong =location.getLongitude();
        velocidad = (int) ((location.getSpeed()*3600)/1000);

        //  EventBus.getDefault().post(mLocation);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        notificaciones();
    }

    @Override
    public void onProviderDisabled(String s) {
        addNotification();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");

    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }


    void notificaciones() {
        NotificationManager nManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();
    }

    public class UpdateGpsInsertaAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei;
        Double xlatitude, xlongitude;
        int xvelocidad;
        double xangulo;

        public UpdateGpsInsertaAsyncTask( Double xlatitude, Double xlongitude, Double xangulo, int xvelocidad) {
            this.xlatitude = xlatitude;
            this.xlongitude = xlongitude;
            this.xangulo= xangulo;
            this.xvelocidad = xvelocidad;

        }

        public int actualizarEstado( Double xlatitude, Double xlongitude, Double xangulo,  int xvelocidad) {
            // Create a new HttpClient and Post Header
            Log.d("LOCATION INSERTA", BASE_URL + METODO_POST_GPS_INSERTA + ximei + "/" + xlatitude + "/" + xlongitude +"/" + xangulo + "/" + xvelocidad);

            GpsEntity gpsEntity = new GpsEntity(mSessionManager.getUserEntity().getAsociado().getIdasociado(), xlatitude,xlongitude,xvelocidad,xangulo);
            mPresenter.sendGps(gpsEntity);

            /*JSONObject body = new JSONObject();
            try {
                body.put("imei", ximei);
                body.put("latitude", xlatitude);
                body.put("longitude", xlongitude);
                body.put("angle", xangulo);
                body.put("speed", xvelocidad);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("body",body.toString());

            Map<String, String> headers = new HashMap<>();
            headers.put("version", "a1");

            Volley.newRequestQueue(getApplicationContext()).add(new GsonRequest<>(Request.Method.POST, "http://200.48.119.44:8085/api/employee/InsertGps", ResultGPSDb.class, headers, null, body, new Response.Listener<ResultGPSDb>() {
                @Override
                public void onResponse(ResultGPSDb response) {
                    Log.d("gps", new Gson().toJson(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",new Gson().toJson(error));
                }
            }));*/
// BASE_URL + METODO_POST_GPS_INSERTA + ximei + "/" + xlatitude + "/" + xlongitude + "/" + xvelocidad


            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(xlatitude, xlongitude, xangulo, xvelocidad);


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

    }

    //METODO_POST_DATOS_EQUIPO
    public class UpdateDatosEquipoAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei, xmanufactura, xbrand, xmodel, xversion, xver;

        public UpdateDatosEquipoAsyncTask( String xmanufactura, String xbrand, String xmodel, String xversion, String xver) {
            this.xmanufactura = xmanufactura;
            this.xbrand = xbrand;
            this.xmodel = xmodel;
            this.xversion = xversion;
            this.xver = xver;
        }

        public int actualizarEstado(String xmanufactura, String xbrand, String xmodel, String xversion, String xver) {
            // Create a new HttpClient and Post Header
            Log.d("DATOS EQUIPO", BASE_URL + METODO_POST_DATOS_EQUIPO + ximei + "/" + xmanufactura + "/" + xbrand + "/" + xmodel + "/" + xversion + "/" + xver);

            /*JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_DATOS_EQUIPO + ximei + "/" + xmanufactura + "/" + xbrand + "/" + xmodel + "/" + xversion + "/" + xver, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });*/
           // AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(xmanufactura, xbrand, xmodel, xversion, xver);


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

    }

    public class UpdateZonaAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei, xzona;

        public UpdateZonaAsyncTask(String ximei, String xzona) {
            this.ximei = ximei;
            this.xzona = xzona;
        }

        public int actualizarEstado(String ximei, String xzona) {
            // Create a new HttpClient and Post Header
            Log.d("REGISTRAZONA", BASE_URL + METODO_POST_ZONA + ximei + "/" + xzona);
/*
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_ZONA + ximei + "/" + xzona, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);*/
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(ximei, xzona);


            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

    }


    /**
     *  Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */

    /*private void getZonaOrigen(final double lat, final double lng){
        final List<LatLng> LatLngList1 = new ArrayList<>();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_CONSUMER, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("features");
                            for (int i = a0; i < jsonArray.length(); i++) {
                                JSONArray coordinates = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                                for (int j = a0; j < coordinates.length(); j++) {
                                    JSONObject innerArray = coordinates.getJSONObject(j);
                                    double latt = innerArray.getDouble("latitud");
                                    double lngg = innerArray.getDouble("longitud");
                                    LatLngList1.add(new LatLng(latt, lngg));
                                }
                                if (ValidarPoligono(LatLngList1, lat, lng)) {
                                    JSONObject properties = jsonArray.getJSONObject(i).getJSONObject("properties");
                                    deszonaOrg = properties.getString("name");

                                    //getvalidacodigozona(deszonaOrg);


                                    new UpdateZonaAsyncTask(getIMEI(), deszonaOrg).execute();

                                    Log.d("nomzona",deszonaOrg);



                                    break;
                                }
                                LatLngList1.clear();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),
                        "No hay datos", Toast.LENGTH_SHORT).show();
            }
        }
        );
        AppController.getInstance().addToRequestQueue(request);
    }*/

    /*public boolean ValidarPoligono(List<LatLng> pointList, double x, double y){
        int counter = a0,i;
        double xinters;
        LatLng p1;
        LatLng p2;
        int n = pointList.size();
        p1 = pointList.get(a0);
        for (i = a1; i<=n ; i++){
            p2 = pointList.get(i % n);
            if (y > Math.min(p1.longitude,p2.longitude)){
                if(y <= Math.max(p1.longitude,p2.longitude)){
                    if(x <= Math.max(p1.latitude,p2.latitude)){
                        if (p1.longitude!=p2.longitude){
                            xinters = (y - p1.longitude)*(p2.latitude - p1.latitude)/(p2.longitude - p1.longitude)+ p1.latitude;
                            if (p1.latitude == p2.latitude || x <= xinters) counter++;
                        }
                    }
                }
            }
            p1=p2;
        }
        return counter % a2 != a0;
    }*/

  /*  void validaroot()
    {
        RootBeer rootBeer = new RootBeer(getApplicationContext());

        if (rootBeer.isRooted())
        {

            Intent dialogIntent = new Intent(getApplicationContext(), ErrorsModoRoot_Activity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(dialogIntent);
        }
    }*/

    /*void validamododesarrollador()
    {
        int adb = Settings.Secure.getInt(this.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , a0);

        if (adb==a0)
        {

        }
        else
        {
            Intent dialogIntent = new Intent(getApplicationContext(), ErrorsModoDesarrollador_Activity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(dialogIntent);

        }
    }
*/

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onDestroy(){
        Log.v("SERVICE","Service killed");
       // Intent serviceIntenta = new Intent(getApplicationContext(), TMAsociadosBroadcastReceiver.class);
        //startService(serviceIntenta);

        //actualizaparse();
        super.onDestroy();
    }



}


