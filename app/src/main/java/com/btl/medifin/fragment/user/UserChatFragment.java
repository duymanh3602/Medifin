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

import com.btl.medifin.R;
import com.btl.medifin.adapter.UserChatAdapter;
import com.btl.medifin.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserChatFragment extends Fragment {

    private RecyclerView rcUser;
    private List<Users> usersList;

    private String myId;

    private EditText searchBar;

    public UserChatFragment() {
        // Required empty public constructor
    }

    public static UserChatFragment newInstance() {
        UserChatFragment fragment = new UserChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nd_chat, container, false);
        rcUser = view.findViewById(R.id.rcMedicine);
        rcUser.setLayoutManager(new LinearLayoutManager(getContext()));
        usersList = new ArrayList<>();
        myId = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("USERNAME", "");
        getUserFromDb();

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void searchUser(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("userName")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                Users users = new Users();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    users = ds.getValue(Users.class);
                    if(!users.getUserName().equalsIgnoreCase(myId)){
                        usersList.add(users);
                    }
                }
                UserChatAdapter userChatAdapter = new UserChatAdapter(getContext(), usersList);
                rcUser.setAdapter(userChatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserFromDb() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                Users users = new Users();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    users = ds.getValue(Users.class);
                    if(!users.getUserName().equalsIgnoreCase(myId)){
                        usersList.add(users);
                    }
                }
                UserChatAdapter userChatAdapter = new UserChatAdapter(getContext(), usersList);
                rcUser.setAdapter(userChatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}