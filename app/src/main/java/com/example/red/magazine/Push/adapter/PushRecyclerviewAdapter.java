package com.example.red.magazine.Push.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.Push.Push;
import com.example.red.magazine.Push.adapterModel.PushAdapterModel;
import com.example.red.magazine.Push.client.PushClient;
import com.example.red.magazine.Push.models.PushPostModel;
import com.example.red.magazine.R;
import com.example.red.magazine.adaptermodels.TopMallsAdapterModel;
import com.example.red.magazine.adapters.TopMallAdapter;
import com.example.red.magazine.malls.MallActivity;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Meseret on 11/6/2017.
 */

 /*publicclass PushRecyclerviewAdapter extends RecyclerView.Adapter<PushRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<PushAdapterModel> albumList;
    private String customer_id;
    private String brande_id;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button item_name;

        public MyViewHolder(View view) {
            super(view);
            item_name = (Button) view.findViewById(R.id.item_name);
        }
    }
    public void clear() {
        int size = this.albumList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.albumList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }


    public PushRecyclerviewAdapter(Context mContext, List<PushAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.push_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PushAdapterModel model = albumList.get(position);
        holder.item_name.setText(model.getName());
        holder.item_name.setTag(model.getId());
        holder.item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brande_id=view.getTag().toString();
                final Dialog dialog=new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.push_dialog_layout);
                final EditText quantity=(EditText)dialog.findViewById(R.id.push_quantity);
                Button send_push=(Button)dialog.findViewById(R.id.send_push);
                final TextView error=(TextView)dialog.findViewById(R.id.push_error);
                send_push.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       String quantity_value= quantity.getText().toString();
                        if (quantity_value.equals("")){
                            error.setText("Please enter the quantity ");
                            error.setVisibility(View.VISIBLE);
                        }else {
                            Retrofit retrofit= getUserAPIretrofit();
                            PushClient client=retrofit.create(PushClient.class);
                            PushPostModel push= new PushPostModel(getCustomer_id(),brande_id,quantity_value);
                            Call<ResponseMessage> call= client.push_post(push);
                            call.enqueue(new Callback<ResponseMessage>() {
                                @Override
                                public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                    if (response.body().getStatus().equals("ok")){
                                      dialog.dismiss();
                                      clear();
                                    }else {

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseMessage> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                dialog.show();

            }
        });

    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    *//**
     * Click listener for popup menu items
     *//*

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}*/