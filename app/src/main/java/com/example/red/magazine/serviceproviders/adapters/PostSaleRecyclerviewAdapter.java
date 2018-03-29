package com.example.red.magazine.serviceproviders.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.serviceproviders.AdapterModel.DailyDemandAdapterModel;
import com.example.red.magazine.serviceproviders.AdapterModel.PostSaleAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

/**
 * Created by RED on 10/13/2017.
 */

public class PostSaleRecyclerviewAdapter extends RecyclerView.Adapter<PostSaleRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<PostSaleAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView price,caption;
        public ImageView product_photo;

        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            caption = (TextView) view.findViewById(R.id.post_sale_caption);
            price = (TextView) view.findViewById(R.id.post_sale_price);
            product_photo=(ImageView)view.findViewById(R.id.post_sale_product_photo);

        }
    }


    public PostSaleRecyclerviewAdapter(Context mContext, List<PostSaleAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_sale_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PostSaleAdapterModel model = albumList.get(position);
        holder.caption.setText("Caption: "+model.getCaption());
        holder.price.setText("Price :"+model.getPrice());
        //holder.time.setText(model.getCreated_at());


        DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        DateTime now = new DateTime();
        Period period = new Period(myBirthDate, now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendMinutes().appendSuffix(" minutes ago\n")
                .printZeroNever()
                .toFormatter();
        String elapsed = formatter.print(period);
        //holder.time.setText(elapsed);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getProduct_photo()).placeholder(R.drawable.background_tile2_small).into(holder.product_photo);



    }



    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
