package com.btl.medifin.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btl.medifin.activity.News;
import com.btl.medifin.activity.SignIn;
import com.btl.medifin.activity.UpdateInfor;
import com.btl.medifin.R;
import com.btl.medifin.adapter.HospitalAdapter;
import com.btl.medifin.adapter.IntroAdapter;
import com.btl.medifin.adapter.SettingAdapter;
import com.btl.medifin.model.Hospital;
import com.btl.medifin.model.IntroItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class UserHomeFragment extends Fragment implements View.OnClickListener {

    private TextView welcomeName;
    String name;
    ViewPager2 introViewpager;
    HospitalAdapter hospitalAdapter;
    private LinearLayout circleLayout;
    LinearLayoutManager layoutManager;
    private Context context;


    public UserHomeFragment() {
        // Required empty public constructor
    }

    public static UserHomeFragment newInstance() {
        UserHomeFragment fragment = new UserHomeFragment();
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

        setListBv();
        introViewpager.setAdapter(hospitalAdapter);

        setupCircle();
        setActiveCircle(0);

        introViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setActiveCircle(position);
            }
        });

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
        introViewpager = view.findViewById(R.id.list_hospital);
        circleLayout = view.findViewById(R.id.hospital_circle);
    }

    private void setupCircle() {
        ImageView[] circles = new ImageView[hospitalAdapter.getItemCount()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        for (int i = 0; i < 3; i++) {
            circles[i] = new ImageView(getContext());
            circles[i].setImageDrawable(ContextCompat.getDrawable(
                    getContext(), R.drawable.circle_inactive
            ));
            circles[i].setLayoutParams(params);
            circleLayout.addView(circles[i]);
        }
    }

    private void setActiveCircle(int index) {
        int max = hospitalAdapter.getItemCount();
        int childCount = circleLayout.getChildCount();
        if (index < max - 1 && index > 0) {
            for (int i = 0; i < 3; i++) {
                ImageView circleView = (ImageView) circleLayout.getChildAt(i);
                if(i == 1){
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_active));
                } else {
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_inactive));
                }
            }
        } else if (index == max - 1){
            for (int i = 0; i < 3; i++) {
                ImageView circleView = (ImageView) circleLayout.getChildAt(i);
                if(i == 2){
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_active));
                } else {
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_inactive));
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                ImageView circleView = (ImageView) circleLayout.getChildAt(i);
                if(i == 0){
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_active));
                } else {
                    circleView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_inactive));
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottom_nav);
        switch (v.getId()) {
            case R.id.btnDatLich_ndHome:
                bnv.setSelectedItemId(R.id.menu_datLich);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new UserBookingFragment()).commit();
                break;
            case R.id.cvHistory_ndHome:
                bnv.setSelectedItemId(R.id.menu_history);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new UserHistoryFragment()).commit();
                break;
            case R.id.cvProfile_ndHome:
                getActivity().startActivity(new Intent(getContext(), UpdateInfor.class));
                break;
            case R.id.cv_message_ndHome:
                bnv.setSelectedItemId(R.id.menu_medicine);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new UserMedicineFragment()).commit();
                break;
            case R.id.cv_news_ndHome:
                getActivity().startActivity(new Intent(getContext(), News.class));
                break;
        }
    }

    private void setListBv() {
        List<Hospital> hospitals = new ArrayList<>();

        Hospital item1 = new Hospital();
        item1.setImg(R.raw.an_cuong);
        item1.setName("Phòng khám An Cường");
        Hospital item2 = new Hospital();
        item2.setImg(R.raw.hong_ngoc);
        item2.setName("Bệnh viện Hồng Nhọc");
        Hospital item3 = new Hospital();
        item3.setImg(R.raw.vnu);
        item3.setName("Bệnh viện Đại học Y - ĐHQGHN");
        Hospital item4 = new Hospital();
        item4.setImg(R.raw.bach_mai);
        item4.setName("Bệnh viện Bạch Mai");
        Hospital item5 = new Hospital();
        item5.setImg(R.raw.dong_do);
        item5.setName("Bệnh viện Đông Đô");
        Hospital item6 = new Hospital();
        item6.setImg(R.raw.thanh_loi);
        item6.setName("Phòng khám Thành Lợi");

        hospitals.add(item1);
        hospitals.add(item2);
        hospitals.add(item3);
        hospitals.add(item4);
        hospitals.add(item5);
        hospitals.add(item6);
        hospitalAdapter = new HospitalAdapter(hospitals);
    }


}


