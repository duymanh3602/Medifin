package com.btl.medifin.fragment.user;

import android.content.Context;
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
import android.widget.Spinner;

import com.btl.medifin.R;
import com.btl.medifin.adapter.DoctorAdapter;
import com.btl.medifin.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserBookingFragment extends Fragment {
    private DatabaseReference ref;
    private Context mContext;
    private RecyclerView rcDatLich;
    private List<Users> mUsers;
    private DoctorAdapter datLichAdapter;
    private EditText searchDoctor;

    public UserBookingFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static UserBookingFragment newInstance() {
        UserBookingFragment fragment = new UserBookingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_dat_lich, container, false);
        rcDatLich = view.findViewById(R.id.rcDatLichKham);

        getDataDoctor();
        searchDoctor = view.findViewById(R.id.searchDoctor);
        searchDoctor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDoctor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void searchDoctor(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("fullName")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                Users doc = new Users();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    doc = ds.getValue(Users.class);
                    if (doc.getLevel().equals("Bác Sĩ")) {
                        mUsers.add(doc);
                    }

                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rcDatLich.setLayoutManager(layoutManager);
                rcDatLich.setAdapter(datLichAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataDoctor() {
        mUsers = new ArrayList<>();
        mUsers.clear();
        ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("level").getValue(String.class).equalsIgnoreCase("Bác Sĩ")){
                        Users bacSi = ds.getValue(Users.class);
                        if(!bacSi.getFullName().isEmpty())
                            mUsers.add(bacSi);
                    }
                    datLichAdapter = new DoctorAdapter(mUsers);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcDatLich.setLayoutManager(layoutManager);
                    rcDatLich.setAdapter(datLichAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}