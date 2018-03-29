package com.example.red.magazine.topsellers.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.ServiceProviderNavigation;
import com.example.red.magazine.topsellers.adaptermodels.PaymentSupporterBanksAdapterModel;
import com.example.red.magazine.topsellers.adaptermodels.TopSellersItemAdapterModel;

import java.util.List;

/**
 * Created by Meseret on 1/13/2018.
 */

public class PaymentSupporterbanksRecyclerViewAdapter extends RecyclerView.Adapter<PaymentSupporterbanksRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<PaymentSupporterBanksAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView product_photo;

        public MyViewHolder(View view) {
            super(view);
            product_photo = (ImageView) view.findViewById(R.id.supporter_bank_logo);
        }
    }


    public PaymentSupporterbanksRecyclerViewAdapter(Context mContext, List<PaymentSupporterBanksAdapterModel> albumList) {
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
                .inflate(R.layout.payment_supporters_bank_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PaymentSupporterBanksAdapterModel model = albumList.get(position);

        Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getLogo()).into(holder.product_photo);
        holder.product_photo.setTag(model.getPayment_link());
        holder.product_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(view.getTag().toString()));
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