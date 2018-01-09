package tmsystem.com.tmsystemdriver.presentation.requisitos;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tmsystem.com.tmsystemdriver.R;
import tmsystem.com.tmsystemdriver.core.LoaderAdapter;
import tmsystem.com.tmsystemdriver.data.models.RequisitosResponse;
import tmsystem.com.tmsystemdriver.utils.OnClickListListener;

/**
 * Created by kath on 08/01/18.
 */

public class RequisitosAdapter extends LoaderAdapter<RequisitosResponse> {


    private Context context;

    public RequisitosAdapter(ArrayList<RequisitosResponse> restEntinties, Context context) {
        super(context);
        setItems(restEntinties);
        this.context = context;
    }


    public ArrayList<RequisitosResponse> getItems() {
        return (ArrayList<RequisitosResponse>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requisitos, parent, false);
        return new ViewHolder(root);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        RequisitosResponse requisitosResponse = getItems().get(position);

        ((ViewHolder) holder).titleRequisito.setText(requisitosResponse.getTitulorequisitos());
        ((ViewHolder) holder).tvRequisito.setText(requisitosResponse.getDetallerequisitos());


        /*((ViewHolder) holder).tvName.setText(restEntinty.getName());
        if (restEntinty.getPhoto1()!=null){
            Glide.with(context)
                    .load(restEntinty.getPhoto1())
                    .into(((ViewHolder) holder).back);
        }else{
            ((ViewHolder) holder).back.setImageDrawable(context.getDrawable(R.drawable.imagemcenter));
        }*/
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_requisito)
        TextView titleRequisito;
        @BindView(R.id.tv_Requisito)
        TextView tvRequisito;

        private OnClickListListener onClickListListener;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

