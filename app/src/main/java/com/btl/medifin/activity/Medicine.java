package com.btl.medifin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.btl.medifin.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Medicine extends AppCompatActivity {

    private List<com.btl.medifin.model.Medicine> medicineList;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    //private MedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        getSupportActionBar().hide();

    }
}