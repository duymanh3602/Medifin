package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Booking extends AppCompatActivity {

    private List<MedBill> medBills = new ArrayList<>();
    private List<MedBill> duplicateList = new ArrayList<>();
    private ArrayList<String> duplicateTime = new ArrayList<>();
    private Spinner timespinner, datespinner;
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
        btnTaoPhieu = findViewById(R.id.btnTaoPhieuKham);
        btnHuy = findViewById(R.id.btnHuyPhieuKham);
        docAdd = findViewById(R.id.docAddress);
        docInf = findViewById(R.id.docInf);
        rate = findViewById(R.id.innerRating);
        timespinner = findViewById(R.id.time_spinner);
        datespinner = findViewById(R.id.date_spinner);

        idBs = getIntent().getStringExtra("IDBS");
        tenBs = getIntent().getStringExtra("TENBS");


        TextView tvTenBs = findViewById(R.id.tvTenBs_datLichNd);
        tvTenBs.setText("Bác sĩ: " + tenBs);

        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        idBn = prefs.getString("USERNAME", "");
        tenBn = prefs.getString("FULLNAME", "");

        getDataBsi(getIntent().getStringExtra("IDBS"));
        getRateBsi(getIntent().getStringExtra("IDBS"));
        setSpinnerDate();
        datespinner.setSelection(0);
        //setSpinnerTime(datespinner.getItemAtPosition(0).toString());
        getTimeDuplicate(datespinner.getItemAtPosition(0).toString());

        datespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //setSpinnerTime(datespinner.getSelectedItem().toString());
                getTimeDuplicate(datespinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
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

        if (checkError) {
            ref = FirebaseDatabase.getInstance().getReference().child("History");
            MedBill medBill = new MedBill("null", "null", "null", "null", "null", "Đang chờ", "null", "null", "null", "null", 0);
            medBill.setId(ref.push().getKey());
            medBill.setIdBs(idBs);
            medBill.setTenBs(tenBs);
            medBill.setIdBn(idBn);
            medBill.setTenBn(tenBn);
            medBill.setDate(datespinner.getSelectedItem().toString());
            medBill.setTime(timespinner.getSelectedItem().toString());
            ref.child(medBill.getId()).setValue(medBill);
            finish();
        }
    }

    private void getDataBsi(String idBs) {
        data = FirebaseDatabase.getInstance().getReference().child("users").child(idBs);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                docAddress = dataSnapshot.child("docAdd").getValue(String.class);
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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(idBs)) {
                        if (medBill.getStatus().equals("Hoàn thành")) {
                            medBills.add(medBill);
                        }
                    }
                }
                rate.setText("Đánh giá: " + String.format("%.1f", calRate(medBills)) + "/5.0 sao");
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
        if (medBills.size() == 0) {
            return 5;
        } else {
            return rate / (double) (medBills.size());
        }
    }

    private void setSpinnerDate() {
        ArrayList<String> arrayList = new ArrayList<>();

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar c = Calendar.getInstance();
        Date today = new Date();
        if (c.get(Calendar.HOUR_OF_DAY) >= 17) {
            c.add(Calendar.DATE, 1);
            today = c.getTime();
        }

        String date = simpleDateFormat.format(today);
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        String tomorrow = simpleDateFormat.format(c.getTime());
        c.add(Calendar.DATE,1);
        String twomorrow = simpleDateFormat.format(c.getTime());

        arrayList.add(date);
        arrayList.add(tomorrow);
        arrayList.add(twomorrow);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        datespinner.setGravity(10);
        datespinner.setAdapter(arrayAdapter);
    }

    private void getTimeDuplicate(String date) {
        duplicateTime = new ArrayList<>();
        data = FirebaseDatabase.getInstance().getReference().child("History");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedBill medBill = new MedBill();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(getIntent().getStringExtra("IDBS"))) {
                        if (medBill.getDate().equals(date) && medBill.getStatus().equals("Đang chờ")) {
                            duplicateTime.add(medBill.getTime().toString());
                        }
                    }
                }

                setSpinnerTime(date, duplicateTime);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setSpinnerTime(String date, ArrayList<String> time) {
        ArrayList<String> arrayList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        String[] day = date.split("/");
        //System.out.println(Integer.parseInt(day[0]) + " - " + Integer.parseInt(day[1])+ " - " + Integer.parseInt(day[2]));
        //check same day
        if (Integer.parseInt(day[0]) == c.get(Calendar.DATE)) {
            if (c.get(Calendar.HOUR_OF_DAY) >= 17) {
                for (int h = 8; h < 18; h++) {
                    if ( h == 12 || h == 13) {
                        continue;
                    }
                    arrayList.add(h + ":00");
                    if ( h == 11) {
                        continue;
                    }
                    arrayList.add(h + ":30");
                }
            } else if (c.get(Calendar.HOUR_OF_DAY) >= 8){
                if (c.get(Calendar.MINUTE) < 30) {
                    arrayList.add(c.get(Calendar.HOUR_OF_DAY) + ":30");
                    for (int h = c.get(Calendar.HOUR_OF_DAY) + 1; h < 18; h++) {
                        if ( h == 12 || h == 13) {
                            continue;
                        }
                        arrayList.add(h + ":00");
                        if ( h == 11) {
                            continue;
                        }
                        arrayList.add(h + ":30");
                    }
                } else {
                    for (int h = c.get(Calendar.HOUR_OF_DAY) + 1; h < 18; h++) {
                        if ( h == 12 || h == 13) {
                            continue;
                        }
                        arrayList.add(h + ":00");
                        if ( h == 11) {
                            continue;
                        }
                        arrayList.add(h + ":30");
                    }
                }
            } else {
                for (int h = 8; h < 18; h++) {
                    if ( h == 12 || h == 13) {
                        continue;
                    }
                    arrayList.add(h + ":00");
                    if ( h == 11) {
                        continue;
                    }
                    arrayList.add(h + ":30");
                }
            }
        } else {
            for (int h = 8; h < 18; h++) {
                if ( h == 12 || h == 13) {
                    continue;
                }
                arrayList.add(h + ":00");
                if ( h == 11) {
                    continue;
                }
                arrayList.add(h + ":30");
            }
        }

        // last filter
        for (int i = 0; i < arrayList.size(); i++) {
            if (time.contains(arrayList.get(i))) {
                arrayList.remove(i);
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        timespinner.setGravity(10);
        timespinner.setAdapter(arrayAdapter);
    }
}