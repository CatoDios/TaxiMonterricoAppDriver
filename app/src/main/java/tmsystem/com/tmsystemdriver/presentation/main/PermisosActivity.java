package tmsystem.com.tmsystemdriver.presentation.main;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tmsystem.com.tmsystemdriver.R;

public class PermisosActivity extends AppCompatActivity {
    private Toolbar toolbar;
    Map<String, Integer> perms = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);
      //  getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Permisos App</font>"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Permisos");

       // AppController.getInstance().clearApplicationData();


        if (Build.VERSION_CODES.M >= 23) {
            if (checkAndRequestPermissions()) {

               // Intent serviceIntentx = new Intent(this, LocationService.class);
               // startService(serviceIntentx);

                startActivity(new Intent(getBaseContext(), PrincipalActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();

               // llamarparse();
            }
        } else {

           // Intent serviceIntentx = new Intent(this, LocationService.class);
           // startService(serviceIntentx);

            startActivity(new Intent(getBaseContext(), PrincipalActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();

            //llamarparse();


        }
    }

   /* void llamarparse()
    {
        if (!isParseInitialized) {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("KKS9paK4U6HZtMYIpcKT")
                    .clientKey("8VCMECKU6O4UbR39vAyG")
                    .server("http://200.48.119.43:1337/parse/")
                    .build()
            );

            isParseInitialized = true;


            isParseInitialized = true;
            ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){

                        VidInstallation= ParseInstallation.getCurrentInstallation().getInstallationId();


                    }else{
                        VidInstallation= ParseInstallation.getCurrentInstallation().getInstallationId();
                    }
                }
            });

        }

    }*/

    /*void actualizaparse() {
        if (VidInstallation.equals("")) {

        } else {
            try {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("tab_asociados");
                query.whereEqualTo("imei", getIMEI());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects,
                                     ParseException e) {
                        try {
                            if ((e == null) || (e.toString() == "")) {
                                objects.get(0).put("installation_Id", VidInstallation);
                                objects.get(0).put("status", 0);
                                objects.get(0).saveInBackground();
                            } else {
                                if ((VidInstallation != null) || VidInstallation != "") {
                                    objects.get(0).put("installation_Id", ParseInstallation.getCurrentInstallation().getInstallationId());
                                    objects.get(0).put("status", 0);
                                    objects.get(0).saveInBackground();
                                }
                            }
                        } catch (Exception e2) {
                        }
                    }
                });

            } catch (Exception ex) {
                Log.e("Error: ", ex.getMessage());
            }
        }


    }
*/
/*

    public String getIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        if (device_id == null) {
            device_id = "notavailable";
        } else {
            device_id = tm.getDeviceId();
        }
        return device_id;
    }

*/

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location1Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int cellphonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int readphonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int storagewexternal = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int wakelock = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int accessnetworkstates = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int vibrate = ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE);
        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int account = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (location1Permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (cellphonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (readphonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (storagewexternal != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (wakelock != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WAKE_LOCK);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (accessnetworkstates != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (vibrate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.VIBRATE);
        }

        if (sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (account != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                perms.put(Manifest.permission.WAKE_LOCK, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.VIBRATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                            &&
                            perms.get(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
                       // Intent serviceIntentx = new Intent(this, LocationService.class);
                       // startService(serviceIntentx);

                      /*  Intent serviceIntenta = new Intent(this, TMAsociadosBroadcastReceiver.class);
                        startService(serviceIntenta);*/

                        startActivity(new Intent(getBaseContext(), PrincipalActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();


                    } else {
                        Log.d("PERMISO DENEGADO", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                                ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.VIBRATE)
                                ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)
                                ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)
                                ) {
                            showDialogOK("La aplicación necesita todos los permisos para su correcto funcionamiento", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermissions();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            finish();
                                            break;
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(this, "La aplicación necesita todos los permisos para su correcto funcionamiento", Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        }
                    }
                }
            }
        }
    }

    public boolean verificarPermisos(){

        if(perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                &&
                perms.get(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", okListener)
                .setCancelable(false)
                .create()
                .show();
    }
}