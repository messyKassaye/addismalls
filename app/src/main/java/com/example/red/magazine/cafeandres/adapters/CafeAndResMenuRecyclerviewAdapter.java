package com.example.red.magazine.cafeandres.adapters;

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

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.CafeResAdapterModel;
import com.example.red.magazine.adapters.CafeAndResRecyclerviewAdapterOuter;
import com.example.red.magazine.cafeandres.CafeAndRestaurantActivity;
import com.example.red.magazine.cafeandres.MenuDisplayer;
import com.example.red.magazine.cafeandres.adapterModel.CafeResMenuAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 12/17/2017.
 */

public class CafeAndResMenuRecyclerviewAdapter extends RecyclerView.Adapter<CafeAndResMenuRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<CafeResMenuAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mall_phtoto;


        public MyViewHolder(View view) {
            super(view);
            mall_phtoto = (ImageView) view.findViewById(R.id.cafe_and_res_menu_image);
        }
    }


    public CafeAndResMenuRecyclerviewAdapter(Context mContext, List<CafeResMenuAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafe_and_res_menu_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CafeResMenuAdapterModel model = albumList.get(position);
        holder.mall_phtoto.setTag(model.getId());
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.mall_phtoto);
        holder.mall_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, MenuDisplayer.class);
                intent.putExtra("menu_id",view.getTag().toString());
                mContext.startActivity(intent);
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