package com.example.red.magazine.hotels.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.CategoryAdapter;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.categories.CategoriesActivity;
import com.example.red.magazine.hotels.HotelOuter;
import com.example.red.magazine.hotels.adaptermodel.HotelAdapter;
import com.example.red.magazine.malls.MallActivity;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/5/2017.
 */

public class HotelRecyclerviewAdapter extends RecyclerView.Adapter<HotelRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<HotelAdapter> albumList;

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


    public HotelRecyclerviewAdapter(Context mContext, List<HotelAdapter> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_malls_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HotelAdapter model = albumList.get(position);
        holder.mallname.setText(model.getName());

        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.mall_phtoto);
        holder.mall_phtoto.setTag(model.getCompany_id());
        holder.mall_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, HotelOuter.class);
                String company_id= view.getTag().toString();
                intent.putExtra("company_id",company_id);
                intent.putExtra("about",model.getDescription());
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