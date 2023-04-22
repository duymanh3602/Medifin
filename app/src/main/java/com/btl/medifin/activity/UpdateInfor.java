package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.btl.medifin.MainActivity;
import com.btl.medifin.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Year;
import java.util.Calendar;
import java.util.regex.Pattern;

public class UpdateInfor extends AppCompatActivity {
    private TextInputLayout tilUsername, tilHoTen, tilMail, tilBirthday, tilAge, tilPhone;
    private SharedPreferences prefs;
    private DatabaseReference reference;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private ImageView back;

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
        setContentView(R.layout.activity_update_infor);
        getSupportActionBar().hide();
        mappingView();
        prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
        String getUsername = prefs.getString(USERNAME, "");
        reference = FirebaseDatabase.getInstance().getReference("users").child(getUsername);
        showAllUserData(getUsername);
        updateInfor();
        findViewById(R.id.btn_Cancel).setOnClickListener(v -> {
            finish();
        });
        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void showAllUserData(String username) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ageFromDB = dataSnapshot.child("age").getValue(String.class);
                String birthdayFromDB = dataSnapshot.child("birthday").getValue(String.class);
                String emailFromDB = dataSnapshot.child("email").getValue(String.class);
                String fullNameFromDB = dataSnapshot.child("fullName").getValue(String.class);
                String phoneFromDB = dataSnapshot.child("phone").getValue(String.class);

                tilAge.getEditText().setText(ageFromDB);
                tilBirthday.getEditText().setText(birthdayFromDB);
                tilMail.getEditText().setText(emailFromDB);
                tilHoTen.getEditText().setText(fullNameFromDB);
                tilPhone.getEditText().setText(phoneFromDB);
                tilUsername.getEditText().setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateInfor() {
        findViewById(R.id.btn_update).setOnClickListener(v -> {
            Boolean checkError = true;
            if(tilHoTen.getEditText().getText().toString().trim().isEmpty()){
                tilHoTen.setError("Tên không được để trống");
                checkError = false;
            }

            if(tilMail.getEditText().getText().toString().trim().isEmpty()){
                tilMail.setError("Email không được để trống");
                checkError = false;
            }

            if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", tilMail.getEditText().getText().toString().trim())){
                tilMail.setError("Email sai định dạng");
                checkError = false;
            }

            if(checkError){
                reference.child("age").setValue(calculateAge(tilBirthday.getEditText().getText().toString().trim()));
                reference.child("birthday").setValue(tilBirthday.getEditText().getText().toString().trim());
                reference.child("email").setValue(tilMail.getEditText().getText().toString().trim());
                reference.child("fullName").setValue(tilHoTen.getEditText().getText().toString().trim());
                reference.child("phone").setValue(tilPhone.getEditText().getText().toString().trim());
                prefs.edit().putString(FULLNAME, tilHoTen.getEditText().getText().toString().trim()).commit();
                startActivity(new Intent(UpdateInfor.this, MainActivity.class));
                finish();
            }
        });
    }

    private void mappingView() {
        tilUsername = findViewById(R.id.til_username_capNhatActivity);
        tilHoTen = findViewById(R.id.til_hoTen_capNhatActivity);
        tilMail = findViewById(R.id.til_mail_capNhatActivity);
        tilBirthday = findViewById(R.id.til_birtday_capNhatActivity);
        tilAge = findViewById(R.id.til_age_capNhatActivity);
        tilPhone = findViewById(R.id.til_phone_capNhatActivity);
        back = findViewById(R.id.backUpdateInfor);
    }

    private String calculateAge(String birthDay) {
        String y = birthDay.substring(birthDay.length()-4, birthDay.length());
        int year = 0;
        for (int i = 0; i < 4; i++) {
            year = year * 10 + (int)(y.charAt(i) - 48);
        }

        int age = Calendar.getInstance().get(Calendar.YEAR) - year;

        return Integer.toString(age);
    }

    private void chooseDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                onDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}