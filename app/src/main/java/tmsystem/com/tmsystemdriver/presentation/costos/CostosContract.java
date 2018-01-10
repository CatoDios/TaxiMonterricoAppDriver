package tmsystem.com.tmsystemdriver.presentation.costos;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.CostoEntity;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.MessageResponse;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;

/**
 * Created by kath on 08/01/18.
 */

public class CostosContract {
    interface View extends BaseView<Presenter> {

        void getDatosCostos(CostosResponse costosResponse);
        void getDatosEspera(CostoTiempoEsperaResponse costoTiempoEsperaResponse);

        void sendCostosResponse(MessageResponse msg);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void startLoad(int id);

        void getCostos(int id);
        void getEperaTiempo(int id, int tiempo);

        void sendCostos(CostoEntity costoEntity);

    }

}
