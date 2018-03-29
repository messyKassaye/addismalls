package com.example.red.magazine.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.red.magazine.R;
import com.example.red.magazine.SponsorsDetail;
import com.example.red.magazine.adaptermodels.SponserAdapterModel;
import com.example.red.magazine.model.Sponser;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RED on 9/27/2017.
 */

public class SponserPagerAdapter extends PagerAdapter {
    private ArrayList<SponserAdapterModel> images;
    private LayoutInflater inflater;
    private Context context;

    public SponserPagerAdapter(Context context, ArrayList<SponserAdapterModel> images) {
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
        SponserAdapterModel model=images.get(position);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        myImage.setTag(model.getWebsite_link());
        TextView sponsorname=(TextView)myImageLayout.findViewById(R.id.sponsor_name);
        //myImage.setImageResource(images.get(position));
        Picasso.with(context).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(myImage);
        sponsorname.setText("Sponsored by: "+model.getName());
        view.addView(myImageLayout, 0);
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company_id=view.getTag().toString();
                /*Intent intent= new Intent(context, SponsorsDetail.class);
                intent.putExtra("company_id",company_id);
                context.startActivity(intent);*/
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(view.getTag().toString()));
                context.startActivity(intent);

            }
        });
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}