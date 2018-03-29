package com.example.red.magazine.malls.adapter;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.TopSellersAdapterModel;
import com.example.red.magazine.malls.fragments.Notification_fragment;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/6/2017.
 */

public class Show_Retailers_adapter extends RecyclerView.Adapter<Show_Retailers_adapter.MyViewHolder> {

private Context mContext;
private List<TopSellersAdapterModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView toppsellername;
    public ImageView top_sellers_phtoto;
    private TextView floor_no,office_no;
    private TextView see_our_items;
    public CardView cardView;

    public MyViewHolder(View view) {
        super(view);
        toppsellername = (TextView) view.findViewById(R.id.top_sellers_name);
        top_sellers_phtoto = (ImageView) view.findViewById(R.id.top_sellers_image);
        floor_no=(TextView)view.findViewById(R.id.floor_no);
        office_no=(TextView)view.findViewById(R.id.office_no);
        cardView=(CardView)view.findViewById(R.id.top_sellers_card_view);
    }
}


    public Show_Retailers_adapter(Context mContext, List<TopSellersAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_retailers_main_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TopSellersAdapterModel model = albumList.get(position);
        holder.toppsellername.setText(model.getName());
        holder.floor_no.setText("Floor No: "+model.getFloor_no());
        holder.office_no.setText("@ Office No: "+model.getOffice_no());
        // loading album cover using Glide library
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.top_sellers_phtoto);
        holder.top_sellers_phtoto.setTag(model.getId());
        holder.top_sellers_phtoto.setTag(model.getId());
        holder.top_sellers_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, TopSellersActivity.class);
                intent.putExtra("id",view.getTag().toString());
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