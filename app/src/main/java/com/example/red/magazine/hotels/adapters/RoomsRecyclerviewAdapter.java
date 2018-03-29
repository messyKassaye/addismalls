package com.example.red.magazine.hotels.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.hotels.HotelOuter;
import com.example.red.magazine.hotels.RoomsDetails;
import com.example.red.magazine.hotels.adaptermodel.HotelAdapter;
import com.example.red.magazine.hotels.adaptermodel.RoomsAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/11/2017.
 */

public class RoomsRecyclerviewAdapter extends RecyclerView.Adapter<RoomsRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<RoomsAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description,price;
        public ImageView cover;
        private Button rooms_details;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.room_title);
            description = (TextView) view.findViewById(R.id.room_description);
            cover=(ImageView) view.findViewById(R.id.roo_photo);
            price=(TextView) view.findViewById(R.id.room_price);
            rooms_details=(Button)view.findViewById(R.id.room_details);
        }
    }


    public RoomsRecyclerviewAdapter(Context mContext, List<RoomsAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rooms_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final RoomsAdapterModel model = albumList.get(position);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getPrice()+" ETB");
        holder.rooms_details.setTag(model.getId());
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getCover()).placeholder(R.drawable.background_tile2_small).into(holder.cover);
       holder.rooms_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, RoomsDetails.class);
                String company_id= view.getTag().toString();
                intent.putExtra("id",company_id);
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