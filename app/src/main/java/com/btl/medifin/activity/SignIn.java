package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.btl.medifin.MainActivity;
import com.btl.medifin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private SharedPreferences prefs;
    private EditText edUsername, edPass;

    public static final String AGE = "AGE";
    public static final String BIRTHDAY = "BIRTHDAY";
    public static final String EMAIL = "EMAIL";
    public static final String FULLNAME = "FULLNAME";
    public static final String LEVEL = "LEVEL";
    public static final String PHONE = "PHONE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        //isFirstLogin = true thì show màn hình giới thiệu
        Boolean isFirstLogin = prefs.getBoolean("ISFIRST", true);
        if(isFirstLogin) {
            prefs.edit().putBoolean("ISFIRST", false).apply();
            startActivity(new Intent(SignIn.this, Intro.class));
            finish();
        }
        if(prefs.getBoolean("REMEMBERLOGIN", false)){
            Intent dangNhapIntent = new Intent(SignIn.this, MainActivity.class);
            startActivity(dangNhapIntent);
            finish();
        }
        setupListener();
    }

    private void mappingView() {
        edUsername = findViewById(R.id.dangNhap_edusername);
        edPass = findViewById(R.id.dangNhap_edpass);
    }

    private void setupListener(){
        findViewById(R.id.dangNhap_ivPhone).setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
        });

        findViewById(R.id.dangNhap_btnDangnhap).setOnClickListener(v -> {
            String userNameInput = edUsername.getText().toString().trim();
            String passwordInput = edPass.getText().toString().trim();

            Boolean checkError = true;
            if(userNameInput.isEmpty()){
                edUsername.setError("Nhập tên đăng nhập");
                checkError = false;
            }
            if(passwordInput.isEmpty()){
                edPass.setError("Nhập mật khẩu");
                checkError = false;
            }
            if(passwordInput.length()<6){
                edPass.setError("Mật khẩu ít nhất 6 kí tự");
                checkError = false;
            }
            if(checkError) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = ref.orderByChild("userName").equalTo(userNameInput);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String passwordFromDB = dataSnapshot.child(userNameInput).child("password").getValue(String.class);

                            if(passwordFromDB.equals(passwordInput)){
                                String fullNameFromDB = dataSnapshot.child(userNameInput).child("fullName").getValue(String.class);
                                String levelFromDB = dataSnapshot.child(userNameInput).child("level").getValue(String.class);
                                String userNameFromDB = dataSnapshot.child(userNameInput).child("userName").getValue(String.class);
                                String mail = dataSnapshot.child(userNameInput).child("email").getValue(String.class);
                                prefs.edit().putString(USERNAME, userNameFromDB).commit();
                                prefs.edit().putString(FULLNAME, fullNameFromDB).commit();
                                prefs.edit().putString(LEVEL, levelFromDB).commit();
                                prefs.edit().putString(EMAIL, mail).commit();
                                prefs.edit().putString("ADDRESS", dataSnapshot.child(userNameInput).child("docAdd").getValue(String.class));
                                prefs.edit().putString("INFO", dataSnapshot.child(userNameInput).child("docInfo").getValue(String.class));
                                prefs.edit().putBoolean("REMEMBERLOGIN", true).commit();
                                Intent dangNhapIntent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(dangNhapIntent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Toast.makeText(SignIn.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                            edUsername.setError("Sai tên đăng nhập");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


        });
    }
}