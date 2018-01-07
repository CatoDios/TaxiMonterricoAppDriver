package tmsystem.com.tmsystemdriver.presentation.main;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.data.models.ServicioPersonalEntity;
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
                    mView.getEstadoResponse(response.body());
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
                    mView.getServicioResponse(response.body());
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
                        mView.getMarKers(response.body());
                    }else {
                        mView.getMarker(response.body().get(0));
                    }
                    mView.showMessage("markers completos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener su ruta");
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

    @Override
    public void getServiciosPersonal(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<ServicioPersonalEntity> call = postRequest.getServicioPersonal("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<ServicioPersonalEntity>() {
            @Override
            public void onResponse(Call<ServicioPersonalEntity> call, Response<ServicioPersonalEntity> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getServicioPersonalResponse(response.body());
                    mView.showMessage("datos completados");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener servicio personal");
                }
            }

            @Override
            public void onFailure(Call<ServicioPersonalEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void getServiciosSeguimiento(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<SeguimientoResponse> call = postRequest.getServicioSeguimiento("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<SeguimientoResponse>() {
            @Override
            public void onResponse(Call<SeguimientoResponse> call, Response<SeguimientoResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getServSeguimientoResponse(response.body());
                    mView.showMessage("seguimiento");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener el seguimiento");
                }
            }

            @Override
            public void onFailure(Call<SeguimientoResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void getServiciosRequisitos(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<RequisitosResponse> call = postRequest.getServicioRequisitos("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<RequisitosResponse>() {
            @Override
            public void onResponse(Call<RequisitosResponse> call, Response<RequisitosResponse> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getServRequisitosResponse(response.body());
                    mView.showMessage("requisitos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener los requisitos del servicio");
                }
            }

            @Override
            public void onFailure(Call<RequisitosResponse> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void getServiciosCostos(int id) {
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
                    mView.getServCostosResponse(response.body());
                    mView.showMessage("costos");

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
    public void getServiciosCostosEspera(int id, int tiempo) {
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
                    mView.getServCostosEsperaResponse(response.body());
                    mView.showMessage("costos");

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
}
