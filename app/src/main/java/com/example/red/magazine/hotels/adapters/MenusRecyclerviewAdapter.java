package com.example.red.magazine.hotels.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.categories.CategoriesActivity;
import com.example.red.magazine.hotels.adaptermodel.MenusAdapterModel;
import com.example.red.magazine.malls.MallActivity;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/11/2017.
 */

public class MenusRecyclerviewAdapter extends RecyclerView.Adapter<MenusRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<MenusAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public ImageView menus_photo;


        public MyViewHolder(View view) {
            super(view);
            menus_photo = (ImageView) view.findViewById(R.id.menus_photo);

        }
    }


    public MenusRecyclerviewAdapter(Context mContext, List<MenusAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menus_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        MenusAdapterModel model = albumList.get(position);
        holder.menus_photo.setTag(model.getId());
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.menus_photo);
        holder.menus_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, MallActivity.class);
                String company_id= view.getTag().toString();
                intent.putExtra("company_id",company_id);
                //mContext.startActivity(intent);

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