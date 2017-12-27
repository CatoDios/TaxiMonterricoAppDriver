package tmasociados.com.taximonterrico.presentation.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;

import tmasociados.com.taximonterrico.R;
import tmasociados.com.taximonterrico.core.BaseActivity;
import tmasociados.com.taximonterrico.data.local.SessionManager;
import tmasociados.com.taximonterrico.presentation.main.PermisosActivity;
import tmasociados.com.taximonterrico.presentation.main.PrincipalActivity;

/**
 * Created by kath on 27/12/17.
 */

public class LoadActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        if (savedInstanceState == null)
            initialProcess();
    }

    private void initialProcess() {
        SessionManager mSessionManager = new SessionManager(getApplicationContext());
        if(mSessionManager.isLogin()){

            next(this,null, PermisosActivity.class, true);
        }else{
            next(this,null, LoginActivity.class, true);
        }
    }

}
