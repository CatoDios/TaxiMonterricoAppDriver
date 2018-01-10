package tmsystem.com.tmsystemdriver.presentation.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.data.local.SessionManager;
import tmsystem.com.tmsystemdriver.data.models.EstadoResponse;
import tmsystem.com.tmsystemdriver.data.models.MarkersEntity;
import tmsystem.com.tmsystemdriver.data.models.SendEstado;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;
import tmsystem.com.tmsystemdriver.data.models.ServicioPersonalEntity;
import tmsystem.com.tmsystemdriver.data.models.UserEntity;
import tmsystem.com.tmsystemdriver.presentation.auth.LoginActivity;
import tmsystem.com.tmsystemdriver.utils.ActivityUtils;

/**
 * Created by kath on 20/12/17.
 */

public class PrincipalActivity extends BaseActivity  implements  MainInterface, MainContract.View{

    DrawerLayout mDrawer;
    NavigationView navigationView;
    SessionManager mSessionManager;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    public TextView tv_username;
    public TextView tv_placa;
    public UserEntity mUser;
    public ImageView imageView;
    public ImageView imageCar;
    private MainFragment fragment;
    private ActionBarDrawerToggle drawerToggle;

    private MainContract.Presenter mPresenter;

    private boolean aCasa = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        mSessionManager = new SessionManager(this);
        mPresenter = new MainPresenter(this, getApplicationContext());

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

        toolbar.setTitle("Conectado");
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

        tv_username = (TextView) header.findViewById(R.id.tv_name);
        tv_placa = (TextView) header.findViewById(R.id.tv_placa);
        imageView = (ImageView) header.findViewById(R.id.imgConductor);
        imageCar = (ImageView) header.findViewById(R.id.imgCar);

        initHeader();
        //findViewById(R.id.appbar).bringToFront();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        findViewById(R.id.appbar).setOutlineProvider(null);

        fragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.body);

        if (fragment == null) {
            fragment = MainFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.body);
        }
        //new TicketsPresenter(fragment, this);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(false);
                        menuItem.setCheckable(false);

                        switch (menuItem.getItemId()) {
                            case R.id.ac_inicio:
                                //Intent intent = new Intent(TicketsActivity.this , ProfileActivity.class);
                                //startActivityForResult(intent, 7);
                                break;

                            case R.id.ac_servicio:
                                // next(TicketsActivity.this, null, CountriesActivity.class, false);
                                break;

                            case R.id.ac_ganancia:
                                //next(TicketsActivity.this, null, SlideActivity.class, false);
                                break;

                            case R.id.ac_chat:
                                //next(TicketsActivity.this,null, AboutUsActivity.class, false);
                                break;

                            case R.id.ac_pagos:
                                CloseSession();
                                break;

                            case R.id.ac_perfil:
                                //Intent intent = new Intent(TicketsActivity.this , ProfileActivity.class);
                                //startActivityForResult(intent, 7);
                                break;

                            case R.id.ac_casa:
                                aCasa = true;  SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(),4, 0);
                                mPresenter.sendEstado(sendEstado);

                                //next(TicketsActivity.this, null, CountriesActivity.class, false);
                                break;

                            case R.id.ac_salir:
                                salirapp();
                                break;

                            case R.id.ac_termino:
                                break;

                            case R.id.ac_politicas:
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
        //AccessToken.setCurrentAccessToken(null);
        newActivityClearPreview(this, null, LoginActivity.class);

    }


    public void initHeader() {

        mUser = mSessionManager.getUserEntity();
        if (mUser != null) {
            tv_username.setText(mUser.getMovil().getMovil());
           /* tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(MainActivity.this, null, ProfileActivity.class, false);
                }
            });*/
            tv_placa.setText(mUser.getMovil().getNplaca());
            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    next(TicketsActivity.this, null, ProfileActivity.class, false);
                }
            });*/

            if (mUser.getAsociado().getImaasoc() != null) {
                Glide.with(this)
                        .load(mSessionManager.getUserEntity().getAsociado().getImaasoc())
                        //.transform(new CircleTransform(this))
                        .into(imageView);
            }

            if (mUser.getMovil().getFotmovilla() != null) {
                Glide.with(this)
                        .load(mSessionManager.getUserEntity().getMovil().getFotmovilla())
                        //.transform(new CircleTransform(this))
                        .into(imageCar);
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
                   /* Glide.with(this)
                            .load(mSessionManager.getUserEntity().getPicture())
                            .transform(new CircleTransform(this))
                            .into(imageView);*/
                    break;


            }
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem itemSwitch = menu.findItem(R.id.myswitch);
        itemSwitch.setActionView(R.layout.switch_toolbar);
        final Switch sw = (Switch) menu.findItem(R.id.myswitch).getActionView().findViewById(R.id.switchForActionBar);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(PrincipalActivity.this, "Activo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public void onBackPressed() {
        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void salirapp() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseas cerrar sesion?")
                .setTitle("Cerrar Sesion")
                .setIcon(R.drawable.btnadvertencia)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SendEstado sendEstado = new SendEstado(mSessionManager.getUserEntity().getAsociado().getIdasociado(),3, 0);
                        mPresenter.sendEstado(sendEstado);
                        CloseSession();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // some code if you want
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void lockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void unlockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbar.setVisibility(View.VISIBLE);

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void getEstadoResponse(EstadoResponse estadoResponse) {

    }

    @Override
    public void getServicioResponse(ServicioEntity servicioEntity) {

    }

    @Override
    public void getMarkers(ArrayList<MarkersEntity> list) {

    }

    @Override
    public void getMarker(MarkersEntity markersEntity) {

    }

    @Override
    public void getServicioPersonalResponse(ServicioPersonalEntity servicioPersonalEntity) {

    }

    @Override
    public void sendEstadoResponse(String msg) {
        if (aCasa){
            Toast.makeText(this, "A mi domicilio", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
