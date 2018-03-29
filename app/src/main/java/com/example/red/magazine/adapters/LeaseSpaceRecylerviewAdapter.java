package com.example.red.magazine.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.LeaseSpaceOuterAdapterModel;
import com.example.red.magazine.malls.adapter.OuterLeaseSpaceRecyclerviewAdapter;
import com.example.red.magazine.malls.adaptermodel.OuterLeaseSpaceAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Meseret on 1/2/2018.
 */

public class LeaseSpaceRecylerviewAdapter extends  RecyclerView.Adapter<LeaseSpaceRecylerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<LeaseSpaceOuterAdapterModel> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView floor_no,area,purpose,phone,description;
        private Button call;
        public ImageView photo;

        public MyViewHolder(View view) {
            super(view);
            floor_no = (TextView) view.findViewById(R.id.outer_spaces_floor_no);
            area = (TextView) view.findViewById(R.id.outer_space_area);
            purpose=(TextView)view.findViewById(R.id.outer_space_purpose);
            phone=(TextView)view.findViewById(R.id.outer_space_phone_no);
            description=(TextView)view.findViewById(R.id.outer_space_descriptio);
            photo=(ImageView)view.findViewById(R.id.outer_space_photo);
            call=(Button)view.findViewById(R.id.outer_call);
        }
    }


    public LeaseSpaceRecylerviewAdapter(Context mContext, List<LeaseSpaceOuterAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outer_spaces_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        LeaseSpaceOuterAdapterModel model = albumList.get(position);
        holder.floor_no.setText("Floor no: "+model.getFloor_no());
        holder.area.setText("Area in meter square: "+model.getArea());
        holder.purpose.setText("Purpose: "+model.getPurpose());
        holder.description.setText(model.getDescription());
        holder.phone.setText("phone no: "+model.getPhone_no());
        holder.call.setTag(model.getPhone_no());

        //PhoneCallListener phoneCall= new PhoneCallListener();
        PhoneCallListener phoneCallListener=new PhoneCallListener();
        TelephonyManager telephonyManager=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
        // loading album cover using Glide library
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.photo);
        //holder.top_sellers_phtoto.setTag(model.getId());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+view.getTag().toString()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                // mContext.startActivity(callIntent);
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

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = mContext.getPackageManager()
                            .getLaunchIntentForPackage(
                                    mContext.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}