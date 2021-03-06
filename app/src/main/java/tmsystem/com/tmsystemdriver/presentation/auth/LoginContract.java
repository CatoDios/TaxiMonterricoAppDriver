package tmsystem.com.tmsystemdriver.presentation.auth;


import tmsystem.com.tmsystemdriver.core.BasePresenter;
import tmsystem.com.tmsystemdriver.core.BaseView;
import tmsystem.com.tmsystemdriver.data.models.AccessTokenEntity;
import tmsystem.com.tmsystemdriver.data.models.UserEntity;

/**
 * Created by katherine on 12/05/17.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccessful(UserEntity userEntity);
        void errorLogin(String msg);
        void showDialogForgotPassword();
        void showSendEmail(String email);
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void loginUser(String username, String password);
        void getProfile(AccessTokenEntity token);
        void openSession(AccessTokenEntity token, UserEntity userEntity);
        void sendEmail(String email);

    }
}
