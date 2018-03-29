package com.example.red.magazine.malls.admin.mallAdminfragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.red.magazine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MallGuider extends Fragment {

    private TextView welcome,upload_photo,upload_mall_photo_btn,uploaded_photo_name;
    private TextView address_not_added_text,add_specific_name,add_specific_name_btn;
    private EditText specific_name_edittext;

    public MallGuider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mall_guider, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("token",0);

        welcome=(TextView)view.findViewById(R.id.well_come);
        upload_photo=(TextView)view.findViewById(R.id.upload_mall_photo);
        upload_mall_photo_btn=(TextView)view.findViewById(R.id.upload_mall_photo_btn);
        uploaded_photo_name=(TextView)view.findViewById(R.id.uploaded_photo_name);
        address_not_added_text=(TextView)view.findViewById(R.id.address_not_added_text);
        add_specific_name=(TextView)view.findViewById(R.id.add_specific_name);
        add_specific_name_btn=(TextView)view.findViewById(R.id.add_specific_name_btn);
        specific_name_edittext=(EditText)view.findViewById(R.id.specific_name_edittext);

        return view;
    }

}
