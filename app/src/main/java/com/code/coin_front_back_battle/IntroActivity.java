package com.code.coin_front_back_battle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coin_front_back_battle.R;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        Handler handler = new Handler();
        //코드 딜레이 주는 구문
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, 1500);//1.5초뒤 run() 코드 실행
    }

    //앱이 멈추거나 백 그라운드로 들어갈 시 엑티비티를 종료함으로써 다시 돌아왔을 때 인트로 화면 다시 띄우기
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
