package com.example.red.magazine.agents.companyAgent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.red.magazine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meseret on 11/7/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> company_list;
    public SpinnerAdapter(Context mcontext, ArrayList<String> company_li){
        this.context=mcontext;
        company_list=company_li;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.custom_spinner_layout,null);
        TextView company_type=(TextView)view1.findViewById(R.id.company_type);
        company_type.setText(company_list.get(i));
        return  view1;
    }
}
