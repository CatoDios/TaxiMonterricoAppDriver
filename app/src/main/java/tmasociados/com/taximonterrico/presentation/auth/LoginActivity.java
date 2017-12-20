package tmasociados.com.taximonterrico.presentation.auth;

import android.os.Bundle;

import tmasociados.com.taximonterrico.R;
import tmasociados.com.taximonterrico.core.BaseActivity;
import tmasociados.com.taximonterrico.utils.ActivityUtils;


public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        LoginFragment fragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = LoginFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
        new LoginPresenter(fragment,this);
    }


}
