package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.medifin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Medicine extends AppCompatActivity {

    private TextView nameMedicine, medicineDescription, medicineDose;
    private ImageView imageMedicine, back;

    private List<com.btl.medifin.model.Medicine> medicineList;
    private DatabaseReference ref;
    private RecyclerView recyclerView;

    private String medicineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        getSupportActionBar().hide();
        mappingView();

        medicineId = getIntent().getStringExtra("ID").toString();

        getMedicineInfor(medicineId);

        back.setOnClickListener(v -> {
            finish();
        });

    }

    private void getMedicineInfor(String name) {
        ref = FirebaseDatabase.getInstance().getReference("medicin");
        ref.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    com.btl.medifin.model.Medicine medicine = dataSnapshot.getValue(com.btl.medifin.model.Medicine.class);
                    nameMedicine.setText("Tên: " + medicine.getName());
                    medicineDescription.setText("Mô tả: " + medicine.getDescription());
                    medicineDose.setText("Liều: " + medicine.getDose());
                } else {
                    Log.d("MedicineInfo", "Medicine not found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MedicineInfo", "Cannot connect to Database");
            }
        });
    }

    private void mappingView() {
        nameMedicine = findViewById(R.id.nameMedicine);
        medicineDescription = findViewById(R.id.medicineDescription);
        medicineDose = findViewById(R.id.medicineDose);
        back = findViewById(R.id.medInfoBack);
        //imageMedicine = findViewById(R.id.imgMedicine);
    }
}