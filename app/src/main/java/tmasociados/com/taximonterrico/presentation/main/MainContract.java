package tmasociados.com.taximonterrico.presentation.main;

import tmasociados.com.taximonterrico.core.BasePresenter;
import tmasociados.com.taximonterrico.core.BaseView;
import tmasociados.com.taximonterrico.data.models.AccessTokenEntity;
import tmasociados.com.taximonterrico.data.models.EstadoResponse;
import tmasociados.com.taximonterrico.data.models.UserEntity;
import tmasociados.com.taximonterrico.presentation.auth.LoginContract;

/**
 * Created by kath on 20/12/17.
 */

public class MainContract {
    interface View extends BaseView<Presenter> {
        void getEstado(EstadoResponse estadoResponse);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void getEstado(int id);
    }
}
