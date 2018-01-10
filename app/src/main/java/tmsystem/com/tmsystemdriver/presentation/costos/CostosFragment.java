package tmsystem.com.tmsystemdriver.presentation.costos;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.core.BaseFragment;
import tmsystem.com.tmsystemdriver.data.models.CostoEntity;
import tmsystem.com.tmsystemdriver.data.models.CostoTiempoEsperaResponse;
import tmsystem.com.tmsystemdriver.data.models.CostosResponse;
import tmsystem.com.tmsystemdriver.data.models.MessageResponse;
import tmsystem.com.tmsystemdriver.utils.ProgressDialogCustom;

/**
 * Created by kath on 08/01/18.
 */

public class CostosFragment extends BaseFragment implements CostosContract.View {


    @BindView(R.id.et_tipo_pago)
    EditText etTipoPago;
    @BindView(R.id.et_vale)
    EditText etVale;
    @BindView(R.id.et_peaje)
    EditText etPeaje;
    @BindView(R.id.et_parqueo)
    EditText etParqueo;
    @BindView(R.id.et_tiempo_espera)
    EditText etTiempoEspera;
    @BindView(R.id.et_espera_costo)
    EditText etEsperaCosto;
    @BindView(R.id.et_tarifa)
    EditText etTarifa;
    @BindView(R.id.et_ruta)
    EditText etRuta;
    Unbinder unbinder;
    @BindView(R.id.btn_guardar)
    Button btnGuardar;
    @BindView(R.id.relative)
    RelativeLayout relative;

    private CostosContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;
    private int id;

    CostoEntity costoEntity;

    public CostosFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(id);
    }

    public static CostosFragment newInstance(Bundle bundle) {
        CostosFragment fragment = new CostosFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new CostosPresenter(this, getContext());
        id = (int) getArguments().getSerializable("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_costos, container, false);
        unbinder = ButterKnife.bind(this, root);

        etTiempoEspera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tiempo = etTiempoEspera.getText().toString();
                if(tiempo.length() == 0){
                    tiempo = "0";
                }

                if(Integer.valueOf(tiempo)!= 0 ){
                    mPresenter.getEperaTiempo(id, Integer.valueOf(tiempo));
                }
            }
        });
        return root;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");

    }


    @Override
    public void getDatosCostos(CostosResponse costosResponse) {

        etTipoPago.setText(costosResponse.getTipoPago());
        etTipoPago.setFocusable(false);
        if (costosResponse.getTipoPago().equals("Credito")) {
            etVale.setText(costosResponse.getNvale());
        } else {
            etVale.setFocusable(false);
            etVale.setClickable(true);
        }
        etPeaje.setText(String.valueOf(costosResponse.getPeaje()));
        etParqueo.setText(String.valueOf(costosResponse.getParqueo()));
        etTiempoEspera.setText(String.valueOf(costosResponse.getEsperaTiempo()));
        etEsperaCosto.setText(String.valueOf(costosResponse.getEsperaCosto()));
        etEsperaCosto.setFocusable(false);
        etTarifa.setText(String.valueOf(costosResponse.getSubTotalRuta()));
        etTarifa.setFocusable(false);
        etRuta.setText(String.valueOf(costosResponse.getNRuta()));
        etRuta.setFocusable(false);

    }

    @Override
    public void getDatosEspera(CostoTiempoEsperaResponse costoTiempoEsperaResponse) {
        etEsperaCosto.setText(String.valueOf(costoTiempoEsperaResponse.getCostofinal()));

    }

    @Override
    public void sendCostosResponse(MessageResponse msg) {
        if(msg.getMessage().equals("Ok")){
            showMessage("Datos enviados con éxito");
            mPresenter.getCostos(id);
            etVale.setFocusable(false);
        }

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void setPresenter(CostosContract.Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void setLoadingIndicator(boolean active) {

        if (getView() == null) {
            return;
        }

        if (active) {
            mProgressDialogCustom.show();
        } else {
            if (mProgressDialogCustom.isShowing()) {
                mProgressDialogCustom.dismiss();
            }
        }
    }


    @Override
    public void showMessage(String message) {
        ((BaseActivity) getActivity()).showMessage(message);
    }


    @Override
    public void showErrorMessage(String message) {
        ((BaseActivity) getActivity()).showMessageError(message);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_guardar)
    public void onViewClicked() {
        CostoEntity newCosto = new CostoEntity(id, etVale.getText().toString(), Double.valueOf(etPeaje.getText().toString()),Double.valueOf(etParqueo.getText().toString()),
                Integer.valueOf(etTiempoEspera.getText().toString()),Double.valueOf(etParqueo.getText().toString()));
        mPresenter.sendCostos(newCosto);

    }
}
