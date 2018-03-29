package com.example.red.magazine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.SponsorsBranchAdaptorModel;
import com.example.red.magazine.adaptermodels.TopSellersAdapterModel;
import com.example.red.magazine.hotels.HotelOuter;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 1/4/2018.
 */

public class SponsorsbranchRecyclerviewAdapter extends RecyclerView.Adapter<SponsorsbranchRecyclerviewAdapter.MyViewHolder> {

private Context mContext;
private List<SponsorsBranchAdaptorModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView toppsellername;
    public ImageView top_sellers_phtoto;
    public CardView cardView;

    public MyViewHolder(View view) {
        super(view);
        toppsellername = (TextView) view.findViewById(R.id.top_sellers_name);
        top_sellers_phtoto = (ImageView) view.findViewById(R.id.top_sellers_image);
        cardView=(CardView)view.findViewById(R.id.top_sellers_card_view);
    }
}


    public SponsorsbranchRecyclerviewAdapter(Context mContext, List<SponsorsBranchAdaptorModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_sellers_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SponsorsBranchAdaptorModel model = albumList.get(position);
        holder.toppsellername.setText(model.getName());

        // loading album cover using Glide library
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.top_sellers_phtoto);

    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}