package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.btl.medifin.R;
import com.btl.medifin.adapter.MedicineAdapter;
import com.btl.medifin.adapter.NewsAdapter;
import com.btl.medifin.model.Medicine;
import com.btl.medifin.model.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {

    private ImageView imgNotice, back;
    private TextView title, date;

    private DatabaseReference ref;
    private RecyclerView recyclerView;

    private List<Notification> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = findViewById(R.id.listNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().hide();
        mappingView();
        getNotice();

        back.setOnClickListener( v-> {
            finish();
        });
    }

    private void getNotice() {
        ref = FirebaseDatabase.getInstance().getReference("notice");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notifications.clear();
                com.btl.medifin.model.Notification notification = new com.btl.medifin.model.Notification();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    notification = ds.getValue(Notification.class);
                    notifications.add(notification);
                    System.out.println(notifications.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("News", "Cannot connect to Database");
            }
        });
        NewsAdapter newsAdapter = new NewsAdapter( this,notifications);
        recyclerView.setAdapter(newsAdapter);
    }

    private void mappingView() {
        imgNotice = findViewById(R.id.noticeImg);
        title = findViewById(R.id.titleNotice);
        date = findViewById(R.id.dateNotice);

        back = findViewById(R.id.backNews);

    }

    public int getInden(String name) {
        int resourcesCode = getResources().getIdentifier(name, "raw", this.getPackageName());
        return resourcesCode;
    }
}