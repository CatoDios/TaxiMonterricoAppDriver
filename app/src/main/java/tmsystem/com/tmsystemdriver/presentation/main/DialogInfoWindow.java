package tmsystem.com.tmsystemdriver.presentation.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.data.models.ServicioEntity;

/**
 * Created by junior on 08/06/16.
 */
public class DialogInfoWindow extends AlertDialog {


    String tlf;
    @BindView(R.id.tv_fecha)
    TextView tvFecha;
    @BindView(R.id.tv_cliente)
    TextView tvCliente;
    @BindView(R.id.btn_call)
    ImageButton btnCall;
    @BindView(R.id.tv_hora)
    TextView tvHora;
    @BindView(R.id.tv_centro_costos)
    TextView tvCentroCostos;
    @BindView(R.id.btn_message)
    ImageButton btnMessage;
    @BindView(R.id.tv_observaciones)
    TextView tvObservaciones;
    @BindView(R.id.tv_vale)
    TextView tvVale;
    @BindView(R.id.tv_desde)
    TextView tvDesde;
    @BindView(R.id.tv_persona)
    TextView tvPersona;
    @BindView(R.id.tv_hasta)
    TextView tvHasta;
    @BindView(R.id.btn_maps)
    ImageButton btnMaps;
    @BindView(R.id.btn_gps)
    ImageButton btnGps;


    private Double latitude, longitude;


    public DialogInfoWindow(Context context, ServicioEntity servicioEntity, Double getlatitude, Double getlongitude) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.layout_infowindow, null);
        ButterKnife.bind(this, view);
        setView(view);

        tvFecha.setText(servicioEntity.getFechaReserva());
        tvCliente.setText(servicioEntity.getCliente());
        tvCentroCostos.setText(servicioEntity.getCentroCostos());
        tvObservaciones.setText(servicioEntity.getObservaciones());
        tvVale.setText(servicioEntity.getNvale());
        tvPersona.setText(servicioEntity.getPersonalTraslado());
        tvDesde.setText(servicioEntity.getRutainicio());
        tvHasta.setText(servicioEntity.getRutafinal());

        latitude = getlatitude;
        longitude = getlongitude;

    }


    @OnClick({R.id.btn_call, R.id.btn_message,R.id.btn_maps,R.id.btn_gps})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:992406360"));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                view.getContext().startActivity(intent);
                break;

            case R.id.btn_message:
                Intent intent_message = new Intent(Intent.ACTION_SEND);
                intent_message.setData(Uri.parse("tel:992406360"));
                view.getContext().startActivity(intent_message);
                break;

            case R.id.btn_maps:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                getContext().startActivity(i);
                break;

            case R.id.btn_gps:
                try {
                    String url = "waze://?ll=" + latitude + "," + longitude + "&navigate=yes";
                    getContext().startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(url)));
                } catch (ActivityNotFoundException ex) {
                    Intent intent_waze =
                            new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    getContext().startActivity(intent_waze);
                }
                break;

        }
    }

}
