package com.example.red.magazine.malls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.malls.adaptermodel.ManageRetailersModelAdapter;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.TopSellersActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/13/2017.
 */

public class Retailers_mange_recyclerview_adapter extends RecyclerView.Adapter<Retailers_mange_recyclerview_adapter.MyViewHolder> {

private Context mContext;
private List<ManageRetailersModelAdapter> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView company_name,floor_no,office_no;
    public ImageView retailers_photo;


    public MyViewHolder(View view) {
        super(view);
        company_name = (TextView) view.findViewById(R.id.retailers_name);
        office_no=(TextView)view.findViewById(R.id.retailers_office_no);
        retailers_photo = (ImageView) view.findViewById(R.id.retailers_photo);

    }
}


    public Retailers_mange_recyclerview_adapter(Context mContext, List<ManageRetailersModelAdapter> albumList) {
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
                .inflate(R.layout.retailers_manage_recyclerviews_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ManageRetailersModelAdapter model = albumList.get(position);
        holder.company_name.setText(model.getName());
        holder.office_no.setText("Office No: "+model.getOffice_no());

        // loading album cover using Glide library
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.retailers_photo);
        //holder.mall_phtoto.setTag(model.getId());
        holder.retailers_photo.setOnClickListener(new View.OnClickListener() {
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