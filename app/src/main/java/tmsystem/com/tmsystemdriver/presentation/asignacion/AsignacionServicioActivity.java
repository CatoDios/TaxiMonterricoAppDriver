package tmsystem.com.tmsystemdriver.presentation.asignacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;

import java.util.Timer;
import java.util.TimerTask;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.presentation.main.PermisosActivity;
import tmsystem.com.tmsystemdriver.utils.ReproductorAudio;

public class AsignacionServicioActivity extends AppCompatActivity implements AsignacionContract.View {

    int pStatus = 20;
    private Handler handler = new Handler();

    TextView tv;

    ProgressDialog pd;

    private boolean isSend = false;

    ReproductorAudio reproductorAudio;

    private SessionManager mSessionManager;
    private AsignacionContract.Presenter mPresenter;


    private AudioManager myAudioManager;

    private int idReserva;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_asignacion_servicio);

        SwipeButton swipeBtn = (SwipeButton) findViewById(R.id.swipe_btn);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                +WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                +WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mPresenter = new AsignacionPresenter(this, getApplicationContext());
        mSessionManager = new SessionManager(getApplicationContext());

        myAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 50, 0);
        myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);

        reproductorAudio = new ReproductorAudio();

        reproductorAudio.play(getApplicationContext(), R.raw.serviciomensaje, true);

        swipeBtn.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                 isSend = true;
                SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), 12, idReserva);
                mPresenter.sendEstado(sendEstado);
                // nextActivity(getActivity(), null, PermisosActivity.class, true);

                startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();

            }
        });

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(20); // Secondary Progress
        mProgress.setMax(20); // Maximum Progress
        mProgress.setProgressDrawable(drawable);


        tv = (TextView) findViewById(R.id.tv);

        Thread a = new Thread(new Runnable() {
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
                                if (!isSend){

                                    SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), 2, idReserva);
                                    mPresenter.sendEstado(sendEstado);
                                    startActivity(new Intent(getBaseContext(), PermisosActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    finish();
                                }
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
        });

        a.start();


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

    @Override
    public void getEstado(EstadoResponse estadoResponse) {
        idReserva = estadoResponse.getIdReserva();

    }

    @Override
    public void sendEstadoResponse(String estado) {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setPresenter(AsignacionContract.Presenter presenter) {
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

    }

    public void onDestroy() {


        super.onDestroy();

        reproductorAudio.stop();

        if(pd != null ){
            pd.dismiss();
        }

    }
}
