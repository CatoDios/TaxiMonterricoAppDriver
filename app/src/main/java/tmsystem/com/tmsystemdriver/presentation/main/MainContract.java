package tmsystem.com.tmsystemdriver.presentation.main;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;

/**
 * Created by kath on 20/12/17.
 */

public class MainContract {
    interface View extends BaseView<Presenter> {
        void getEstado(EstadoResponse estadoResponse);
        void sendEstadoResponse(String estado);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void getEstado(int id);
        void sendEstado(SendEstado sendEstado);
    }
}
