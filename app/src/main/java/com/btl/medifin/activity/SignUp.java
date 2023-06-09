package com.btl.medifin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.medifin.R;
import com.btl.medifin.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private FirebaseDatabase database;
    DatabaseReference ref;
    Button btnDangKi, btnHuyBo;
    TextView cancel;
    ImageView back;
    EditText etUserName, etPassword, etRePassword, etEmail;
    private Spinner gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mappingView();
        //setupSpinner();
        signUp();
        cancel.setOnClickListener(v -> {
            finish();
        });
        back.setOnClickListener(v -> {
            finish();
        });

    }

//    private void setupSpinner() {
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("Nam");
//        arrayList.add("Nữ");
//        arrayList.add("Không tiết lộ");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
//        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
//        gender.setGravity(10);
//        gender.setAdapter(arrayAdapter);
//    }

    private void signUp() {
        btnDangKi.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();
            String rePass = etRePassword.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            Boolean checkError = true;

            if(userName.isEmpty()){
                etUserName.setError("Tên đăng nhập không được bỏ trống");
                checkError = false;
            }
            if(rePass.isEmpty()){
                etRePassword.setError("Nhập lại mật khẩu không được bỏ trống");
                checkError = false;
            }
            if(pass.length()<6){
                etPassword.setError("Mật khẩu phải từ 6 kí tự");
                checkError = false;
            }
            if(!rePass.equals(pass)){
                etRePassword.setError("Nhập lại mật khẩu không đúng");
                checkError = false;
            }
            if(!Pattern.matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$", email)){
                etEmail.setError("Mail sai định dạng");
                checkError = false;
            }
            if(checkError){
                Users user = new Users();
                user.setUserName(userName);
                user.setPassword(pass);
                user.setEmail(email);
                user.setLevel("Bệnh Nhân");
                user.setAge("");
                user.setBirthday("");
                user.setPhone("");
                user.setFullName("");
                user.setSpecialized("Người dùng");
                user.setDoctorAdd("null");
                user.setDoctorInfo("null");

                database = FirebaseDatabase.getInstance();
                ref = database.getReference("users");
                ref.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            Toast.makeText(SignUp.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            ref.child(userName).setValue(user);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });
    }

    private void mappingView() {
        btnDangKi = findViewById(R.id.dangKi_btnDangKi);
        cancel = findViewById(R.id.btn_Cancel);
        back = findViewById(R.id.back);
        etUserName = findViewById(R.id.dangKi_edUserName);
        etPassword = findViewById(R.id.dangKi_etPassword);
        etRePassword = findViewById(R.id.dangKi_etRePassword);
        etEmail = findViewById(R.id.dangKi_etEmail);
        //gender = findViewById(R.id.gender);
    }
}