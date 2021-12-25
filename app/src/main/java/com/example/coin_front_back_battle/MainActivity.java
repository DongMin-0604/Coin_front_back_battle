package com.example.coin_front_back_battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    Button front_BT;
    Button go_main_BT,go_main_BT2;
    Button back_BT;

    TextView Batting_text,result_value_Text;

    ImageView change_img;

    EditText Value_ET;

    LinearLayout Batting_layout, touch_layout, result_layout;

    String front_text, back_text, front_or_back;
    String run = "게임중지";
    int coin_Key;

    Handler mHandler = null;

    Toast toast;

    InputMethodManager manager;

    int delay_key = 1;
    int delay = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setting_BT();
        coin_Anime();

    }

    //ID 세팅
    public void init() {
        front_BT = findViewById(R.id.front_BT);
        go_main_BT = findViewById(R.id.go_main_BT);
        back_BT = findViewById(R.id.back_BT);
        go_main_BT2 = findViewById(R.id.go_main_BT2);

        Value_ET = findViewById(R.id.Value_ET);

        change_img = findViewById(R.id.change_img);

        Batting_text = findViewById(R.id.Batting_Text);
        result_value_Text = findViewById(R.id.result_value_Text);

        Batting_layout = findViewById(R.id.Batting_layout);
        touch_layout = findViewById(R.id.touch_layout);
        result_layout = findViewById(R.id.result_layout);

        touch_layout.setOnTouchListener(this);

        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        Random random = new Random();
        coin_Key = random.nextInt(3000) + 1;
    }

    //버튼 이벤트
    public void setting_BT() {
        front_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                front_BT.setEnabled(false);
                back_BT.setEnabled(false);

                if (TextUtils.isEmpty(front_text)) {
                    //이전 조건이 없을 시
                    Batting_layout.setVisibility(View.VISIBLE);
                    Batting_text.setText("앞");
                } else {
                    //이전 조건값 Edittext에 남기기
                    Value_ET.setText(front_text);
                    Batting_layout.setVisibility(View.VISIBLE);
                    Batting_text.setText("앞");
                }
            }
        });
        back_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_BT.setEnabled(false);
                back_BT.setEnabled(false);

                if (TextUtils.isEmpty(back_text)) {
                    //이전 조건이 없을 시
                    Batting_layout.setVisibility(View.VISIBLE);
                    Batting_text.setText("뒤");
                } else {
                    //이전 조건값 Edittext에 남기기
                    Value_ET.setText(back_text);
                    Batting_layout.setVisibility(View.VISIBLE);
                    Batting_text.setText("뒤");
                }
            }
        });
        go_main_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_BT.setEnabled(true);
                back_BT.setEnabled(true);
                manager.hideSoftInputFromWindow(Value_ET.getWindowToken(), 0);
                if (Batting_text.getText().toString().equals("앞")) {
                        front_text = Value_ET.getText().toString();
                        Value_ET.setText("");
                        Batting_layout.setVisibility(View.GONE);
                        Log.d("1", "앞:" + front_text);


                } else if (Batting_text.getText().toString().equals("뒤")) {
                        back_text = Value_ET.getText().toString();
                        Value_ET.setText("");
                        Batting_layout.setVisibility(View.GONE);
                        Log.d("1", "뒤:" + back_text);

                }
            }
        });
        go_main_BT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front_BT.setEnabled(true);
                back_BT.setEnabled(true);
                result_layout.setVisibility(View.GONE);
            }
        });
    }


    public void coin_Anime() {
        delay = 100;
        delay_key = 1;
        //동전 애니메이션
        mHandler = new Handler();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (run != "게임중지") {
                    front_BT.setEnabled(false);
                    back_BT.setEnabled(false);

                    coin_Key++;
                    coin_Key = coin_Key % 2;
                    Log.d("1", "키:" + coin_Key);
                    delay_key++;
                    img_play(coin_Key);

                    switch (delay_key) {
                        case 15:
                            delay = 150;
                            break;
                        case 20:
                            delay = 250;
                            break;
                        case 25:
                            delay = 300;
                            break;
                        case 35:
                            delay = 400;
                            break;
                        case 37:
                            delay = 450;
                            break;
                        case 39:
                            delay = 500;
                            break;
                        case 40:
                            result();
                            break;
                    }
                }
                mHandler.sendEmptyMessageDelayed(0, delay);
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    private void result() {
        mHandler = new Handler();
        run = "게임중지";
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                front_BT.setEnabled(false);
                back_BT.setEnabled(false);
                if (front_or_back == "앞"){
                    result_value_Text.setText(front_text);

                }else if (front_or_back == "뒤"){
                    result_value_Text.setText(back_text);
                }
                result_layout.setVisibility(View.VISIBLE);
            }
        },1000);//4500

    }

    private void img_play(int coin_Key) {
        mHandler.removeMessages(0);
        Log.d("1", "img_play");
        switch (coin_Key) {
            case 0:
                change_img.setImageResource(R.drawable.coin_front);
                front_or_back = "앞";
                break;
            case 1:
                change_img.setImageResource(R.drawable.coin_back);
                front_or_back = "뒤";
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == event.ACTION_UP) {
            if (TextUtils.isEmpty(front_text) || TextUtils.isEmpty(back_text)){
                toast.makeText(getApplicationContext(), "조건이 비어있습니다.", Toast.LENGTH_SHORT).show();
            }else {
                switch (v.getId()) {
                    case R.id.touch_layout:
                        run = "게임시작";
                        Log.d("1", "손가락 뗌"+run);
                        coin_Anime();
                        break;
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.getLooper().quit();
    }
}
