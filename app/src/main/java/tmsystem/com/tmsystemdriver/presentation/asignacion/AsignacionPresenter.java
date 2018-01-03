package tmsystem.com.tmsystemdriver.presentation.asignacion;

import android.content.Context;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.AccessTokenEntity;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.UserEntity;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.LoginRequest;
import tmsystem.com.tmsystemdriver.data.remote.request.PostRequest;

/**
 * Created by katherine on 10/05/17.
 */

public class AsignacionPresenter implements AsignacionContract.Presenter {

    private final AsignacionContract.View mView;
    private Context context;
    private final SessionManager mSessionManager;
    private String user,pass;

    public AsignacionPresenter(@NonNull AsignacionContract.View mView, @NonNull Context context) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
        mSessionManager = new SessionManager(context);
    }


    @Override
    public void start() {

    }

    @Override
    public void sendEstado(SendEstado sendEstado) {
        PostRequest postRequest =
                ServiceFactory.createService(PostRequest.class);
        Call<EstadoResponse> call = postRequest.sendEstado("bearer "+ mSessionManager.getUserToken(), sendEstado);
        call.enqueue(new Callback<EstadoResponse>() {
            @Override
            public void onResponse(Call<EstadoResponse> call, Response<EstadoResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.sendEstadoResponse(response.body().getDesestado());
                    mView.showMessage("envio");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al enviar su estado");
                }
            }

            @Override
            public void onFailure(Call<EstadoResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }
}
