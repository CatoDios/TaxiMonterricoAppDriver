package tmsystem.com.tmsystemdriver.presentation.seguimiento;

import android.content.Context;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.data.remote.ServiceFactory;
import tmsystem.com.tmsystemdriver.data.remote.request.GetRequest;

/**
 * Created by kath on 08/01/18.
 */

public class SeguimientoPresenter implements SeguimientoContract.Presenter , SeguimientoItem {

    private SeguimientoContract.View mView;
    private Context context;
    private SessionManager mSessionManager;

    public SeguimientoPresenter(SeguimientoContract.View mView, Context context) {
        this.context =context;
        this.mView = mView;
        this.mView.setPresenter(this);
        this.mSessionManager = new SessionManager(this.context);
    }

    @Override
    public void clickItem(SeguimientoResponse seguimientoResponse) {
        mView.clickItemSeguimiento(seguimientoResponse);
    }

    @Override
    public void deleteItem(SeguimientoResponse seguimientoResponse, int position) {

    }

    @Override
    public void startLoad(int id) {
        getSeguimiento(id);

    }

    @Override
    public void getSeguimiento(int id) {
        GetRequest postRequest =
                ServiceFactory.createService(GetRequest.class);
        Call<ArrayList<SeguimientoResponse>> call = postRequest.getServicioSeguimiento("bearer "+ mSessionManager.getUserToken(), id);
        call.enqueue(new Callback<ArrayList<SeguimientoResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<SeguimientoResponse>> call, Response<ArrayList<SeguimientoResponse>> response) {
                if (!mView.isActive()) {
                    return;
                }

                if (response.isSuccessful()) {
                    mView.getListSeguimiento(response.body());
                    mView.showMessage("seguimiento");

                    //openSession(token, response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al obtener el seguimiento");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SeguimientoResponse>> call, Throwable t) {
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
