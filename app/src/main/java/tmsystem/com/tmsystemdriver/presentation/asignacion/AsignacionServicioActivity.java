package tmsystem.com.tmsystemdriver.presentation.asignacion;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.presentation.main.PermisosActivity;
import tmsystem.com.tmsystemdriver.utils.ReproductorAudio;

public class AsignacionServicioActivity extends AppCompatActivity {

    //Ruta de conexion
    String BASE_URL ="http://200.48.119.46:6668/Service1.svc/";

    //Metodos Sistema
    String METODO_GET_ASIGNACION = "getasignacionservicio/";
    String METODO_POST_ASIGNACION = "postaceptaservicio/";
    String METODO_POST_NO_ACEPTA = "postnoaceptaservicio/";

    int pStatus = 20;
    private Handler handler = new Handler();
    TimerTask mTimerTask;
    Timer t = new Timer();

    TextView tv;

    ProgressDialog pd;

    String xestado;

    ReproductorAudio reproductorAudio;


    private AudioManager myAudioManager;

    TextView lblestado;

    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_servicio);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                +WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                +WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Asignacion Servicio</font>"));


        lblestado = (TextView) findViewById(R.id.lblestado);

        getasignacionservicio(getIMEI());

        progress();

        myAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 50, 0);
        myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);

        reproductorAudio = new ReproductorAudio();

        reproductorAudio.play(getApplicationContext(), R.raw.serviciomensaje, false);


        RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.acepta);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new AlertDialog.Builder(AsignacionServicioActivity.this)
                        .setTitle("Aceptar el servicio")
                        .setMessage("¿Deseas aceptar el servicio?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                              /*  new UpdatePostServicioAsyncTask(getIMEI()).execute();
                                startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();*/
                            }
                        })

                        .setIcon(R.drawable.btnadvertencia)
                        .show();
            }
        });


        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(20); // Secondary Progress
        mProgress.setMax(20); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();*/

        tv = (TextView) findViewById(R.id.tv);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus > 0) {
                    pStatus += -1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                            tv.setText(pStatus + "");

                            if (pStatus==0)
                            {
                                /*new UpdatePostNoServicioAsyncTask(getIMEI()).execute();
                                startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                finish();*/
                            }


                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(1000); //thread will take approx 3 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        notificaciones();
        actualizaparse();

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


    }

    void actualizaparse() {

      /*  try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("tab_asociados");
            query.whereEqualTo("imei", getIMEI());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> objects,
                                 ParseException e) {
                    try {
                        objects.get(0).put("status",  0);

                        objects.get(0).saveInBackground();

                    } catch (Exception e2) {
                    }
                }
            });

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }*/

    }

    void notificaciones()
    {
        NotificationManager nManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        nManager.cancelAll();
    }

    public String getIMEI()
    {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        return device_id;
    }

    private void getasignacionservicio(String imei) {

/*        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + METODO_GET_ASIGNACION + imei, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("getasignacionservicioResult");
                    int valorn = jsonArray.length();
                    if (valorn == 0) {
                        if(pd != null ){
                            pd.dismiss();
                        }
                    } else {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                        xestado = jsonObject.getString("descondestadosistema");

                        lblestado.setText(xestado);

                        colorestado();

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
               // Toast.makeText(getApplicationContext(),
                 //       error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hideProgress();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);*/
    }

    void colorestado(){
        String p = lblestado.getText().toString();

        if( p.equals("ASIG. AUTOMATICA") || p.equals("ALERTA DE SERVICIO") )
        {
            color="#7ec0ee";
            lblestado.setTextColor(Color.parseColor(color));
        }
    }

    void progress()
    {
        pd = new ProgressDialog(AsignacionServicioActivity.this);
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
      /*  startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), PermisosActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class UpdatePostServicioAsyncTask extends AsyncTask<Void, Long, Integer> {
        //regfinruta/{nreserva}/{peaje}/{parqueo}/{tesperamin}
        String ximei;

        public UpdatePostServicioAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
            /*Log.d("LOCATION INSERTA",BASE_URL + METODO_POST_ASIGNACION + ximei);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_ASIGNACION + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            return 1;*/
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

    public class UpdatePostNoServicioAsyncTask extends AsyncTask<Void, Long, Integer> {
        //regfinruta/{nreserva}/{peaje}/{parqueo}/{tesperamin}
        String ximei;

        public UpdatePostNoServicioAsyncTask(String ximei) {
            this.ximei = ximei;
        }

        public int actualizarEstado(String ximei) {
            // Create a new HttpClient and Post Header
           /* Log.d("LOCATION INSERTA",BASE_URL + METODO_POST_NO_ACEPTA + ximei);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    BASE_URL + METODO_POST_NO_ACEPTA + ximei, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            AppController.getInstance().addToRequestQueue(jsonObjReq);
            */
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


    public void onDestroy() {


        super.onDestroy();

        reproductorAudio.stop();

        if(pd != null ){
            pd.dismiss();
        }

    }
}
