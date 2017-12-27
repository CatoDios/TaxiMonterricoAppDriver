package tmasociados.com.taximonterrico.presentation.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobsandgeeks.saripaar.Validator;

import butterknife.ButterKnife;
import tmasociados.com.taximonterrico.R;
import tmasociados.com.taximonterrico.core.BaseFragment;
import tmasociados.com.taximonterrico.presentation.auth.LoginFragment;
import tmasociados.com.taximonterrico.presentation.auth.dialogs.DialogForgotPassword;
import tmasociados.com.taximonterrico.utils.PermissionUtils;
import tmasociados.com.taximonterrico.utils.ProgressDialogCustom;

/**
 * Created by kath on 20/12/17.
 */

public class MainFragment extends BaseFragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationChangeListener,
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private boolean mPermissionDenied = false;
    SupportMapFragment mapFragment;

    private AlertDialog dialogogps, alertverficaversion;
    private LocationManager locationManager;
    private String provider;
    GoogleApiClient googleApiClient;
    LocationRequest mLocationRequest;

    double ylatitude;
    double ylongitude;
    int visualizamovil=0;
    GoogleApiClient mGoogleApiClient;


    private GoogleMap mMap;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
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
    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
      /*  mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();*/
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                enableMyLocation();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
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
                        buildGoogleApiClient();                    }
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

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));



            // Showing the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            int speed = (int) ((location.getSpeed() * 3600) / 1000);
        }

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
}
