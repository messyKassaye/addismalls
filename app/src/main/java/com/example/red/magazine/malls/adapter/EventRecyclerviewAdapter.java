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

import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.EventAdapterModel;
import com.example.red.magazine.malls.adaptermodel.ServiceprovidersAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/17/2017.
 */

public class EventRecyclerviewAdapter extends RecyclerView.Adapter<EventRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventAdapterModel> albumList;

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


    public EventRecyclerviewAdapter(Context mContext, List<EventAdapterModel> albumList) {
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
        EventAdapterModel model = albumList.get(position);

    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
