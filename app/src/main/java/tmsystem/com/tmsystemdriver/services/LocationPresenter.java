package tmsystem.com.tmsystemdriver.services;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.GpsEntity;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.PostRequest;
import tmsystem.com.tmsystemdriver.presentation.auth.LoginContract;
import tmsystem.com.tmsystemdriver.presentation.seguimiento.SeguimientoContract;

/**
 * Created by kath on 08/01/18.
 */

public class LocationPresenter implements LocationContract.Presenter {

    private LocationContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public LocationPresenter(LocationContract.View mView, Context context) {
        this.context =context;
        this.mView = mView;
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void sendGps(GpsEntity gpsEntity) {
        PostRequest postRequest =
                ServiceFactory.createService(PostRequest.class);
        Call<String> call = postRequest.sendGPS("bearer "+ mSessionManager.getUserToken(), gpsEntity);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getGpsResponse(response.body());
                    mView.showMessage("enviado");
                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al enviar su gps");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
            }
        });
    }

    @Override
    public void start() {

    }
}
