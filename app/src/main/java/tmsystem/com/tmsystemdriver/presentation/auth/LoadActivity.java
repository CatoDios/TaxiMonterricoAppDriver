package tmsystem.com.tmsystemdriver.presentation.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.presentation.main.PermisosActivity;

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
