package tmsystem.com.tmsystemdriver.presentation.seguimiento;


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
import tmsystem.com.tmsystemdriver.data.models.SeguimientoResponse;
import tmsystem.com.tmsystemdriver.utils.OnClickListListener;

/**
 * Created by kath on 08/01/18.
 */

public class SeguimientoAdapter extends LoaderAdapter<SeguimientoResponse> implements OnClickListListener {

    private Context context;
    private SeguimientoItem seguimientoItem;

    public SeguimientoAdapter(ArrayList<SeguimientoResponse> restEntinties, Context context, SeguimientoItem seguimientoItem) {
        super(context);
        setItems(restEntinties);
        this.context = context;
        this.seguimientoItem = seguimientoItem;
    }

    public SeguimientoAdapter(ArrayList<SeguimientoResponse> restEntinties, Context context) {
        super(context);
        setItems(restEntinties);
        this.context = context;
    }

    public ArrayList<SeguimientoResponse> getItems() {
        return (ArrayList<SeguimientoResponse>) getmItems();
    }

    @Override
    public long getYourItemId(int position) {
        return getmItems().get(position).getIdSeguimiento();
    }

    @Override
    public RecyclerView.ViewHolder getYourItemViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seguimiento, parent, false);
        return new ViewHolder(root, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindYourViewHolder(RecyclerView.ViewHolder holder, int position) {
        SeguimientoResponse seguimientoResponse = getItems().get(position);

        ((ViewHolder) holder).tvSeguimiento.setText(seguimientoResponse.getDesseguimiento());

        /*((ViewHolder) holder).tvName.setText(restEntinty.getName());
        if (restEntinty.getPhoto1()!=null){
            Glide.with(context)
                    .load(restEntinty.getPhoto1())
                    .into(((ViewHolder) holder).back);
        }else{
            ((ViewHolder) holder).back.setImageDrawable(context.getDrawable(R.drawable.imagemcenter));
        }*/
    }

    @Override
    public void onClick(int position) {
        SeguimientoResponse seguimientoResponse = getItems().get(position);
        seguimientoItem.clickItem(seguimientoResponse);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_seguimiento)
        TextView tvSeguimiento;
        private OnClickListListener onClickListListener;

        ViewHolder(View itemView, OnClickListListener onClickListListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onClickListListener = onClickListListener;
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListListener.onClick(getAdapterPosition());
        }
    }

}

