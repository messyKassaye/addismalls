package com.example.red.magazine.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.CategoryAdapterModel;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.categories.CategoriesActivity;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by RED on 10/1/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

private Context mContext;
private List<CategoryAdapterModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout category_layout,category_translucent_layout;
    public TextView category_name;


    public MyViewHolder(View view) {
        super(view);
        category_layout = (LinearLayout) view.findViewById(R.id.category_layout);
        category_name = (TextView) view.findViewById(R.id.category_name);
        category_translucent_layout=(LinearLayout)view.findViewById(R.id.category_translucent_layout);
    }
}


    public CategoryAdapter(Context mContext, List<CategoryAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategoryAdapterModel model = albumList.get(position);
        holder.category_name.setText(model.getName());
        holder.category_translucent_layout.setTag(model.getId());
        final ImageView img = new ImageView(mContext);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setAlpha(0.8f);
        Picasso.with(mContext)
                .load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                        holder.category_layout.setBackgroundDrawable(img.getDrawable());
                    }

                    @Override
                    public void onError() {

                    }
                });
        holder.category_translucent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, CategoriesActivity.class);
                intent.putExtra("id",view.getTag().toString());
                intent.putExtra("name",holder.category_name.getText());
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
