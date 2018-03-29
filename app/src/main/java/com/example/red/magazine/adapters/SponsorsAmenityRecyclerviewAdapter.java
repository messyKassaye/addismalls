package com.example.red.magazine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.SponsorsAmenityAdapterModel;
import com.example.red.magazine.malls.adapter.AmenitiesRecyclerviewAdapter;
import com.example.red.magazine.malls.adaptermodel.AmenityAdapterModel;

import java.util.List;

/**
 * Created by Meseret on 1/4/2018.
 */

public class SponsorsAmenityRecyclerviewAdapter extends RecyclerView.Adapter<SponsorsAmenityRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<SponsorsAmenityAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.amenity_name);
            description = (TextView) view.findViewById(R.id.amenity_description);
        }
    }


    public SponsorsAmenityRecyclerviewAdapter(Context mContext, List<SponsorsAmenityAdapterModel> albumList) {
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
                .inflate(R.layout.amenty_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SponsorsAmenityAdapterModel model = albumList.get(position);
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());


    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
