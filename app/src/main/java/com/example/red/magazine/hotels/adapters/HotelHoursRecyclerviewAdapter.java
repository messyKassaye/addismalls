package com.example.red.magazine.hotels.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.hotels.adaptermodel.HotelDisheAdapterModel;
import com.example.red.magazine.hotels.adaptermodel.OpeningHoursAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 11/18/2017.
 */

public class HotelHoursRecyclerviewAdapter extends RecyclerView.Adapter<HotelHoursRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<OpeningHoursAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView monday_date,tuesday_date,wendsday_date,thursday_date,friday_date,satureday_date,sunday_date;
        public TextView monday_hours,tuesday_hours,wendsday_hours,thursday_hours,friday_hours,satureday_hours,sunday_hours;


        public MyViewHolder(View view) {
            super(view);
            monday_date = (TextView) view.findViewById(R.id.monday_hours_date);
            monday_hours=(TextView)view.findViewById(R.id.monday_opening_hour);

            tuesday_date = (TextView) view.findViewById(R.id.tues_hours_date);
            tuesday_hours=(TextView)view.findViewById(R.id.tues_opening_hour);

            wendsday_date = (TextView) view.findViewById(R.id.wendsday_hours_date);
            wendsday_hours=(TextView)view.findViewById(R.id.wendsday_opening_hour);

            thursday_date = (TextView) view.findViewById(R.id.thurs_hours_date);
            thursday_hours=(TextView)view.findViewById(R.id.thurs_opening_hour);

            friday_date = (TextView) view.findViewById(R.id.friday_hours_date);
            friday_hours=(TextView)view.findViewById(R.id.friday_opening_hour);

            satureday_date = (TextView) view.findViewById(R.id.sature_hours_date);
            satureday_hours=(TextView)view.findViewById(R.id.sature_opening_hour);

            sunday_date = (TextView) view.findViewById(R.id.sunday_hours_date);
            sunday_hours=(TextView)view.findViewById(R.id.sunday_opening_hour);




        }
    }


    public HotelHoursRecyclerviewAdapter(Context mContext, List<OpeningHoursAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hours_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final OpeningHoursAdapterModel model = albumList.get(position);
        holder.monday_hours.setText(model.getMonday());
        holder.tuesday_hours.setText(model.getTuesday());
        holder.wendsday_hours.setText(model.getWendsday());
        holder.thursday_hours.setText(model.getThursday());
        holder.friday_hours.setText(model.getFriday());
        holder.satureday_hours.setText(model.getSatureday());
        holder.sunday_hours.setText(model.getSunday());
    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}