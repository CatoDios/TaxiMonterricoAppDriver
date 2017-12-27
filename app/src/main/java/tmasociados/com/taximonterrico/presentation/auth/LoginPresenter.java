package tmasociados.com.taximonterrico.presentation.auth;

import android.content.Context;
import android.support.annotation.NonNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tmasociados.com.taximonterrico.data.local.SessionManager;
import tmasociados.com.taximonterrico.data.models.AccessTokenEntity;
import tmasociados.com.taximonterrico.data.models.UserEntity;
import tmasociados.com.taximonterrico.data.remote.ServiceFactory;
import tmasociados.com.taximonterrico.data.remote.request.LoginRequest;

/**
 * Created by katherine on 10/05/17.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mView;
    private Context context;
    private final SessionManager mSessionManager;
    private String user,pass;

    public LoginPresenter(@NonNull LoginContract.View mView, @NonNull Context context) {
        this.context = context;
        this.mView = mView;
        this.mView.setPresenter(this);
        mSessionManager = new SessionManager(context);
    }

    @Override
    public void loginUser(String username, String password) {
        user = username;
        pass = password;
        LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<AccessTokenEntity> call = loginService.login(username,password, "password");
        mView.setLoadingIndicator(true);
        call.enqueue(new Callback<AccessTokenEntity>() {
            @Override
            public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                if(!mView.isActive()){
                    return;
                }
                mView.setLoadingIndicator(false);

                if (response.isSuccessful()) {

                    getProfile(response.body());

                } else {
                    mView.setLoadingIndicator(false);
                    mView.errorLogin("login fallido");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                if(!mView.isActive()){
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.errorLogin("No se puede conectar al servidor");
            }
        });
    }

    @Override
    public void getProfile(final AccessTokenEntity token) {
       LoginRequest loginService =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginService.getUser("bearer "+ token.getAccess_token(), user, pass);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    if (!mView.isActive()) {
                        return;
                    }
                    openSession(token, response.body());

                } else {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.setLoadingIndicator(false);
                    mView.errorLogin("Ocurrió un error al cargar su perfil");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.errorLogin("Fallo al traer datos, comunicarse con su administrador");
            }
        });
    }



    @Override
    public void openSession(AccessTokenEntity token, UserEntity userEntity) {

        mSessionManager.openSession(token);
        mSessionManager.setUser(userEntity);
        mView.setLoadingIndicator(false);
        mView.loginSuccessful(userEntity);
    }

    @Override
    public void sendEmail(String email) {
       /* LoginRequest loginRequest =
                ServiceFactory.createService(LoginRequest.class);
        Call<UserEntity> call = loginRequest.recovery(email);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                if (response.isSuccessful()) {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.showMessage("Se envió un correo con instrucciones");
                } else {
                    if (!mView.isActive()) {
                        return;
                    }
                    mView.setLoadingIndicator(false);
                    mView.showErrorMessage("Ocurrió un error al enviar el correo a su dirección");
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setLoadingIndicator(false);
                mView.showErrorMessage("Fallo al traer datos, comunicarse con su administrador");
            }
        });*/
    }

    @Override
    public void start() {

    }
}
