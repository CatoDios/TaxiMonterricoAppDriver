package tmsystem.com.tmsystemdriver.presentation.requisitos;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;

/**
 * Created by kath on 08/01/18.
 */

public class RequisitosContract {
    interface View extends BaseView<Presenter> {

        void getListRequisitos(ArrayList<RequisitosResponse> list);


        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void startLoad(int id);

        void getRequisitos(int id);

    }

}
