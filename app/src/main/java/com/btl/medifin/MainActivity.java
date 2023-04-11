package com.btl.medifin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.btl.medifin.activity.SignUp;
import com.btl.medifin.activity.UpdateInfor;
import com.btl.medifin.fragment.bacsi.BsHomeFragment;
import com.btl.medifin.fragment.bacsi.BsLichKhamFragment;
import com.btl.medifin.fragment.nguoidung.NdChatFragment;
import com.btl.medifin.fragment.nguoidung.NdDatLichFragment;
import com.btl.medifin.fragment.nguoidung.NdHistoryFragment;
import com.btl.medifin.fragment.nguoidung.NdHomeFragment;
import com.btl.medifin.fragment.nguoidung.NdSettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    DatabaseReference ref;

    private SharedPreferences prefs;
    public static final String AGE = "AGE";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String EMAIL = "EMAIL";
    public static final String FULLNAME = "FULLNAME";
    public static final String LEVEL = "LEVEL";
    public static final String PHONE = "PHONE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testDbConnect();
        getSupportActionBar().hide();
        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        String level = prefs.getString(LEVEL, "");
        String checkNameIsBlank = prefs.getString(FULLNAME, "");
//        String userName = prefs.getString(USERNAME, "");

        if(checkNameIsBlank.isEmpty()){
            Toast.makeText(this, "ten trong", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UpdateInfor.class));
            finish();
        }

//        Bundle b = new Bundle();
//        b.putString(USERNAME, userName);
        if(level.equals("Bệnh Nhân")){
            addViewUsers();
        } else {
            addViewDoctors();
        }
    }

    private void addViewDoctors() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new BsHomeFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment = null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    currentFragment = new BsHomeFragment();
                    break;
                case R.id.menu_history:
                    currentFragment = new NdHistoryFragment();
                    break;
                case R.id.menu_datLich:
                    currentFragment = new BsLichKhamFragment();
                    break;
                case R.id.menu_chat:
                    currentFragment = new NdChatFragment();
                    break;
                case R.id.menu_setting:
                    currentFragment = new NdSettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });
    }

    private void addViewUsers() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new NdHomeFragment()).commit();

        bnv.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment = null;
            switch (item.getItemId()){
                case R.id.menu_home:
                    currentFragment = new NdHomeFragment();
                    break;
                case R.id.menu_history:
                    currentFragment = new NdHistoryFragment();
                    break;
                case R.id.menu_datLich:
                    currentFragment = new NdDatLichFragment();
                    break;
                case R.id.menu_chat:
                    currentFragment = new NdChatFragment();
                    break;
                case R.id.menu_setting:
                    currentFragment = new NdSettingFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, currentFragment).commit();
            return true;
        });
    }

    private void mappingView() {
        bnv = findViewById(R.id.bottom_nav);
    }

    private void testDbConnect() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("medicine");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("SUCCESS!");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Fail");
            }
        });
    }
}