package tmsystem.com.tmsystemdriver.presentation.costos;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.CostoEntity;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.MessageResponse;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.GetRequest;
import tmsystem.com.tmsystemdriver.data.remote.request.PostRequest;

/**
 * Created by kath on 08/01/18.
 */

public class CostosPresenter implements CostosContract.Presenter {

    private CostosContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public CostosPresenter(CostosContract.View mView, Context context) {
        this.context =context;
        this.mView = mView;
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void startLoad(int id) {
        getCostos(id);
    }

    @Override
    public void getCostos(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<CostosResponse> call = postRequest.getServicioCostos("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<CostosResponse>() {
            @Override
            public void onResponse(Call<CostosResponse> call, Response<CostosResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getDatosCostos(response.body());
                    mView.showMessage("Costos obtenidos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener los costos del servicio");
                }
            }

            @Override
            public void onFailure(Call<CostosResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void getEperaTiempo(int id, int tiempo) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<CostoTiempoEsperaResponse> call = postRequest.getServicioCostosEspera("bearer "+ mSessionManager.getUserToken(), id, tiempo);
        call.enqueue(new Callback<CostoTiempoEsperaResponse>() {
            @Override
            public void onResponse(Call<CostoTiempoEsperaResponse> call, Response<CostoTiempoEsperaResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getDatosEspera(response.body());
                    mView.showMessage("Costos obtenidos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener los costos del servicio");
                }
            }

            @Override
            public void onFailure(Call<CostoTiempoEsperaResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void sendCostos(CostoEntity costoEntity) {
        PostRequest postRequest =
                ServiceFactory.createService(PostRequest.class);
        Call<MessageResponse> call = postRequest.sendCostos("bearer "+ mSessionManager.getUserToken(), costoEntity);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.sendCostosResponse(response.body());
                   // mView.showMessage("envio");
                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                   // mView.showErrorMessage("Ocurrió un error al enviar sus costos");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
               // mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void start() {

    }
}
