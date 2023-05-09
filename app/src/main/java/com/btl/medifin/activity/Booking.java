package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.btl.medifin.R;
import com.btl.medifin.model.MedBill;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Booking extends AppCompatActivity{

    private List<MedBill> medBills = new ArrayList<>();
    private List<MedBill> duplicate = new ArrayList<>();
    private EditText edNgay, edGio;
    //private Button btnTaoPhieu, btnHuy;
    private TimePickerDialog timePickerDialog;
    private TextView btnHuy;
    private MaterialButton btnTaoPhieu;
    private DatabaseReference ref, data;
    private SharedPreferences prefs;
    String idBs, idBn, tenBs, tenBn, docAddress, docInfo;
    TextView docAdd, docInf, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().hide();
        edNgay = findViewById(R.id.edNgay_TaoPhieuKham);
        edGio = findViewById(R.id.edGio_TaoPhieuKham);
        btnTaoPhieu = findViewById(R.id.btnTaoPhieuKham);
        btnHuy = findViewById(R.id.btnHuyPhieuKham);
        docAdd = findViewById(R.id.docAddress);
        docInf = findViewById(R.id.docInf);
        rate = findViewById(R.id.innerRating);

        idBs = getIntent().getStringExtra("IDBS");
        tenBs = getIntent().getStringExtra("TENBS");


        TextView tvTenBs = findViewById(R.id.tvTenBs_datLichNd);
        tvTenBs.setText("Bác sĩ: "+tenBs);

        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        idBn = prefs.getString("USERNAME", "");
        tenBn = prefs.getString("FULLNAME", "");
//        docAddress = prefs.getString("ADDRESS", "");
//        docInfo = prefs.getString("INFO", "");

        getDataBsi(getIntent().getStringExtra("IDBS"));
        getRateBsi(getIntent().getStringExtra("IDBS"));


        edNgay.setOnClickListener(v -> {
            showDateDialog();
        });
        edGio.setOnClickListener(v -> {
            showTimeDialog();
        });
        btnHuy.setOnClickListener(v -> {
            finish();
        });
        btnTaoPhieu.setOnClickListener(v -> {
            taoPhieuKham();
        });
    }

    private void taoPhieuKham() {
        Boolean checkError = true;
        if(edNgay.getText().toString().trim().isEmpty()){
            edNgay.setError("Không được bỏ trống ngày");
            checkError = false;
        }
        if(edGio.getText().toString().trim().isEmpty()){
            edGio.setError("Không được bỏ trống thời gian");
            checkError = false;
        }
        if(checkError){
            ref = FirebaseDatabase.getInstance().getReference().child("History");
            MedBill medBill = new MedBill("null", "null", "null", "null", "null", "Đang chờ", "null", "null", "null", "null", 0);
            medBill.setId(ref.push().getKey());
            medBill.setIdBs(idBs);
            medBill.setTenBs(tenBs);
            medBill.setIdBn(idBn);
            medBill.setTenBn(tenBn);
            medBill.setDate(edNgay.getText().toString().trim());
            medBill.setTime(edGio.getText().toString().trim());
            ref.child(medBill.getId()).setValue(medBill);
            finish();
        }
    }

    private void setTimePickerInterval(TimePicker timePicker) {
        int TIME_PICKER_INTERVAL = 15;
        try {

            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
        } catch (Exception e) {
            Log.e("TAG", "Exception: " + e);
        }
    }

    private void showTimeDialog() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                edGio.setText(time);
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        tpd.show();
    }

    private void showDateDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                edNgay.setText(date);
            }
        };

        new DatePickerDialog(this, onDateSetListener, year, month, day).show();
    }

    private void getDataBsi(String idBs) {
        data = FirebaseDatabase.getInstance().getReference().child("users").child(idBs);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docAddress =  dataSnapshot.child("docAdd").getValue(String.class);
                docInfo = dataSnapshot.child("docInfo").getValue(String.class);
                docInf.setText("Thông Tin: " + docInfo);
                docAdd.setText("Địa Chỉ: " + docAddress);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DocInfo", "Fail to found this docterID");
            }
        });
    }

    private void getRateBsi(String idBs) {
        data = FirebaseDatabase.getInstance().getReference().child("History");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedBill medBill = new MedBill();
                medBills.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(idBs)) {
                        if (medBill.getStatus().equals("Hoàn thành")) {
                            medBills.add(medBill);
                        }
                    }
                }
                //calRate(medBills);
                rate.setText("Đánh giá: " + calRate(medBills) + "/5.0 sao");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double calRate(List<MedBill> medBills) {
        double rate = 0;
        for (int i = 0; i < medBills.size(); i++) {
            rate += medBills.get(i).getRate();
        }
        if(medBills.size() == 0) {
            return 5;
        } else {
            return rate / (double) (medBills.size());
        }
    }

    private boolean checkValidTime(String idBs, String time, String date) {
        data = FirebaseDatabase.getInstance().getReference().child("History");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedBill medBill = new MedBill();
                duplicate.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(idBs)) {
                        if (medBill.getTime().equals(time) && medBill.getDate().equals(date)) {
                            duplicate.add(medBill);
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (duplicate.size() != 0) {
            return false;
        } else {
            return true;
        }
    }
}