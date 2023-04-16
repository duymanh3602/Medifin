package com.btl.medifin.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btl.medifin.activity.News;
import com.btl.medifin.activity.UpdateInfor;
import com.btl.medifin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NdHomeFragment extends Fragment implements View.OnClickListener{

    private TextView welcomeName;
    String name;


    public NdHomeFragment() {
        // Required empty public constructor
    }

    public static NdHomeFragment newInstance() {
        NdHomeFragment fragment = new NdHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_home, container, false);

        mappingView(view);
        name = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("FULLNAME", "");
        welcomeName.setText("Xin chào " + name + " !!!");
        // Inflate the layout for this fragment
        return view;
    }

    private void mappingView(View view) {
        welcomeName = view.findViewById(R.id.userNameWelcome);
        view.findViewById(R.id.cvHistory_ndHome).setOnClickListener(this);
        view.findViewById(R.id.cvProfile_ndHome).setOnClickListener(this);
        view.findViewById(R.id.cv_message_ndHome).setOnClickListener(this);
        view.findViewById(R.id.cv_news_ndHome).setOnClickListener(this);
        view.findViewById(R.id.btnDatLich_ndHome).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottom_nav);
        switch (v.getId()){
            case R.id.btnDatLich_ndHome:
                bnv.setSelectedItemId(R.id.menu_datLich);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NdDatLichFragment()).commit();
                break;
            case R.id.cvHistory_ndHome:
                bnv.setSelectedItemId(R.id.menu_history);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NdHistoryFragment()).commit();
                break;
            case R.id.cvProfile_ndHome:
                getActivity().startActivity(new Intent(getContext(), UpdateInfor.class));
                break;
            case R.id.cv_message_ndHome:
                bnv.setSelectedItemId(R.id.menu_medicine);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NdMedicineFragment()).commit();
                break;
            case R.id.cv_news_ndHome:
                getActivity().startActivity(new Intent(getContext(), News.class));
                break;
        }
    }


}