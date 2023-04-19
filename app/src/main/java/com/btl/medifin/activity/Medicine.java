package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    com.btl.medifin.model.Medicine medicine = dataSnapshot.getValue(com.btl.medifin.model.Medicine.class);
                    //imageMedicine.setImageURI(Uri.parse("https://res.cloudinary.com/dsgac7fag/image/upload/v1681840651/thuoc_khang_sinh_mun_zwjk0p.jpg"));
                    //imageMedicine.setImageBitmap(getBitmapFromURL("https://res.cloudinary.com/dsgac7fag/image/upload/v1681840651/thuoc_khang_sinh_mun_zwjk0p.jpg"));
                    String path = "R.raw." + medicine.getImg();
                    int resourcesCode = getResources().getIdentifier(medicine.getImg(), "raw", getPackageName());
                    imageMedicine.setImageResource(resourcesCode);
                    //imageMedicine.setMa
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            Log.d("Medicine", e.toString());
            return null;
        }
    }

    private void mappingView() {
        nameMedicine = findViewById(R.id.nameMedicine);
        medicineDescription = findViewById(R.id.medicineDescription);
        medicineDose = findViewById(R.id.medicineDose);
        back = findViewById(R.id.medInfoBack);
        imageMedicine = findViewById(R.id.imgMedicineInfo);
    }
}