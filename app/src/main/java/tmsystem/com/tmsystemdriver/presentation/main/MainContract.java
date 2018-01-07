package tmsystem.com.tmsystemdriver.presentation.main;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.data.models.ServicioPersonalEntity;

/**
 * Created by kath on 20/12/17.
 */

public class MainContract {
    interface View extends BaseView<Presenter> {
        void getEstadoResponse(EstadoResponse estadoResponse);
        void getServicioResponse(ServicioEntity servicioEntity);
        void getMarkers(ArrayList<MarkersEntity> list);
        void getMarker(MarkersEntity markersEntity);

        void getServicioPersonalResponse(ServicioPersonalEntity servicioPersonalEntity);
        void getServSeguimientoResponse(SeguimientoResponse seguimientoResponse);
        void getServRequisitosResponse(RequisitosResponse requisitosResponse);
        void getServCostosResponse(CostosResponse costosResponse);
        void getServCostosEsperaResponse(CostoTiempoEsperaResponse costoTiempoEsperaResponse);

        void sendEstadoResponse(String estado);


        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void getEstado(int id);
        void sendEstado(SendEstado sendEstado);
        void getServicio(int id);
        void getMarkers(int id);
        void getServiciosPersonal(int id);
        void getServiciosSeguimiento(int id);
        void getServiciosRequisitos(int id);
        void getServiciosCostos(int id);
        void getServiciosCostosEspera(int id, int tiempo);

    }
}
