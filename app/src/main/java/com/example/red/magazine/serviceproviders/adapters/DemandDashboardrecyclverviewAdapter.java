package com.example.red.magazine.serviceproviders.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
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
import com.example.red.magazine.serviceproviders.AdapterModel.DemandAdapterModel;
import com.example.red.magazine.statics.ProjectStatic;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by RED on 10/9/2017.
 */

public class DemandDashboardrecyclverviewAdapter extends RecyclerView.Adapter<DemandDashboardrecyclverviewAdapter.MyViewHolder> {

private Context mContext;
private List<DemandAdapterModel> albumList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView company_name,description,availability,price,posted_time,watched_by,take_action;
    public ImageView company_photo,product_photo;

    public CardView cardView;

    public MyViewHolder(View view) {
        super(view);
        company_name = (TextView) view.findViewById(R.id.demand_company_name);
        description = (TextView) view.findViewById(R.id.posted_description);
        posted_time=(TextView)view.findViewById(R.id.posted_time);
        availability=(TextView)view.findViewById(R.id.deliver_time);
        price=(TextView)view.findViewById(R.id.single_price);
        watched_by=(TextView)view.findViewById(R.id.watched_by);
        take_action=(TextView)view.findViewById(R.id.take_action);
        company_photo=(ImageView)view.findViewById(R.id.company_photo);
        product_photo=(ImageView)view.findViewById(R.id.posted_product_photo);
        cardView=(CardView)view.findViewById(R.id.top_mall_card_view);
    }
}


    public DemandDashboardrecyclverviewAdapter(Context mContext, List<DemandAdapterModel> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_provider_dashboard_recyclerview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DemandAdapterModel model = albumList.get(position);
        holder.company_name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.availability.setText(model.getAvailability());
        holder.price.setText(model.getPrice());
        holder.watched_by.setText("23");
        holder.take_action.setText("take action");


        DateTime myBirthDate = new DateTime(1978, 3, 26, 12, 35, 0, 0);
        DateTime now = new DateTime();
        Period period = new Period(myBirthDate, now);

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendMinutes().appendSuffix(" minutes ago\n")
                .printZeroNever()
                .toFormatter();
        String elapsed = formatter.print(period);
        holder.posted_time.setText(elapsed);
        // loading album cover using Glide library
        //Glide.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).into(holder.mall_phtoto);
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getPhoto_path()).placeholder(R.drawable.background_tile2_small).into(holder.company_photo);
        /*//holder.mall_phtoto.setTag(model.getCompany_id());
        holder.mall_phtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, MallActivity.class);
                String company_id= view.getTag().toString();
                intent.putExtra("company_id",company_id);
                mContext.startActivity(intent);

            }
        });*/
        Picasso.with(mContext).load(ProjectStatic.IMAPGE_PATH+model.getProduct_photo()).transform(new RoundedCornersTransformation(20,20)).into(holder.product_photo);


    }
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml("<font color:'#000'>"+tv.getText().toString()+"</font>"), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }
    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GREEN);
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);
        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }


    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}