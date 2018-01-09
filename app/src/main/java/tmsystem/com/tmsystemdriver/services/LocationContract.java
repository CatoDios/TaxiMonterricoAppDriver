package tmsystem.com.tmsystemdriver.services;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.GpsEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.presentation.seguimiento.SeguimientoContract;

/**
 * Created by kath on 08/01/18.
 */

public class LocationContract {
    interface View extends BaseView<Presenter> {

        void getGpsResponse(String msg);


        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void sendGps(GpsEntity gpsEntity);

    }
}
