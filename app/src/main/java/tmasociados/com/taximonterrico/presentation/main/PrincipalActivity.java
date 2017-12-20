package tmasociados.com.taximonterrico.presentation.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tmasociados.com.taximonterrico.R;
import tmasociados.com.taximonterrico.core.BaseActivity;
import tmasociados.com.taximonterrico.data.local.SessionManager;
import tmasociados.com.taximonterrico.data.models.UserEntity;

/**
 * Created by kath on 20/12/17.
 */

public class PrincipalActivity extends BaseActivity {

    DrawerLayout mDrawer;
    NavigationView navigationView;
    SessionManager mSessionManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    public TextView tv_username;
    public TextView tv_email;
    public UserEntity mUser;
    public ImageView imageView;
    //private TicketsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mSessionManager = new SessionManager(this);

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation);


        /**
         * Setup click events on the Navigation View Items.
         */

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Mis tickets");

        setupDrawerContent(navigationView);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                mDrawer,                    /* DrawerLayout object */
                toolbar,
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        );
        mDrawerToggle.syncState();
        mDrawer.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
        View header = navigationView.getHeaderView(0);

        tv_username = (TextView) header.findViewById(R.id.tv_fullnanme);
        tv_email = (TextView) header.findViewById(R.id.lblemail);
        imageView = (ImageView) header.findViewById(R.id.imgConductor);
        initHeader();
/*
        fragment = (TicketsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = TicketsFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        new TicketsPresenter(fragment, this);*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateProfile(UserEntity userEntity) {
        if (userEntity != null) {
            tv_username.setText(userEntity.getFullName());
            tv_email.setText(userEntity.getEmail());
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(false);
                        menuItem.setCheckable(false);

                        switch (menuItem.getItemId()) {
                            case R.id.action_profile:
                                Intent intent = new Intent(TicketsActivity.this , ProfileActivity.class);
                                startActivityForResult(intent, 7);
                                break;
                            case R.id.action_buy:
                                next(TicketsActivity.this, null, CountriesActivity.class, false);
                                break;
                            case R.id.action_help:
                                next(TicketsActivity.this, null, SlideActivity.class, false);
                                break;
                            case R.id.action_info:
                                next(TicketsActivity.this,null, AboutUsActivity.class, false);
                                break;
                            case R.id.action_signout:
                                CloseSession();
                                break;

                            default:

                                break;
                        }
                        menuItem.setChecked(false);
                        //  mDrawer.closeDrawers();
                        return true;
                    }
                });
    }

    private void CloseSession() {
        mSessionManager.closeSession();
        AccessToken.setCurrentAccessToken(null);
        newActivityClearPreview(this, null, LoginActivity.class);

    }


    public void initHeader() {

        mUser = mSessionManager.getUserEntity();
        if (mUser != null) {
            tv_username.setText(mUser.getFullName());
            tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(TicketsActivity.this, null, ProfileActivity.class, false);
                }
            });
            tv_email.setText(mUser.getEmail());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(TicketsActivity.this, null, ProfileActivity.class, false);
                }
            });

            if (mUser.getPicture() != null) {
                Glide.with(this)
                        .load(mSessionManager.getUserEntity().getPicture())
                        .transform(new CircleTransform(this))
                        .into(imageView);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 200:
                    initHeader();
                    break;
                case 7:
                    Glide.with(this)
                            .load(mSessionManager.getUserEntity().getPicture())
                            .transform(new CircleTransform(this))
                            .into(imageView);
                    break;


            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
