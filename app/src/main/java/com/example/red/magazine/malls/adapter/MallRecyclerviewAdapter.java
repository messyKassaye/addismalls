package com.example.red.magazine.malls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.malls.MallActivity;
import com.example.red.magazine.malls.adaptermodel.ServiceprovidersAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/3/2017.
 */

public class MallRecyclerviewAdapter extends RecyclerView.Adapter<MallRecyclerviewAdapter.MyViewHolder> {

private Context mContext;
private List<ServiceprovidersAdapterModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView mallname;
    public ImageView mall_phtoto;
    public CardView cardView;

    public MyViewHolder(View view) {
        super(view);
        mallname = (TextView) view.findViewById(R.id.mall_name);
        mall_phtoto = (ImageView) view.findViewById(R.id.mall_image);
        cardView=(CardView)view.findViewById(R.id.top_mall_card_view);
    }
}


    public MallRecyclerviewAdapter(Context mContext, List<ServiceprovidersAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
    public void clearApplications() {
        int size = this.albumList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                albumList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_malls_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceprovidersAdapterModel model = albumList.get(position);
        holder.mallname.setText(model.getName());

        // loading album cover using Glide library
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.mall_phtoto);
        holder.mall_phtoto.setTag(model.getId());
        holder.mall_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, TopSellersActivity.class);
                intent.putExtra("id",view.getTag().toString());
                mContext.startActivity(intent);
                //Toast.makeText(mContext,view.getTag().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}