package tmsystem.com.tmsystemdriver.presentation.asignacion;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.mobsandgeeks.saripaar.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.core.BaseFragment;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.presentation.auth.dialogs.DialogForgotPassword;
import tmsystem.com.tmsystemdriver.presentation.main.PermisosActivity;
import tmsystem.com.tmsystemdriver.utils.ProgressDialogCustom;
import tmsystem.com.tmsystemdriver.utils.ReproductorAudio;

/**
 * Created by junior on 27/08/16.
 */
public class AsignacionFragment extends BaseFragment implements AsignacionContract.View {

    private static final String TAG = AsignacionActivity.class.getSimpleName();
    private static final int CAMINO_AL_SERVICIO = 12;
    private static final int NO_DISPONIBLE = 2;


    @BindView(R.id.lblestado)
    TextView lblestado;
    @BindView(R.id.circularProgressbar)
    ProgressBar circularProgressbar;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.swipe_btn)
    SwipeButton swipeBtn;
    @BindView(R.id.acepta)
    RelativeLayout acepta;
    Unbinder unbinder;


    ReproductorAudio reproductorAudio;
    private AudioManager myAudioManager;

    int pStatus = 20;
    private Handler handler = new Handler();

    Thread a = new Thread();


    private AsignacionContract.Presenter mPresenter;
    private SessionManager mSessionManager;
    private ProgressDialogCustom mProgressDialogCustom;
    private DialogForgotPassword dialogForgotPassword;
    private Validator validator;
    private boolean isLoading = false;

    public AsignacionFragment() {
        // Requires empty public constructor
    }

    public static AsignacionFragment newInstance() {
        return new AsignacionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_asignacion_servicio, container, false);
        mSessionManager = new SessionManager(getContext());
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAudio();

        swipeBtn.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {

                SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), CAMINO_AL_SERVICIO);
                mPresenter.sendEstado(sendEstado);
               // nextActivity(getActivity(), null, PermisosActivity.class, true);

                startActivity(new Intent(getContext(), PermisosActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                getActivity().finish();

            }
        });

        count();

    }

    public void getAudio(){
        myAudioManager = (AudioManager)getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 50, 0);
        myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);

        reproductorAudio = new ReproductorAudio();

        reproductorAudio.play(getActivity().getApplicationContext(), R.raw.serviciomensaje, false);

    }

    public void count(){

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress = (ProgressBar) getActivity().findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(20); // Secondary Progress
        mProgress.setMax(20); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

/*
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
                                SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), NO_DISPONIBLE);
                                mPresenter.sendEstado(sendEstado);
                                nextActivity(getActivity(), null, PermisosActivity.class, true);
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
        }).start();*/
        a = new Thread(new Runnable() {
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
                                SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(), NO_DISPONIBLE);
                                mPresenter.sendEstado(sendEstado);
                                nextActivity(getActivity(), null, PermisosActivity.class, true);
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
    public void setPresenter(AsignacionContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
    }

    @Override
    public void showMessage(String msg) {
        ((BaseActivity) getActivity()).showMessage(msg);
    }

    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }


    @Override
    public void sendEstadoResponse(String estado) {
        if (estado != null){
            getActivity().finish();
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reproductorAudio.stop();
        unbinder.unbind();
    }

}