package com.example.red.magazine.malls.admin.mallAdminfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.red.magazine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMorePhoto extends Fragment {


    public AddMorePhoto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_more_photo, container, false);
    }

}
