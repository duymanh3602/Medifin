package com.btl.medifin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.btl.medifin.R;
import com.btl.medifin.adapter.IntroAdapter;
import com.btl.medifin.model.IntroItem;

import java.util.ArrayList;
import java.util.List;

public class Intro extends AppCompatActivity {
    private IntroAdapter introAdapter;
    private LinearLayout circleLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        ViewPager2 introViewpager = findViewById(R.id.intro_viewpager);
        circleLayout = findViewById(R.id.intro_circleLayout);
        setupItem();
        introViewpager.setAdapter(introAdapter);
        setupCircle();
        setActiveCircle(0);
        introViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setActiveCircle(position);
            }
        });

        findViewById(R.id.intro_btnDangNhap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro.this, SignIn.class));
                finish();
            }
        });
    }

    private void setupItem(){
        List<IntroItem> introItems = new ArrayList<>();

        IntroItem item1 = new IntroItem();
        item1.setImageView(R.drawable.logo);
        item1.setTextView("MediFin là ứng dụng đặt lịch khám giúp người bệnh có thể giúp người bệnh giảm thiểu thời gian chờ đợi mỗi lần khám, cung cấp những thông tin cần thiết về bác sĩ và các loại thuốc.");
        /*IntroItem item2 = new IntroItem();
        item2.setImageView(R.drawable.intro1);
        item2.setTextView("Những bác sĩ với trình độ cao sẽ được MediFin mời về hệ thống để trực tiếp khám cho bệnh nhân một cách tốt nhất.");
        IntroItem item3 = new IntroItem();
        item3.setImageView(R.drawable.intro);
        item3.setTextView("Lưu trữ, quản lý và tra cứu thông tin các lần khám chữa bệnh");
*/
        introItems.add(item1);
/*        introItems.add(item2);
        introItems.add(item3);*/
        introAdapter = new IntroAdapter(introItems);
    }

    private void setupCircle(){
        ImageView [] circles = new ImageView[introAdapter.getItemCount()];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        for(int i = 0; i < circles.length; i++){
            circles[i] = new ImageView(getApplicationContext());
            circles[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.circle_inactive
            ));
            circles[i].setLayoutParams(params);
            circleLayout.addView(circles[i]);
        }
    }

    private void setActiveCircle(int index){
        int childCount = circleLayout.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView circleView = (ImageView) circleLayout.getChildAt(i);
            if(i == index){
                circleView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_active));
            } else {
                circleView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_inactive));
            }
        }
    }
}