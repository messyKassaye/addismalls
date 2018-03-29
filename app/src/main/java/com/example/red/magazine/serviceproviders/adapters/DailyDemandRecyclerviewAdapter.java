package com.example.red.magazine.serviceproviders.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.serviceproviders.AdapterModel.DailyDemandAdapterModel;
import com.example.red.magazine.serviceproviders.AdapterModel.DemandAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by RED on 10/10/2017.
 */

public class DailyDemandRecyclerviewAdapter  extends RecyclerView.Adapter<DailyDemandRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<DailyDemandAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView demand_name,quantity,time;
        public ImageView product_photo;

        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            demand_name = (TextView) view.findViewById(R.id.demand_product_name);
            quantity = (TextView) view.findViewById(R.id.demand_quantity);
            time=(TextView)view.findViewById(R.id.demand_time);
            product_photo=(ImageView)view.findViewById(R.id.demand_product_photo);

        }
    }


    public DailyDemandRecyclerviewAdapter(Context mContext, List<DailyDemandAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manage_res_demand_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DailyDemandAdapterModel model = albumList.get(position);
        holder.demand_name.setText(model.getName());
        holder.quantity.setText("Quantity :"+model.getQuantity());
        holder.time.setText(model.getCreated_at());


        DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        DateTime now = new DateTime();
        Period period = new Period(myBirthDate, now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendMinutes().appendSuffix(" minutes ago\n")
                .printZeroNever()
                .toFormatter();
        String elapsed = formatter.print(period);
        holder.time.setText(elapsed);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.product_photo);



    }



    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
