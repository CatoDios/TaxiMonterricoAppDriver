package tmsystem.com.tmsystemdriver.presentation.requisitos;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.GetRequest;

/**
 * Created by kath on 08/01/18.
 */

public class RequisitosPresenter implements RequisitosContract.Presenter {

    private RequisitosContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public RequisitosPresenter(RequisitosContract.View mView, Context context) {
        this.context =context;
        this.mView = mView;
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }


    @Override
    public void startLoad(int id) {
        getRequisitos(id);

    }

    @Override
    public void getRequisitos(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<ArrayList<RequisitosResponse>> call = postRequest.getServicioRequisitos("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<ArrayList<RequisitosResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RequisitosResponse>> call, Response<ArrayList<RequisitosResponse>> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getListRequisitos(response.body());
                    mView.showMessage("Requisitos Obtenidos");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener el seguimiento");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RequisitosResponse>> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }

    @Override
    public void start() {

    }
}
