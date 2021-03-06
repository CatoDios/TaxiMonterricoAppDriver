package tmsystem.com.tmsystemdriver.presentation.seguimiento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.BaseActivity;
import tmsystem.com.tmsystemdriver.core.BaseFragment;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoEntity;
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.utils.ProgressDialogCustom;

/**
 * Created by kath on 08/01/18.
 */

public class SeguimientoFragment extends BaseFragment implements SeguimientoContract.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.noListIcon)
    ImageView noListIcon;
    @BindView(R.id.noListMain)
    TextView noListMain;
    @BindView(R.id.noList)
    LinearLayout noList;
    Unbinder unbinder;
    @BindView(R.id.tv_text)
    EditText tvText;
    @BindView(R.id.btn_send)
    ImageButton btnSend;
    @BindView(R.id.box_message)
    LinearLayout boxMessage;


    private String daySelected;
    private SeguimientoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SeguimientoContract.Presenter mPresenter;
    private ProgressDialogCustom mProgressDialogCustom;

    private int id;

    private int id_otros;

    public SeguimientoFragment() {
        // Requires empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startLoad(id);
    }

    public static SeguimientoFragment newInstance(Bundle bundle) {
        SeguimientoFragment fragment = new SeguimientoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SeguimientoPresenter(this, getContext());
        id = (int) getArguments().getSerializable("id");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, root);

       /* AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment fragment = new HomeFragment();
                fragmentTransaction.replace(R.id.contentContainer_1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressDialogCustom = new ProgressDialogCustom(getContext(), "Obteniendo datos...");
        mLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(mLayoutManager);
        mAdapter = new SeguimientoAdapter(new ArrayList<SeguimientoResponse>(), getContext(), (SeguimientoItem) mPresenter);
        rvList.setAdapter(mAdapter);
    }


    @Override
    public void getListSeguimiento(ArrayList<SeguimientoResponse> list) {
        mAdapter.setItems(list);
        if (list != null) {
            noList.setVisibility((list.size() > 0) ? View.GONE : View.VISIBLE);
        }

    }

    @Override
    public void clickItemSeguimiento(SeguimientoResponse seguimientoResponse) {

        if(Objects.equals(seguimientoResponse.getDesseguimiento(), "OTROS")){
            boxMessage.setVisibility(View.VISIBLE);
            id_otros = seguimientoResponse.getIdSeguimiento();

        }else {
            SeguimientoEntity seguimientoEntity = new SeguimientoEntity();
            seguimientoEntity.setIdReserva(id);
            seguimientoEntity.setIdSeguimiento(seguimientoResponse.getIdSeguimiento());
            seguimientoEntity.setObservaciones(" ");
            mPresenter.sendSeguimiento(seguimientoEntity);
        }


    }

    @Override
    public void sendSeguimientoResponse(String msg) {
        Toast.makeText(getContext(), "seguimiento envíado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(SeguimientoContract.Presenter presenter) {
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

    @OnClick(R.id.btn_send)
    public void onViewClicked() {

        if (tvText.getText().length() != 0){
            SeguimientoEntity seguimiento_otros = new SeguimientoEntity();
            seguimiento_otros.setIdReserva(id);
            seguimiento_otros.setIdSeguimiento(id_otros);
            seguimiento_otros.setObservaciones(tvText.getText().toString());
            mPresenter.sendSeguimiento(seguimiento_otros);
        }else {
            Toast.makeText(getContext(), "No puede enviar un mensaje vacío", Toast.LENGTH_LONG).show();
        }


    }
}
