package com.example.red.magazine.hotels.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.hotels.HotelOuter;
import com.example.red.magazine.hotels.adaptermodel.HotelAdapter;
import com.example.red.magazine.hotels.adaptermodel.HotelDisheAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adaptersmodel.HotelsAdapterModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/18/2017.
 */

public class HotelDisheRecyclerviewAdapter extends RecyclerView.Adapter<HotelDisheRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<HotelDisheAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dishe_name;
        public ImageView dishe_photo;
        public Button show_me_more;
        public TextView description;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            dishe_name = (TextView) view.findViewById(R.id.hotel_dishe_name);
            dishe_photo = (ImageView) view.findViewById(R.id.hotel_dishe_photo);
            show_me_more=(Button)view.findViewById(R.id.hotel_dishes_detail);
            description=(TextView)view.findViewById(R.id.hotel_dishes_description);
            cardView=(CardView)view.findViewById(R.id.hotel_dishe_cardview);

        }
    }


    public HotelDisheRecyclerviewAdapter(Context mContext, List<HotelDisheAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotels_dishes_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HotelDisheAdapterModel model = albumList.get(position);
        holder.dishe_name.setText(model.getName());
        holder.description.setText(model.getDescription());
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.dishe_photo);
        holder.show_me_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             holder.description.setVisibility(View.VISIBLE);
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