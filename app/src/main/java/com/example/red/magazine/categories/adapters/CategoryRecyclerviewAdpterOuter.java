package com.example.red.magazine.categories.adapters;

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
import com.example.red.magazine.categories.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.hotels.HotelOuter;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/8/2017.
 */

public class CategoryRecyclerviewAdpterOuter extends RecyclerView.Adapter<CategoryRecyclerviewAdpterOuter.MyViewHolder> {

    private Context mContext;
    private List<CategoryAdapterModel> albumList;

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


    public CategoryRecyclerviewAdpterOuter(Context mContext, List<CategoryAdapterModel> albumList) {
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
        final CategoryAdapterModel model = albumList.get(position);
        holder.mallname.setText(model.getName());
        holder.mall_phtoto.setTag(model.getCompany_id());
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.mall_phtoto);
        //holder.mall_phtoto.setTag(model.getCompany_id());
        holder.mall_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(mContext, TopSellersActivity.class);
                intent.putExtra("id",view.getTag().toString());
                mContext.startActivity(intent);*/
                Toast.makeText(mContext,view.getTag().toString(),Toast.LENGTH_LONG).show();
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