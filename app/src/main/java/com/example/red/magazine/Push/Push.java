package com.example.red.magazine.Push;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.red.magazine.Push.adapterModel.PushAdapterModel;
import com.example.red.magazine.Push.client.PushClient;
import com.example.red.magazine.Push.models.PushModel;
import com.example.red.magazine.Push.models.PushPostModel;
import com.example.red.magazine.R;
import com.example.red.magazine.malls.model.ResponseMessage;
import com.example.red.magazine.statics.ProjectStatic;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Push extends AppCompatActivity {
   private ViewFlipper flipper;
    private View first_view,second_view;

    private EditText custom_code;
    private Button send_btn;

    private List<PushAdapterModel> modelList;
    private PushRecyclerviewAdapter adapter;
    private RecyclerView recyclerView;

    private TextView text_item,text_success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        flipper= (ViewFlipper)findViewById(R.id.push_flipper);

        first_view= LayoutInflater.from(this).inflate(R.layout.push_first_view,null);
        flipper.addView(first_view);
        flipper.showNext();

        second_view=LayoutInflater.from(this).inflate(R.layout.push_second_view,null);

        text_item=(TextView)second_view.findViewById(R.id.your_item);
        text_success=(TextView)second_view.findViewById(R.id.push_success);

        modelList=new ArrayList<>();
        adapter=new PushRecyclerviewAdapter(this,modelList);
        recyclerView=(RecyclerView)second_view.findViewById(R.id.push_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        custom_code=(EditText)first_view.findViewById(R.id.referral_code);
        send_btn=(Button)first_view.findViewById(R.id.send_push);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String customer_id=custom_code.getText().toString();
                if (customer_id.equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter your customer code",Toast.LENGTH_LONG).show();
                }else {
                    Retrofit retrofit=getUserAPIretrofit();
                    PushClient client= retrofit.create(PushClient.class);
                    Call<List<PushModel>> call= client.get_push(customer_id);
                    call.enqueue(new Callback<List<PushModel>>() {
                        @Override
                        public void onResponse(Call<List<PushModel>> call, Response<List<PushModel>> response) {
                            flipper.addView(second_view);
                            flipper.showNext();
                            for(int i=0;i<response.body().size();i++){
                              PushAdapterModel model=new PushAdapterModel();
                              model.setName(response.body().get(i).getName());
                              model.setId(response.body().get(i).getId());
                              modelList.add(model);
                          }
                           adapter.setCustomer_id(customer_id);
                           recyclerView.setAdapter(adapter);
                           adapter.notifyDataSetChanged();


                        }
                        @Override
                        public void onFailure(Call<List<PushModel>> call, Throwable t) {

                        }
                    });

                }
            }
        });




        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        // set the animation type's to ViewFlipperActivity
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        // set interval time for flipping between views
        flipper.setFlipInterval(3000);
        flipper.setDisplayedChild(6);

    }
    public Retrofit getUserAPIretrofit(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ProjectStatic.URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class PushRecyclerviewAdapter extends RecyclerView.Adapter<PushRecyclerviewAdapter.MyViewHolder> {

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
                                            text_success.setVisibility(View.VISIBLE);
                                            text_item.setVisibility(View.GONE);
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

        /**
         * Click listener for popup menu items
         */

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }
}
