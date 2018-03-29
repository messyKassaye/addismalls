package com.example.red.magazine.suppliers.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;
import com.example.red.magazine.serviceproviders.AdapterModel.DailyDemandAdapterModel;
import com.example.red.magazine.serviceproviders.adapters.DailyDemandRecyclerviewAdapter;
import com.example.red.magazine.statics.ProjectStatic;
import com.example.red.magazine.suppliers.adaptersmodel.DemandRequestAdapterModel;
import com.example.red.magazine.suppliers.clients.SuppliersClient;
import com.example.red.magazine.suppliers.model.DemandRequest;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RED on 10/12/2017.
 */

public class SupplierRecyclerviewAdapter extends RecyclerView.Adapter<SupplierRecyclerviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<DemandRequestAdapterModel> albumList;
    SharedPreferences preferences;
    String token;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView company_name,quantity,full_address;


        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            company_name = (TextView) view.findViewById(R.id.request_sender_company_name);
            quantity = (TextView) view.findViewById(R.id.request_quantity);
            full_address=(TextView)view.findViewById(R.id.full_address);
        }
    }


    public SupplierRecyclerviewAdapter(Context mContext, List<DemandRequestAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
        preferences = mContext.getSharedPreferences("suppliers_data",0);
        token=preferences.getString("token","");
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
                .inflate(R.layout.supplier_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }
    public void updateData(List<DemandRequestAdapterModel> viewModels) {
        albumList.clear();
        albumList.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final DemandRequestAdapterModel model = albumList.get(position);
        holder.company_name.setText(model.getName());
        holder.quantity.setText("Quantity :"+model.getQuantity());
        holder.full_address.setText("Location: "+model.getLocation()+" ,phone no: "+model.getPhone());

    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }



    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
