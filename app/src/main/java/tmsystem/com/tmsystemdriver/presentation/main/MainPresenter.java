package tmsystem.com.tmsystemdriver.presentation.main;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.GetRequest;
import tmsystem.com.tmsystemdriver.data.remote.request.PostRequest;

/**
 * Created by kath on 20/12/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private Context context;
    private final SessionManager mSessionManager;
    private String user,pass;

    public MainPresenter(@NonNull MainContract.View mView, @NonNull Context context) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
        mSessionManager = new SessionManager(context);
    }
    @Override
    public void start() {

    }

    @Override
    public void getEstado(int id) {

        GetRequest getRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<EstadoResponse> call = getRequest.getEstado("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<EstadoResponse>() {
            @Override
            public void onResponse(Call<EstadoResponse> call, Response<EstadoResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getEstado(response.body());
                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener su estado");
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

    @Override
    public void getServicio(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<ServicioEntity> call = postRequest.getServicio("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<ServicioEntity>() {
            @Override
            public void onResponse(Call<ServicioEntity> call, Response<ServicioEntity> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.sendServicioResponse(response.body());
                    mView.showMessage("datos completados");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener su estado");
                }
            }

            @Override
            public void onFailure(Call<ServicioEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });

    }

    @Override
    public void getMarkers(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<ArrayList<MarkersEntity>> call = postRequest.getServicioUbication("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<ArrayList<MarkersEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<MarkersEntity>> call, Response<ArrayList<MarkersEntity>> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    if(response.body().size()>1){
                        mView.sendMarkers(response.body());
                    }else {
                        mView.sendMarker(response.body().get(0));
                    }
                    mView.showMessage("markers completos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener su estado");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MarkersEntity>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }
}
