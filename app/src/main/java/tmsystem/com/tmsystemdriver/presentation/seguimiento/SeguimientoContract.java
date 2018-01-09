package tmsystem.com.tmsystemdriver.presentation.seguimiento;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;

/**
 * Created by kath on 08/01/18.
 */

public class SeguimientoContract {
    interface View extends BaseView<Presenter> {

        void getListSeguimiento(ArrayList<SeguimientoResponse> list);

        void clickItemSeguimiento(SeguimientoResponse seguimientoResponse);

        void sendSeguimientoResponse(String msg);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void startLoad(int id);

        void getSeguimiento( int id);

        void sendSeguimiento(SeguimientoEntity seguimientoEntity);

    }

}
