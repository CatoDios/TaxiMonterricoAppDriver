package tmasociados.com.taximonterrico.presentation.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import tmasociados.com.taximonterrico.servicios.NetworkStateReceiver;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NetworkStateReceiver.NetworkStateReceiverListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationChangeListener, LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    //Ruta de conexion
   // String BASE_URL ="http://200.48.119.46:6668/Service1.svc/";

    //Metodos Sistema
    String METODO_GET_VALIDAIMEI = "getvalidaimnei/";

    //Metodos Sistema
    String METODO_POST_DISPONIBLE = "postestadodisponible/";
    String METODO_POST_NODISPONIBLE = "postestadonodisponible/";
    String METODO_POST_SESIONCERRADA = "postsesioncerrada/";
    String METODO_POST_CASA = "postcasa/";

    String METODO_GET_MOVILESDISPONIBLES = "getmovilesdisponibles/";

    String METODO_GET_DATOSASOCIADO = "getdatosasociado/";
    String METODO_GET_SERVICIOSEMPALMADO = "getserviciosempalmado/";

    String xapelidos, xnombres, xcodasociado, xcodmovil, xruta;

    private NetworkStateReceiver networkStateReceiver;

    TextView name, lblemaixl, lblemailx;

    //CircleImageView imgConductor;

    BottomBar bottomBar;

    ProgressDialog pd;

    Toolbar toolbar;

    int xtrafic=0;

    int visualizamovil=0;

   // ArrayList<MovilesDb> Moviles_Db = new ArrayList<>();
   // ArrayList<ServiciosDisponiblesDb> ServiciosDisponibles_Db = new ArrayList<>();
    //ArrayList<ServiciosEmpalmadoDb> ServiciosEmpalmado_Db = new ArrayList<>();

    FloatingActionButton btntrafico, btnmoviles, floatingActionButton3;

    Float xlatitude, xlongitude;

    Context context;

    double ylatitude;
    double ylongitude;

    ListView lstregistrados;

    String xdeszonificacion, xhora, xcant;

    private String provider;
    private LocationManager locationManager;

    private AlertDialog dialogogps, alertverficaversion;

    String xitem;

    String xestado="";

    protected PowerManager.WakeLock wakelock;

    String xcodconductor;


    private static final String TAG=MainActivity.class.getName();
    private static ArrayList<Activity> activities=new ArrayList<Activity>();

    SharedPreferences sharedpreferences;

    //ParseInstallation installation;

    String namesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (xestado.equals("DISPONIBLE"))
            {
                nodiposnible();
            }
            else
            {
                if (xestado.equals("NO DISPONIBLE") || xestado.equals("SESION CERRADA") || xestado.equals("A MI DOMICILIO"))
                {
                    disponible();
                }
            }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        name = (TextView)header.findViewById(R.id.lblemail);
        lblemailx= (TextView)header.findViewById(R.id.lblemailx);
        //imgConductor = (CircleImageView) header.findViewById(R.id.imgConductor);

        String ver = "";
        try {
            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            ver = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException ne) {

        }

        lblemailx.setText("Version:" + " " + ver );
        lblemailx.setTypeface(null, Typeface.BOLD);


        btntrafico = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton3= (FloatingActionButton) findViewById(R.id.floatingActionButton3);






        getvalidaimnei(getIMEI());

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tabmenu)
                {
                    bottomBar.selectTabAtPosition(0);
                }
                else
                {
                    if (tabId == R.id.tab_calls)
                    {

                        bottomBar.selectTabAtPosition(0);

                        // seguimiento
                        startActivity(new Intent(getApplicationContext(), DatosPerfilActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                    }
                    else
                    {
                        if (tabId == R.id.tab_groups)
                        {
                            // vuelo servicio
                            startActivity(new Intent(getApplicationContext(), ProduccionActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            finish();

                        }
                        else
                        {
                            if (tabId == R.id.tab_groupsx)
                            {
                                startActivity(new Intent(getApplicationContext(), CalificacionesActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();
                                bottomBar.selectTabAtPosition(0);
                            }
                        }
                    }

                }
            }
        });


        progress();

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

        notificaciones();

        xx();

        final PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        this.wakelock=pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "etiqueta");
        wakelock.acquire();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        activities.add(this);

    }

    void xx()
    {
        String packageName = "com.tmasociados";
        String url = "http://carreto.pt/tools/android-store-version/?package=";
       // JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url+packageName, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        /*
                                here you have access to:

                                package_name, - the app package name
                                status - success (true) of the request or not (false)
                                author - the app author
                                app_name - the app name on the store
                                locale - the locale defined by default for the app
                                publish_date - the date when the update was published
                                version - the version on the store
                                last_version_description - the update text description


                             */

                        String as="";
                        //Toast.makeText(getApplicationContext(), response.getString("version").toString(), Toast.LENGTH_LONG).show();
                        try{
                            if(response != null && response.has("status") && response.getBoolean("status") && response.has("version")){
                              //  Toast.makeText(getApplicationContext(), response.getString("version").toString(), Toast.LENGTH_LONG).show();
                                String ver = "";
                                try {
                                    PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                    ver = pinfo.versionName;
                                } catch (PackageManager.NameNotFoundException ne) {

                                }

                                String xver="";
                                xver=response.getString("version").toString();

                                if (ver.equals(xver))
                                {

                                }
                                else
                                {
                                    verificaversion();
                                }

                            }
                            else{
                                //TODO handling error
                            }
                        }
                        catch (Exception e){
                            //TODO handling error
                        }

                    }


                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("hola", "Error: " + error.getMessage());

                    }
                });

        //AppController.getInstance().addToRequestQueue(jsObjRequest);
    }


    public void verificaversion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Existe una nueva version por favor actualizar!!!");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                final String apkurl = "market://details?id=" + "com.tmasociados";
                final Uri marketUri = Uri.parse(apkurl);

                Intent promptInstall = new Intent(Intent.ACTION_VIEW).setData(marketUri);
                startActivity(promptInstall);

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

    void notificaciones()
    {
        NotificationManager nManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();
    }

    void mensajeestado()
    {

       Alerter.create(MainActivity.this)
                .setTitle("Estado")
                .setText("Se actualizo su estado con exito")
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }

    public void nodiposnible() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseas cambiar tu estado a NO DISPONIBLE?")
                .setTitle("Cambio Estado")
                .setIcon(R.drawable.btnadvertencia)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        progress();

                        new UpdateCodigoNoDisponibleAsyncTask(getIMEI()).execute();

                        getvalidaimnei(getIMEI());

                        mensajeestado();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // some code if you want
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void disponible() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseas cambiar tu estado a DISPONIBLE?")
                .setTitle("Cambio Estado")
                .setIcon(R.drawable.btnadvertencia)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        progress();

                        new UpdateCodigoDisponibleAsyncTask(getIMEI()).execute();

                        getvalidaimnei(getIMEI());

                        mensajeestado();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // some code if you want
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    void progress()
    {
        pd = new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // pd.setIcon(R.drawable.btnloading); // you can set your own icon here
        // pd.setTitle("Cargando");
        pd.setMessage("Espere por favor...");
        pd.setIndeterminate(false);
        pd.setCancelable(false); // this will disable the back button
        pd.show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_turno) {
            startActivity(new Intent(getApplicationContext(), TurnoActivity.class)
                    .addFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        else
        {
            if (id == R.id.nav_mensaje) {

                startActivity(new Intent(getApplicationContext(), MensajeriaActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
            else
            {
                if (id == R.id.nav_vuelos) {

                    startActivity(new Intent(getApplicationContext(), DatosServicioVueloActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
                else
                {
                    if (id == R.id.nav_casa) {

                        amidomicilio();
                    }
                    else
                    {
                        if (id == R.id.nav_salir) {

                            salirapp();
                        }
                        else
                        {
                            if (id == R.id.nav_servicioempalmado)
                            {
                                if (xestado.equals("DISPONIBLE"))
                                {
                                    ServiciosEmpalmado_Db.clear();
                                    getserviciosempalmado(getIMEI());
                                    showInputDialogempalme();
                                }
                                else
                                {
                                    Alerter.create(MainActivity.this)
                                            .setTitle("Error")
                                            .setText("Verifica tu estado...")
                                            .setBackgroundColorRes(R.color.coloradvertencia)
                                            .show();
                                }

                            }
                            else
                            {
                                if (id == R.id.nav_servicioseparacion)
                                {
                                    if (xestado.equals("A MI DOMICILIO") || xestado.equals("SESION CERRADA"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), SeparacionServiciosActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        finish();
                                    }
                                    else
                                    {
                                        Alerter.create(MainActivity.this)
                                                .setTitle("Error")
                                                .setText("Verifica tu estado...")
                                                .setBackgroundColorRes(R.color.coloradvertencia)
                                                .show();
                                    }

                                }
                                else
                                {
                                    if (id == R.id.nav_valespendientes)
                                    {
                                        startActivity(new Intent(getApplicationContext(), ValesPendientesActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                        finish();
                                    }
                                    else
                                    {
                                        if (id == R.id.nav_pagos)
                                        {
                                            startActivity(new Intent(getApplicationContext(), PagosActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                            finish();
                                        }
                                        else
                                        {
                                            if (id == R.id.nav_resetear)
                                            {
                                                AppController.getInstance().clearApplicationData();

                                                trimCache(this);

                                                Alerter.create(MainActivity.this)
                                                        .setTitle("Aviso")
                                                        .setText("Se realizo la limpieza respectiva...")
                                                        .setBackgroundColorRes(R.color.coloradvertencia)
                                                        .show();
                                            }
                                            else
                                            {
                                                if (id == R.id.nav_servautoasignado)
                                                {
                                                    if (xestado.equals("DISPONIBLE"))
                                                    {
                                                        startActivity(new Intent(getApplicationContext(), HiperzonasActivity.class)
                                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                                        finish();
                                                    }
                                                    else
                                                    {

                                                        Alerter.create(MainActivity.this)
                                                                .setTitle("Aviso")
                                                                .setText("Revise su estado por favor")
                                                                .setBackgroundColorRes(R.color.coloradvertencia)
                                                                .show();
                                                    }

                                                }
                                            }
                                        }

                                        //


                                    }

                                }
                            }

                        }
                    }
                }
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
    public String getIMEI()
    {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        return device_id;
    }

    void actualizaparse() {
        //String deviceToken = ParseInstallation.getCurrentInstallation().getString("installationId");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        namesp = preferences.getString("spkey", "");

        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("tab_asociados");
            query.whereEqualTo("codconductor", xcodconductor);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> objects,
                                 ParseException e) {
                    try {

                        objects.get(0).put("installation_Id", ParseInstallation.getCurrentInstallation().get("installationId"));
                        objects.get(0).put("status",  0);
                        objects.get(0).put("imei",  getIMEI());

                        objects.get(0).saveInBackground();

                    } catch (Exception e2) {
                    }
                }
            });

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }

    }

    private void getvalidaimnei(String imei) {
        Log.d("LOCATION INSERTA",  BASE_URL + METODO_GET_VALIDAIMEI + imei);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + METODO_GET_VALIDAIMEI + imei, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("getvalidaimneiResult");
                    int valorn = jsonArray.length();
                    if (valorn == 0) {
                        if(pd != null){
                            pd.dismiss();
                        }

                        msgimei();
                    } else {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                        getdatosasociado(getIMEI());

                        if(pd != null ){
                            pd.dismiss();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void getdatosasociado(String ximei) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + METODO_GET_DATOSASOCIADO + ximei, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("getdatosasociadoResult");
                    int valorn = jsonArray.length();
                    if (valorn == 0) {
                        msgimei();
                        if(pd != null ){
                            pd.dismiss();
                        }
                    } else {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        xapelidos = jsonObject.getString("apeasoc");
                        xnombres = jsonObject.getString("nomasoc");
                        xcodasociado = jsonObject.getString("codigoasociado");
                        xcodmovil = jsonObject.getString("codmovil");
                        xestado = jsonObject.getString("descondestadosistema");
                        xruta = jsonObject.getString("rutafoto");
                        xcodconductor= jsonObject.getString("codconductor");

                        actualizaparse();

                        //lblemaixl.setText(xestado);
                        name.setText(xapelidos + " " + xnombres + "\n" + "\n" +  xcodasociado + " " +"-" +  " " +  xcodmovil);
                        Picasso.with(getApplicationContext()).load(xruta).into(imgConductor);

                        if (xestado.equals("ALERTA DE SERVICIO") || xestado.equals("ASIG. AUTOMATICA"))
                        {
                            startActivity(new Intent(getBaseContext(), AsignacionServicioActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            finish();
                        }
                        else
                        {
                            if (xestado.equals("CAMINO AL SERVICIO") || xestado.equals("EN EL PUNTO")|| xestado.equals("SERVICIO EN PROCESO")|| xestado.equals("USUARIO CONTACTADO")|| xestado.equals("SERVICIO POR DESPLAZAMIENTO"))
                            {
                                startActivity(new Intent(getBaseContext(), DatosServicioActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();
                            }
                            else
                            {

                                if (xestado.equals("DISPONIBLE"))
                                {
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        toolbar.setBackgroundColor((Color.parseColor("#688368")));
                                        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));
                                        setTaskBarCologreem();
                                    }

                                }
                                else
                                {
                                    if (xestado.equals("NO DISPONIBLE") || xestado.equals("A MI DOMICILIO") || xestado.equals("SESION CERRADA"))
                                    {
                                        if (Build.VERSION.SDK_INT >= 21) {
                                            toolbar.setBackgroundColor((Color.parseColor("#812f1c")));
                                            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                            setTaskBarColored();
                                        }

                                    }
                                    else
                                    {
                                        if (xestado.equals("SUSPENSION TEMPORAL") || xestado.equals("SUSPENSION DEFINITIVA"))
                                        {
                                            if (Build.VERSION.SDK_INT >= 21) {
                                                toolbar.setBackgroundColor((Color.parseColor("#949493")));
                                                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                setTaskBarCologris();
                                            }
                                            estadonoactivo();

                                        }
                                        else
                                        {
                                            if (xestado.equals("DESACTIVACION POR ADMINISTRADOR"))
                                            {
                                                if (Build.VERSION.SDK_INT >= 21) {
                                                    toolbar.setBackgroundColor((Color.parseColor("#949493")));
                                                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                    setTaskBarCologris();
                                                }
                                                desacitvadoporadministrado();

                                            }
                                            else
                                            {
                                                if (xestado.equals("NO CONEXION SISTEMAS"))
                                                {
                                                    if (Build.VERSION.SDK_INT >= 21) {
                                                        toolbar.setBackgroundColor((Color.parseColor("#949493")));
                                                        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                        setTaskBarCologris();
                                                    }
                                                    estadoporinactividad();

                                                }
                                                else
                                                {
                                                    if (xestado.equals("ASOCIADO DESACTIVADO POR NO CONTAR CON SERVICIO"))
                                                    {
                                                        if (Build.VERSION.SDK_INT >= 21) {
                                                            toolbar.setBackgroundColor((Color.parseColor("#949493")));
                                                            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                            setTaskBarCologris();
                                                        }
                                                        desactivadosinservicio();

                                                    }
                                                    else
                                                    {
                                                        if (xestado.equals("PERMISO AUTORIZADO"))
                                                        {
                                                            if (Build.VERSION.SDK_INT >= 21) {
                                                                toolbar.setBackgroundColor((Color.parseColor("#949493")));
                                                                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                                setTaskBarCologris();
                                                            }
                                                            permisotemporal();

                                                        }
                                                        else
                                                        {
                                                            if (xestado.equals("PARADERO"))
                                                            {
                                                                if (Build.VERSION.SDK_INT >= 21) {
                                                                    toolbar.setBackgroundColor((Color.parseColor("#295f83")));
                                                                    getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>"+ xestado+ "</font>"));

                                                                    setTaskBarColoazul();
                                                                }

                                                            }
                                                        }
                                                    }
                                                }

                                            }


                                        }


                                    }
                                }



                            }
                        }

                        if(pd != null ){
                            pd.dismiss();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                  /*  Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();*/

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
              /*  Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void permisotemporal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Ud. se encuentra con permiso comuniquese con el area de flota para su reactivacion");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                System.exit(0);

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }


    public void desactivadosinservicio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("No estuviste en la hora punta, por ahora distribuiremos los servicios entre los que sí estuvieron");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                System.exit(0);

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

    public void estadoporinactividad() {
          AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Estimado asociado ha sido desactivado por estar sin conexion con el sistema mucho tiempo, verifica tu ahorro de energia, aplicaciones de limpieza");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                System.exit(0);
            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

    public void desacitvadoporadministrado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Estimado asociado ha sido desactivado por el administrador de flota, comunicarse con el area de flota");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                System.exit(0);

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

    public void estadonoactivo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("Usted se encuentra suspendido, por favor comunicarse con el area de flota");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                System.exit(0);

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }

    public void setTaskBarColoazul() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorazul));
        }
    }

    public void setTaskBarCologris() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorgris));
        }
    }

    public void setTaskBarColored() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.coloradvertencia));
        }
    }

    public void setTaskBarCologreem() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccent));
        }
    }



    public void msgimei() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainActivity.this);

        alertDialog2.setCancelable(false);

        // Setting Dialog Title
        alertDialog2.setTitle("Imei Incorecto");

        // Setting Dialog Message
        alertDialog2.setMessage("Tu imei no se encuentra registrado, deseas actualizarlo?");

        // Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.btnverror);

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("Actualizar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        startActivity(new Intent(getBaseContext(), ActualizaImeiActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        System.exit(0);
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);

        if (dialogogps != null) {
            dialogogps.dismiss();
        }

        if (alertverficaversion != null) {
            alertverficaversion.dismiss();
        }

         if(pd != null ){
            pd.dismiss();
        }

        this.wakelock.release();

        activities.remove(this);
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

        Alerter.create(MainActivity.this)
                .setTitle("Error")
                .setText("Verifica tu conexion a internet")
                .setBackgroundColorRes(R.color.coloradvertencia)
                .show();
    }


    @Override
    public void onMyLocationChange(Location location) {
        double xvlatitude = location.getLatitude();

        // Getting longitude of the current location
        double xvlongitude = location.getLongitude();

        ylatitude= location.getLatitude();
        ylongitude= location.getLongitude();

        LatLng latLng = new LatLng(xvlatitude, xvlongitude);

        //latitude= String.valueOf(location.getLatitude());
        //longitude= String.valueOf(location.getLongitude());

        // Creating a LatLng object for the current location


        // getZona(latitude, longitude);

        if (visualizamovil==0)
        {
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ybtnmmovil)));



            // Showing the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            int speed = (int) ((location.getSpeed() * 3600) / 1000);
        }




    }




    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            mMap.setOnMyLocationChangeListener(this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
       // Toast.makeText(this, "Enabled new provider " + provider,
            //    Toast.LENGTH_SHORT).show();
        if (dialogogps != null) {
            dialogogps.dismiss();
        }

    }

    @Override
    public void onProviderDisabled(String s) {
      //  Toast.makeText(this, "Disabled provider " + provider,
             //   Toast.LENGTH_SHORT).show();.
        alertafalta();
    }
    public void alertafalta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Advertencia");
        builder.setMessage("El sistema GPS esta desactivado, para continuar presione el boton activar?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.btnadvertencia);
        builder.setPositiveButton("Activar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                startActivity(new Intent

                        (Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        });
        dialogogps = builder.create();
        dialogogps.show();
    }



    public class UpdateCodigoDisponibleAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei;

        public UpdateCodigoDisponibleAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_DISPONIBLE + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(ximei);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    public class UpdateCodigoNoDisponibleAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei;

        public UpdateCodigoNoDisponibleAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_NODISPONIBLE + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(ximei);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    private void getmovilesdisponibles(String imei) {
        //showpDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + METODO_GET_MOVILESDISPONIBLES + imei, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    //MyToastInformation.show(getApplicationContext(), "No ha ingresado N° de vale, por favor validar", true);

                    JSONArray jsonArray = response.getJSONArray("getmovilesdisponiblesResult");
                    int valorn = jsonArray.length();
                    if (valorn == 0) {
                        // MyToastInformation.show(getApplicationContext(), "No existe servicios disponibles", true);
                        if(pd != null ){
                            pd.dismiss();
                        }

                    } else {
                        for (int i = 0; i <= jsonArray.length() - 1; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Para los puntos, origen

                            xlatitude = Float.parseFloat(jsonObject.getString("Latitude"));
                            xlongitude = Float.parseFloat(jsonObject.getString("Longitude"));
                            Moviles_Db.add(new MovilesDb(xlatitude,xlongitude));
                        }

                        for (int i = 0; i <= Moviles_Db.size() - 1; i++) {
                            Log.d("PUNTOS", String.valueOf(Moviles_Db.size()));
                            LatLng latLngDestino = new LatLng(Moviles_Db.get(i).getLatitude(), Moviles_Db.get(i).getLongitude());

                            Marker myMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLngDestino)
                                    .title("0")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.btngpsasociado)));
                        }


                        if(pd != null){
                            pd.dismiss();
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.activity_servicios_disponibles, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Servicios Disponibles");

        lstregistrados = (ListView) promptView.findViewById(R.id.lstregistrados);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        dialog.dismiss();
                    }

                                });

        // create an alert dialog
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialogempalme() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.activity_servicios_empalmado, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setTitle("Servicios Empalmado");

        lstregistrados = (ListView) promptView.findViewById(R.id.lstregistrados);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        dialog.dismiss();
                    }

                });

        // create an alert dialog
        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    private void getserviciosempalmado(String imei)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + METODO_GET_SERVICIOSEMPALMADO+ imei, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("getserviciosempalmadoResult");
                    int valorn = jsonArray.length();
                    if (valorn == 0) {
                        if(pd != null){
                            pd.dismiss();
                        }
                    } else {
//xdeszonificacion, xhora, xcant;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //vitem, vcodsistemaauto, vhora, vrutafoto
                            xdeszonificacion = jsonObject.getString("direccion");
                            xhora = jsonObject.getString("horreg");

                            ServiciosEmpalmado_Db.add(new ServiciosEmpalmadoDb(xdeszonificacion, xhora));
                        }

                        ServiciosEmpalmadoAdapter adapter =
                                new ServiciosEmpalmadoAdapter(MainActivity.this, ServiciosEmpalmado_Db);
                        lstregistrados.setAdapter(adapter);
                        if(pd != null ){
                            pd.dismiss();
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hideProgress();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    public class UpdateCodigoSesionCerradaAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei;

        public UpdateCodigoSesionCerradaAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_SESIONCERRADA + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(ximei);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    public class UpdateCodigoCasaAsyncTask extends AsyncTask<Void, Long, Integer> {
        String ximei;

        public UpdateCodigoCasaAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_CASA + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            actualizarEstado(ximei);

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }


    public void salirapp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseas cerrar sesion?")
                .setTitle("Cerrar Sesion")
                .setIcon(R.drawable.btnadvertencia)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        new UpdateCodigoSesionCerradaAsyncTask(getIMEI()).execute();

                        //clearParse();
                        finishAll();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // some code if you want
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void amidomicilio() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseas  un servicio a tu domicilio?")
                .setTitle("A mi Domicilio")
                .setIcon(R.drawable.btnadvertencia)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        new UpdateCodigoCasaAsyncTask(getIMEI()).execute();

                        getvalidaimnei(getIMEI());

                        mensajeestado();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // some code if you want
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);

        wakelock.acquire();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        this.wakelock.release();

    }

    public static void finishAll()
    {
        for(Activity activity:activities)
            activity.finish();
    }



    public static void clearParse() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        Class clazz = installation.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        Method method1 = null;
        try {
            method1 = clazz.getDeclaredMethod("getCurrentInstallationController");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method1.setAccessible(true);
        Object result = null;
        try {
            result = method1.invoke(installation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Method method2 = null;
        try {
            method2 = result.getClass().getDeclaredMethod("clearFromDisk");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method2.setAccessible(true);
        try {
            String result2=(String) method2.invoke(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Method method3 = null;
        try {
            method3 = result.getClass().getDeclaredMethod("clearFromMemory");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        method3.setAccessible(true);
        try {
            String result3=(String) method3.invoke(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
