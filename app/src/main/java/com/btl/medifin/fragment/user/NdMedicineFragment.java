package com.btl.medifin.fragment.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.btl.medifin.R;
import com.btl.medifin.adapter.MedicineAdapter;
import com.btl.medifin.model.Medicine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NdMedicineFragment extends Fragment {

    private RecyclerView rcMedi;
    private List<Medicine> medicineList;

    private EditText searchBar;

    public NdMedicineFragment() {
        // Required empty public constructor
    }

    public static NdChatFragment newInstance() {
        NdChatFragment fragment = new NdChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_medicine, container, false);
        rcMedi = view.findViewById(R.id.rcMedicine);
        rcMedi.setLayoutManager(new LinearLayoutManager(getContext()));
        medicineList = new ArrayList<>();
        getMedicineFromDb();

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void search(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("medicin").orderByChild("name")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                Medicine medicine = new Medicine();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    medicine = ds.getValue(Medicine.class);
                    medicineList.add(medicine);

                }
                MedicineAdapter medicineAdapter = new MedicineAdapter(getContext(), medicineList);
                rcMedi.setAdapter(medicineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getMedicineFromDb() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("medicin");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList.clear();
                Medicine medicine = new Medicine();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    medicine = ds.getValue(Medicine.class);
                    System.out.printf(medicine.toString());
                    medicineList.add(medicine);
                }
                MedicineAdapter medicineAdapter = new MedicineAdapter(getContext(), medicineList);
                rcMedi.setAdapter(medicineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setMedImage() {

    }
}