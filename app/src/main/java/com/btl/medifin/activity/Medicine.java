package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.btl.medifin.R;
import com.btl.medifin.adapter.MedicineAdapter;
import com.btl.medifin.adapter.MessageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Medicine extends AppCompatActivity {

    private List<com.btl.medifin.model.Medicine> medicineList;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private MedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        getSupportActionBar().hide();

    }

    private void getMedicineList(){
        medicineList = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("medicine");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    com.btl.medifin.model.Medicine obj = ds.getValue(com.btl.medifin.model.Medicine.class);

                        medicineList.add(obj);

                }

                medicineAdapter = new MedicineAdapter(Medicine.this, medicineList);
                recyclerView.setAdapter(medicineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}