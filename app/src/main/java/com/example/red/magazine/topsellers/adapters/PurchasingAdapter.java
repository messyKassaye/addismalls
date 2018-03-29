package com.example.red.magazine.topsellers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.SponsorsDetail;
import com.example.red.magazine.adaptermodels.SponserAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.topsellers.adaptermodels.PurchasingAdapterModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Meseret on 1/13/2018.
 */

public class PurchasingAdapter extends PagerAdapter {
    private ArrayList<PurchasingAdapterModel> images;
    private LayoutInflater inflater;
    private Context context;

    public PurchasingAdapter(Context context, ArrayList<PurchasingAdapterModel> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        PurchasingAdapterModel model=images.get(position);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
       // myImage.setTag(model.getCompany_id());
        Picasso.with(context).load(ProjectStatic.IMAPGE_PATH+model.getPhoto()).placeholder(R.drawable.background_tile2_small).into(myImage);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}