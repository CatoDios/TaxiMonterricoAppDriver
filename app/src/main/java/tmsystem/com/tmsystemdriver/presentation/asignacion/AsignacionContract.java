package tmsystem.com.tmsystemdriver.presentation.asignacion;


import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.AccessTokenEntity;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.UserEntity;

/**
 * Created by katherine on 12/05/17.
 */

public interface AsignacionContract {
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
