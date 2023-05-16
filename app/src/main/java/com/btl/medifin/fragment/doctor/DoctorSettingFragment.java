package com.btl.medifin.fragment.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.btl.medifin.R;
import com.btl.medifin.activity.SignIn;
import com.btl.medifin.adapter.SettingAdapter;
import com.btl.medifin.model.Setting;
import com.btl.medifin.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorSettingFragment extends Fragment {


    private TextView tvIdTaiKhoan, userName, userMail;
    private ListView lvSetting;
    List<Setting> mLists;
    private Context context;
    private SharedPreferences prefs;
    private DatabaseReference reference;
    private Switch nghiKham;

    public DoctorSettingFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static DoctorSettingFragment newInstance() {
        DoctorSettingFragment fragment = new DoctorSettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bs_setting, container, false);
        mapping(view);
        addSettingItem();
        setDataAdapter();
        setNghiKhamStatus();
        nghiKham.setOnClickListener(v-> {
            nghiKham();
        });
        return view;
    }

    private void setDataAdapter() {
        SettingAdapter settingAdapter = new SettingAdapter(context, mLists);
        lvSetting.setDivider(null);
        lvSetting.setAdapter(settingAdapter);
        lvSetting.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 7:
                    context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().clear().commit();

                    context.startActivity(new Intent(context, SignIn.class));
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        });
    }

    private void setNghiKhamStatus() {
        String userName = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "");
        reference = FirebaseDatabase.getInstance().getReference("users").child(userName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users temp = dataSnapshot.getValue(Users.class);
                if (temp.getLevel().equals("Bác Sĩ Nghỉ")) {
                    nghiKham.setChecked(true);
                } else {
                    nghiKham.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void nghiKham() {
        String userName = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "");
        reference = FirebaseDatabase.getInstance().getReference("users").child(userName);
        if (nghiKham.isChecked()) {
            reference.child("level").setValue("Bác Sĩ Nghỉ");
        } else {
            reference.child("level").setValue("Bác Sĩ");
        }
    }



    private void addSettingItem() {
        mLists = new ArrayList<>();

        Setting obj = new Setting();
        obj.setImage(R.drawable.insurance);
        obj.setTitle("Quy định sử dụng");
        mLists.add(obj);

        Setting obj2 = new Setting();
        obj2.setImage(R.drawable.security);
        obj2.setTitle("Chính sách bảo mật");
        mLists.add(obj2);

        Setting obj3 = new Setting();
        obj3.setImage(R.drawable.customer);
        obj3.setTitle("Điều khoản dịch vụ");
        mLists.add(obj3);

        Setting obj4 = new Setting();
        obj4.setImage(R.drawable.phone_call);
        obj4.setTitle("Tổng đài CSKH 19001009");
        mLists.add(obj4);

        Setting obj5 = new Setting();
        obj5.setImage(R.drawable.like);
        obj5.setTitle("Đánh giá ứng dụng");
        mLists.add(obj5);

        Setting obj6 = new Setting();
        obj6.setImage(R.drawable.network);
        obj6.setTitle("Chia sẻ ứng dụng");
        mLists.add(obj6);

        Setting obj9 = new Setting();
        obj9.setImage(R.drawable.conversation);
        obj9.setTitle("Một số câu hỏi thường gặp");
        mLists.add(obj9);

        Setting obj7 = new Setting();
        obj7.setImage(R.drawable.sign_out);
        obj7.setTitle("Đăng xuất");
        mLists.add(obj7);

        Setting obj8 = new Setting();
        obj8.setImage(R.drawable.languages);
        obj8.setTitle("Ngôn ngữ");
        mLists.add(obj8);

        Setting obj10 = new Setting();
        obj10.setImage(R.drawable.merge);
        obj10.setTitle("Phiên bản v1.6.9");
        mLists.add(obj10);


    }

    private void mapping(View view) {
        tvIdTaiKhoan = view.findViewById(R.id.tv_taiKhoan_setting_bs);
        userName = view.findViewById(R.id.userName_bs);
        userMail = view.findViewById(R.id.usermail_bs);
        lvSetting = view.findViewById(R.id.lv_setting_bs);
        nghiKham = view.findViewById(R.id.nghi_kham);

        tvIdTaiKhoan.setText("Tài khoản: " + context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", ""));
        userName.setText(context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("FULLNAME",""));
        userMail.setText(context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("EMAIL",""));

    }
}