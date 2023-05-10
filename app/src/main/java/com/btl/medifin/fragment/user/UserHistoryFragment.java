package com.btl.medifin.fragment.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.btl.medifin.R;
import com.btl.medifin.adapter.HistoryAdapter;
import com.btl.medifin.model.MedBill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UserHistoryFragment extends Fragment {
    private EditText edFirstDate, edSecondDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private RecyclerView rcHistory;
    private Spinner spinner;
    private List<MedBill> medBillList;

    public static final String formatTime = "HH:mm";
    public static final String formatDate = "dd/MM/yyyy";



    public UserHistoryFragment() {
        // Required empty public constructor
    }

    public static UserHistoryFragment newInstance() {
        UserHistoryFragment fragment = new UserHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_history, container, false);
        rcHistory = view.findViewById(R.id.rcLichSuKhamNd);
        spinner = view.findViewById(R.id.history_mode);
        rcHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        setSpinner();
        getLichSu(0);
        spinner.setSelection(3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String mode = spinner.getSelectedItem().toString();
                if (mode.equals("Tất cả")) {
                    getLichSu(0);
                } else {
                    filter(mode);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        return view;
    }

    private void setSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Đang chờ");
        arrayList.add("Đang khám");
        arrayList.add("Hoàn thành");
        arrayList.add("Tất cả");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinner.setGravity(10);
        spinner.setAdapter(arrayAdapter);
    }

    private void getLichSu(int i) {
        if ( i == 0) {
            getHistoryFromDb();
        }
    }

    private void getHistoryFromDb() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.orderByChild("date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String idNd = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "none");
                    String level = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("LEVEL", "none");
                    medBillList = new ArrayList<>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    && ds.child("status").getValue(String.class).equalsIgnoreCase("Hoàn thành")
                        if(level.equalsIgnoreCase("Bệnh Nhân")){
                            if(ds.child("idBn").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                MedBill obj = ds.getValue(MedBill.class);
                                medBillList.add(obj);
                                //Toast.makeText(getContext(), "found! " + obj.getDate() + " " + obj.getNote(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(ds.child("idBs").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                MedBill obj = ds.getValue(MedBill.class);
                                medBillList.add(obj);
                                //Toast.makeText(getContext(), "found! " + obj.getDate() + " " + obj.getNote(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), medBillList);
                    rcHistory.setAdapter(historyAdapter);
                } catch (NullPointerException e){
                    Log.e("===//", ""+e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Boolean compareDate(Date myDate){
//        Date beforeDate = parseDate(edFirstDate.getText().toString().trim());
//        Date afterDate = parseDate(edSecondDate.getText().toString().trim());
//
//        if(beforeDate.compareTo(myDate) == 0 || beforeDate.before(myDate) && afterDate.after(myDate) || afterDate.compareTo(myDate) == 0){
//            return true;
//        }
//        else return false;
        return true;
    }

    private void filter(String option) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History");
        databaseReference.orderByChild("date");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String idNd = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "none");
                    String level = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("LEVEL", "none");
                    medBillList = new ArrayList<>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        if(level.equalsIgnoreCase("Bệnh Nhân") && ds.child("status").getValue(String.class).equalsIgnoreCase(option)){
                            if(ds.child("idBn").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                MedBill obj = ds.getValue(MedBill.class);
                                medBillList.add(obj);
                            }
                        } else {
                            if(ds.child("idBs").getValue(String.class).equalsIgnoreCase(idNd) && compareDate(parseDate(ds.child("date").getValue(String.class)))){
                                MedBill obj = ds.getValue(MedBill.class);
                                medBillList.add(obj);
                            }
                        }
                    }
                    HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), medBillList);
                    rcHistory.setAdapter(historyAdapter);
                } catch (NullPointerException e){
                    Log.e("===//", ""+e);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private Date parseDate(String date){
        SimpleDateFormat inputParser = new SimpleDateFormat(formatDate);
        try {
            return inputParser.parse(date);
        } catch (ParseException e){
            return new Date(0);
        }
    }

}