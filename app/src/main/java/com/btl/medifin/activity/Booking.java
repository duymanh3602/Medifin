package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.btl.medifin.MainActivity;
import com.btl.medifin.R;
import com.btl.medifin.model.PhieuKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class Booking extends AppCompatActivity {
    private EditText edNgay, edGio;
    private Button btnTaoPhieu, btnHuy;
    private DatabaseReference ref, data;
    private SharedPreferences prefs;
    String idBs, idBn, tenBs, tenBn, docAddress, docInfo;
    TextView docAdd, docInf;

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
            PhieuKham phieuKham = new PhieuKham("null", "null", "null", "null", "null", "Đang chờ", "null", "null", "null", "null", 0);
            phieuKham.setId(ref.push().getKey());
            phieuKham.setIdBs(idBs);
            phieuKham.setTenBs(tenBs);
            phieuKham.setIdBn(idBn);
            phieuKham.setTenBn(tenBn);
            phieuKham.setDate(edNgay.getText().toString().trim());
            phieuKham.setTime(edGio.getText().toString().trim());
            ref.child(phieuKham.getId()).setValue(phieuKham);
            finish();
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

        new TimePickerDialog(this, onTimeSetListener, hour, minute, true).show();
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
}