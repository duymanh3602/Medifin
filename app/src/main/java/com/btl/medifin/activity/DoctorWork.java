package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.btl.medifin.R;
import com.btl.medifin.model.MedBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DoctorWork extends AppCompatActivity {

    private LinearLayout l1, l2, l3, l4, l5;
    private DatabaseReference data;
    private Spinner spinner;
    private ImageView back;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_work);
        getSupportActionBar().hide();
        mappingView();
        setupSpinner();
        getDataDoctor(spinner.getItemAtPosition(0).toString());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String date = spinner.getSelectedItem().toString();
                getDataDoctor(date);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        back.setOnClickListener(v-> {
            finish();
        });
    }

    private void mappingView() {
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        back = findViewById(R.id.work_back);
        spinner = findViewById(R.id.date_chose);
    }

    private void setupSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar c = Calendar.getInstance();
        Date today = new Date();
        if (c.get(Calendar.HOUR_OF_DAY) >= 18) {
            c.add(Calendar.DATE, 1);
            today = c.getTime();
        }

        String date = simpleDateFormat.format(today);
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        String tomorrow = simpleDateFormat.format(c.getTime());
        c.add(Calendar.DATE, 1);
        String twomorrow = simpleDateFormat.format(c.getTime());

        arrayList.add(date);
        arrayList.add(tomorrow);
        arrayList.add(twomorrow);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setGravity(10);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDataDoctor(String date) {
        Calendar calendar = Calendar.getInstance();
        String[] day = date.split("/");
        ArrayList<String> duplicateTime = new ArrayList<>();
        data = FirebaseDatabase.getInstance().getReference().child("History");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedBill medBill = new MedBill();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", ""))) {
                        if (medBill.getDate().equals(date) && medBill.getStatus().equals("Đang chờ")) {
                            duplicateTime.add(medBill.getTime().toString());
                        }
                    }
                }
                if (Integer.parseInt(day[0]) == calendar.get(Calendar.DATE)) {
                    if (calendar.get(Calendar.HOUR_OF_DAY) < 17 || (calendar.get(Calendar.MINUTE) < 30 && calendar.get(Calendar.HOUR_OF_DAY) == 17)) {
                        setupDotorWork(duplicateTime, true);
                    }
                } else {
                    setupDotorWork(duplicateTime, false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupDotorWork(ArrayList<String> list, boolean today) {

        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        // line 1
        if (list.contains("8:00")) {
            l1.getChildAt(0).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l1.getChildAt(0).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("8:30")) {
            l1.getChildAt(1).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l1.getChildAt(1).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("9:00")) {
            l1.getChildAt(2).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l1.getChildAt(2).setBackgroundResource(R.drawable.bs_work_empty);
        }

        // line 2
        if (list.contains("9:30")) {
            l2.getChildAt(0).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l2.getChildAt(0).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("10:00")) {
            l2.getChildAt(1).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l2.getChildAt(1).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("10:30")) {
            l2.getChildAt(2).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l2.getChildAt(2).setBackgroundResource(R.drawable.bs_work_empty);
        }

        // line 3
        if (list.contains("11:00")) {
            l3.getChildAt(0).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l3.getChildAt(0).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("14:00")) {
            l3.getChildAt(1).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l3.getChildAt(1).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("14:30")) {
            l3.getChildAt(2).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l3.getChildAt(2).setBackgroundResource(R.drawable.bs_work_empty);
        }

        // line 4
        if (list.contains("15:00")) {
            l4.getChildAt(0).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l4.getChildAt(0).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("15:30")) {
            l4.getChildAt(1).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l4.getChildAt(1).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("16:00")) {
            l4.getChildAt(2).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l4.getChildAt(2).setBackgroundResource(R.drawable.bs_work_empty);
        }

        // line 5
        if (list.contains("16:30")) {
            l5.getChildAt(0).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l5.getChildAt(0).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("17:00")) {
            l5.getChildAt(1).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l5.getChildAt(1).setBackgroundResource(R.drawable.bs_work_empty);
        }
        if (list.contains("17:30")) {
            l5.getChildAt(2).setBackgroundResource(R.drawable.bs_work_full);
        } else {
            l5.getChildAt(2).setBackgroundResource(R.drawable.bs_work_empty);
        }

        if (today) {
            checkTodayPass(h, m);
        }
    }

    private void checkTodayPass(int h, int m) {
        // line 1
        if (h > 8 || (h == 8 && m > 30)) {
            l1.getChildAt(0).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 8) {
            l1.getChildAt(1).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 9 || (h == 9 && m > 30)) {
            l1.getChildAt(2).setBackgroundResource(R.drawable.bs_work_pass);
        }

        // line 2
        if (h > 9) {
            l2.getChildAt(0).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 10 || (h == 10 && m > 30)) {
            l2.getChildAt(1).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 10) {
            l2.getChildAt(2).setBackgroundResource(R.drawable.bs_work_pass);
        }

        // line 3
        if (h > 11 || (h == 11 && m > 30)) {
            l3.getChildAt(0).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 14 || (h == 14 && m > 30)) {
            l3.getChildAt(1).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 14) {
            l3.getChildAt(2).setBackgroundResource(R.drawable.bs_work_pass);
        }

        // line 4
        if (h > 15 || (h == 15 && m > 30)) {
            l4.getChildAt(0).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 15) {
            l4.getChildAt(1).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 16 || (h == 16 && m > 30)) {
            l4.getChildAt(2).setBackgroundResource(R.drawable.bs_work_pass);
        }

        // line 5
        if (h > 16) {
            l5.getChildAt(0).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 17 || (h == 17 && m > 30)) {
            l5.getChildAt(1).setBackgroundResource(R.drawable.bs_work_pass);
        }
        if (h > 17) {
            l5.getChildAt(2).setBackgroundResource(R.drawable.bs_work_pass);
        }
    }

}