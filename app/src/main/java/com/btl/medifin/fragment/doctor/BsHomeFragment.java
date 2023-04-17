package com.btl.medifin.fragment.doctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.btl.medifin.activity.News;
import com.btl.medifin.activity.UpdateInfor;
import com.btl.medifin.R;
import com.btl.medifin.fragment.user.NdHistoryFragment;
import com.btl.medifin.fragment.user.NdMedicineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BsHomeFragment extends Fragment implements View.OnClickListener{
    private Button btnLichKham;
    private CardView cvProfile, cvHistory, cvMessage, cvNews;
    private BottomNavigationView bnv;
    private TextView welcomeName;
    String name;

    public BsHomeFragment() {
        // Required empty public constructor
    }

    public static BsHomeFragment newInstance() {
        BsHomeFragment fragment = new BsHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bs_home, container, false);
        bnv = getActivity().findViewById(R.id.bottom_nav);
        mapping(view);
        name = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("FULLNAME", "");
        welcomeName.setText("Xin chào " + name + " !!!");

        return view;
    }



    private void mapping(View view) {
        welcomeName = view.findViewById(R.id.userNameWelcome);
        view.findViewById(R.id.btnLichKham_bsHome).setOnClickListener(this);
        view.findViewById(R.id.cvProfile_bsHome).setOnClickListener(this);
        view.findViewById(R.id.cvHistory_bsHome).setOnClickListener(this);
        view.findViewById(R.id.cv_message_bsHome).setOnClickListener(this);
        view.findViewById(R.id.cv_news_bsHome).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLichKham_bsHome:
                bnv.setSelectedItemId(R.id.menu_datLich);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new BsLichKhamFragment()).commit();
                break;
            case R.id.cvHistory_bsHome:
                bnv.setSelectedItemId(R.id.menu_history);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NdHistoryFragment()).commit();
                break;
            case R.id.cvProfile_bsHome:
                getActivity().startActivity(new Intent(getContext(), UpdateInfor.class));
                break;
            case R.id.cv_message_bsHome:
                bnv.setSelectedItemId(R.id.menu_medicine);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NdMedicineFragment()).commit();
                break;
            case R.id.cv_news_bsHome:
                getActivity().startActivity(new Intent(getContext(), News.class));
                break;
        }
    }
}