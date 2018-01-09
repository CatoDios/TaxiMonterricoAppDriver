package tmsystem.com.tmsystemdriver.presentation.seguimiento;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.utils.ActivityUtils;

/**
 * Created by kath on 08/01/18.
 */

public class SeguimientoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.body)
    FrameLayout body;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        ButterKnife.bind(this);


        toolbar.setTitle("SEGUIMIENTO");
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        SeguimientoFragment fragment = (SeguimientoFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = SeguimientoFragment.newInstance(getIntent().getExtras());

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }

        // Create the presenter
        // new LoginPresenter(fragment,this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
