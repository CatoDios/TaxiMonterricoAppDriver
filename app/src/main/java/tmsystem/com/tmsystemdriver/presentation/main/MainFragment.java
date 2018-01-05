package tmsystem.com.tmsystemdriver.presentation.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseFragment;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.presentation.asignacion.AsignacionServicioActivity;
import tmsystem.com.tmsystemdriver.utils.PermissionUtils;

/**
 * Created by kath on 20/12/17.
 */

public class MainFragment extends BaseFragment implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationChangeListener, LocationListener, MainContract.View {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static final String ACTION_NOTIFY_NEW_PROMO = "NOTIFY_NEW_PROMO";
    @BindView(R.id.acccion_requisitos)
    FloatingActionButton acccionRequisitos;
    @BindView(R.id.accion_costos)
    FloatingActionButton accionCostos;
    @BindView(R.id.accion_seguimiento)
    FloatingActionButton accionSeguimiento;
    @BindView(R.id.floating_menu)
    FloatingActionsMenu floatingMenu;
    private BroadcastReceiver mNotificationsReceiver;


    public static final int DISPO = 1;
    public static final int NO_DISPO = 2;


    public static final String DISPONIBLE = "DISPONIBLE";
    public static final String NO_DISPONIBLE = "NO DISPONIBLE";
    public static final String CAMINO_AL_SERVICIO = "CAMINO AL SERVICIO";
    public static final String EN_EL_PUNTO = "EN EL PUNTO";
    public static final String USUARIO_CONTACTADO = "USUARIO CONTACTADO";
    public static final String SERVICIO_EN_PROCESO = "SERVICIO EN PROCESO";
    public static final String SERVICIO_FINALIZADO = "SERVICIO FINALIZADO";


    @BindView(R.id.btn_estado)
    Button btnEstado;
    Unbinder unbinder;

    SupportMapFragment mapFragment;
    @BindView(R.id.tv_conectado)
    TextView tvConectado;
    @BindView(R.id.mySwitch)
    Switch mySwitch;

    private AlertDialog dialogogps, dialogDisponible, alertverficaversion;
    private LocationManager locationManager;
    private String provider;
    GoogleApiClient googleApiClient;
    LocationRequest mLocationRequest;

    double ylatitude;
    double ylongitude;
    int visualizamovil = 0;
    GoogleApiClient mGoogleApiClient;

    private View mapView;
    private GoogleMap mMap;

    private SessionManager mSessionManager;

    private MainContract.Presenter mPresenter;

    private MainInterface mainInterface;

    private int idEstado;
    private int nextEstado;
    private int idReserva;


    private ServicioEntity serEntity;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado());
        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this, getContext());
        mSessionManager = new SessionManager(getContext());
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            ///latituteField.setText("Location not available");
            // longitudeField.setText("Location not available");
        }

        mNotificationsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String title = intent.getStringExtra("title");
                String description = intent.getStringExtra("description");
                String expiryDate = intent.getStringExtra("expiry_date");
                String discount = intent.getStringExtra("discount");
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fr = getFragmentManager();
            FragmentTransaction ft = fr.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        // set the switch to ON
        mySwitch.setChecked(false);
        // attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), DISPO);
                    mPresenter.sendEstado(sendEstado);
                    // Toast.makeText(getContext(), "Seleccionado", Toast.LENGTH_SHORT).show();

                } else {
                    SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), NO_DISPO);
                    mPresenter.sendEstado(sendEstado);
                    // Toast.makeText(getContext(), "No seleccionado", Toast.LENGTH_SHORT).show();

                }

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }


    }

    public void validarEstadoDisponible(int estado) {

        switch (estado) {
            case 1:
                UiServicio(DISPONIBLE, DISPONIBLE, false, true, true, false);
                mySwitch.setChecked(true);
                break;

            case 2:
                UiServicio(NO_DISPONIBLE, NO_DISPONIBLE, false, true, true, false);
                break;

            case 3:
                // SESION CERRADA 3

                break;
            case 4:
                // A MI DOMICILIO 4
                break;

            case 5:
                //SUSPENSION TEMPORAL 5
                String msg = "SUSPENCIÓN TEMPORAL";
                mySwitch.setClickable(false);
                alertaNodisponible(msg);
                break;

            case 6:
                //SUSPENSION DEFINITIVA 6
                String msg1 = "SUSPENSIÓN DEFINITIVA";
                mySwitch.setClickable(false);
                alertaNodisponible(msg1);
                break;

            case 7:
                //PERMISO TEMPORAL 7
                String msg2 = "PERMISO TEMPORAL";
                mySwitch.setClickable(false);
                alertaNodisponible(msg2);
                break;

            case 8:
                //LLAMADO A BASE 8
                String msg3 = "LLAMADO A BASE";
                mySwitch.setClickable(false);
                alertaNodisponible(msg3);
                break;

            case 9:
                //SERVICIO PENDIENTE DE ASIGNACION
                mPresenter.getEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado());
                break;

            case 10:
                //10 ASIGNACION MANUAL
                nextActivity(getActivity(), null, AsignacionServicioActivity.class, true);
                UiServicio(DISPONIBLE, "TOMAR SERVICIO", true, false, false, true);
                btnEstado.setBackgroundColor(getResources().getColor(R.color.green));
                break;

            case 11:
                //11 ASIGNACION AUTOMATICA
                nextActivity(getActivity(), null, AsignacionServicioActivity.class, true);
                UiServicio(DISPONIBLE, "TOMAR SERVICIO", true, false, false, true);
                btnEstado.setBackgroundColor(getResources().getColor(R.color.green));
                break;

            case 12:
                //12 CAMINO AL SERVICIO
                UiServicio(CAMINO_AL_SERVICIO, EN_EL_PUNTO, true, false, false, true);
                nextEstado = estado + 1;
                floatingMenu.setVisibility(View.VISIBLE);
                mPresenter.getServicio(idReserva);
                mPresenter.getMarkers(idReserva);
                break;

            case 13:
                //13 EN EL PUNTO
                UiServicio(EN_EL_PUNTO, USUARIO_CONTACTADO, true, false, false, true);
                nextEstado = estado + 1;
                // nextEstado = 14;
                break;

            case 14:
                //14 USUARIO CONTACTADO11
                UiServicio(USUARIO_CONTACTADO, SERVICIO_EN_PROCESO, true, false, false, true);
                nextEstado = estado + 1;
                //nextEstado = 15;
                break;

            case 15:
                //15 SERVICIO EN PROCESO
                UiServicio(SERVICIO_EN_PROCESO, "FINALIZAR SERVICIO", true, false, false, true);
                nextEstado = estado + 1;
                //nextEstado = 16;
                break;

            case 16:
                //16 SERVICIO FINALIZADO
                UiServicio(SERVICIO_FINALIZADO, "PONERME DISPONIBLE", true, false, false, true);
                nextEstado = 1;
                break;
        }

    }

    public void UiServicio(String nombreServicio, String nombreBoton, boolean btnClick, boolean unlock, boolean switchVisible, boolean btnVisible) {

        tvConectado.setText(nombreServicio);

        if (switchVisible) {
            mySwitch.setVisibility(View.VISIBLE);
        } else {
            mySwitch.setVisibility(View.GONE);
        }

        if (unlock) {
            mainInterface.unlockDrawer();
        } else {
            mainInterface.lockDrawer();
        }

        if (btnVisible) {
            btnEstado.setVisibility(View.VISIBLE);
        } else {
            btnEstado.setVisibility(View.GONE);
        }

        btnEstado.setClickable(btnClick);
        btnEstado.setText(nombreBoton);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
      /*  mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();*/
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 30);
        locationButton.setBackgroundColor(getResources().getColor(R.color.green));
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                enableMyLocation();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        if (dialogogps != null) {
            dialogogps.dismiss();
        }

    }

    @Override
    public void onProviderDisabled(String s) {
        alertafalta();

    }

    public void alertafalta() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    public void alertaNodisponible(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención");
        builder.setMessage("Usted se encuentra con " + msg + " no podrá marcarse como disponible hasta que regularice su situaciión");
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogDisponible.dismiss();
            }
        });
        dialogDisponible = builder.create();
        dialogDisponible.show();
    }

    @Override
    public void onMyLocationChange(Location location) {
        double xvlatitude = location.getLatitude();

        // Getting longitude of the current location
        double xvlongitude = location.getLongitude();

        ylatitude = location.getLatitude();
        ylongitude = location.getLongitude();

        LatLng latLng = new LatLng(xvlatitude, xvlongitude);

        //latitude= String.valueOf(location.getLatitude());
        //longitude= String.valueOf(location.getLongitude());

        // Creating a LatLng object for the current location

        // getZona(latitude, longitude);

        if (visualizamovil == 0) {
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

        if (serEntity != null) {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    DialogInfoWindow dialogInfoWindow = new DialogInfoWindow(getContext(), serEntity);
                    dialogInfoWindow.show();
                    return false;
                }
            });
        }

    }


    protected Bitmap doInBackground(String src) {
        try {
            URL url = new URL(src);
            InputStream is = url.openConnection().getInputStream();
            Bitmap bitMap = BitmapFactory.decodeStream(is);
            return bitMap;

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            mMap.setOnMyLocationChangeListener(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void getEstado(EstadoResponse estadoResponse) {

        idEstado = estadoResponse.getIdEstado();
        idReserva = estadoResponse.getIdReserva();
        validarEstadoDisponible(idEstado);

        //btnEstado.setText(estadoResponse.getDesestado());

    }

    @Override
    public void sendEstadoResponse(String estado) {
        mPresenter.getEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado());
        //tvConectado.setText("DISPONIBLE");
    }

    @Override
    public void sendServicioResponse(ServicioEntity servicioEntity) {
        serEntity = servicioEntity;

    }

    @Override
    public void sendMarkers(ArrayList<MarkersEntity> list) {

    }

    @Override
    public void sendMarker(MarkersEntity markersEntity) {
        LatLng latLngDestino = new LatLng(Double.valueOf(markersEntity.getLatitude()), Double.valueOf(markersEntity.getLongitude()));

        Marker myMarker = mMap.addMarker(new MarkerOptions()
                .position(latLngDestino)
                .snippet(markersEntity.getDireccionReal())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_costos)));    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick(R.id.btn_estado)
    public void onViewClicked() {
        SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), nextEstado);
        mPresenter.sendEstado(sendEstado);
        //mainInterface.lockDrawer();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainInterface = (MainInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MyInterface");
        }
    }
}
