package tmsystem.com.tmsystemdriver.presentation.main;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;

/**
 * Created by kath on 20/12/17.
 */

public class MainContract {
    interface View extends BaseView<Presenter> {
        void getEstado(EstadoResponse estadoResponse);
        void sendEstadoResponse(String estado);
        void sendServicioResponse(ServicioEntity servicioEntity);
        void sendMarkers(ArrayList<MarkersEntity> list);
        void sendMarker(MarkersEntity markersEntity);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void getEstado(int id);
        void sendEstado(SendEstado sendEstado);
        void getServicio(int id);
        void getMarkers(int id);
    }
}
