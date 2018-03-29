package com.example.red.magazine.suppliers.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.red.magazine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragmentOne extends Fragment {

    SharedPreferences preferences;
    public TabFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_fragment_one, container, false);

        preferences = getActivity().getSharedPreferences("suppliers_data",0);
        Toast.makeText(getActivity(),preferences.getString("id",""),Toast.LENGTH_LONG).show();

        return  view;
    }

}
