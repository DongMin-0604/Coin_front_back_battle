package com.code.coin_front_back_battle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coin_front_back_battle.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    //뒤로가기 두번 누른 시간 차 측정을 위한 변수
    private long backKeyPressedTime;

    Button front_BT;
    Button go_main_BT, go_main_BT2;
    Button back_BT;

    TextView Batting_text, result_value_Text, TOUCH_TV;

    ImageView change_img, result_IV;

    EditText Value_ET;

    LinearLayout Batting_layout, touch_layout, result_layout, Batting_layout_anime, result_layout_anime;

    String front_text, back_text, front_or_back;
    String run = "게임중지";
    int coin_Key;

    Handler mHandler = null;

    Toast toast;
    //핸드폰 자판 표시 여부 결정해주는 매니저
    InputMethodManager manager;
    //애니메이션 지정
    Animation animation_Test;

    int delay_key = 1;
    int delay = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //기본 설정을 담은 함수
        init();
        //버튼 onClick 시 효과만 담은 함수
        setting_BT();
        //동전 애니메이션을 담은 함수
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
        result_IV = findViewById(R.id.result_IV);

        TOUCH_TV = findViewById(R.id.TOUCH_TV);

        Batting_text = findViewById(R.id.Batting_Text);
        result_value_Text = findViewById(R.id.result_value_Text);

        Batting_layout = findViewById(R.id.Batting_layout);
        touch_layout = findViewById(R.id.touch_layout);
        result_layout = findViewById(R.id.result_layout);
        Batting_layout_anime = findViewById(R.id.Batting_layout_anime);
        result_layout_anime = findViewById(R.id.result_layout_anime);

        touch_layout.setOnTouchListener(this);

        manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        animation_Test = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //동전 앞 뒤 확률을 랜덤하게 하기 위한 랜덤 함수 호출
        Random random = new Random();
        coin_Key = random.nextInt(3000) + 1;
    }

    //버튼 이벤트
    public void setting_BT() {
        front_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button_Style_no_click();

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
                Button_Style_no_click();

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

                Button_Style_default();
                //키보드 자판 집어넣기
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
                Button_Style_default();
                result_layout.setVisibility(View.GONE);
            }
        });
    }


    public void coin_Anime() {
        //동전 애니메이션 함수
        delay = 100;
        delay_key = 1;


        mHandler = new Handler();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (run != "게임중지") {
                    Button_Style_no_click();

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
                            delay = 470;
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
        //결과창 출력하는 함수
        mHandler = new Handler();
        run = "게임중지";
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (front_or_back == "앞") {
                    result_value_Text.setText(front_text);
                    result_IV.setImageResource(R.drawable.coin_front);

                } else if (front_or_back == "뒤") {
                    result_value_Text.setText(back_text);
                    result_IV.setImageResource(R.drawable.coin_back);
                }


                Button_Style_no_click();

                TOUCH_TV.setText("TOUCH!");
                front_text = "";
                back_text = "";

                result_layout.setVisibility(View.VISIBLE);
            }
        }, 800);//0.8초

    }

    private void img_play(int coin_Key) {
        //이미지를 coni_key에 따라 UI에서 실제로 변경해주는 함수
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
        //화면 터치 시 이벤트
        int action = event.getAction();
        if (action == event.ACTION_UP) {

            if (Batting_layout.getVisibility() == View.VISIBLE || result_layout.getVisibility() == View.VISIBLE) {

            } else if (TextUtils.isEmpty(front_text) || TextUtils.isEmpty(back_text)) {
                toast.makeText(getApplicationContext(), "조건이 비어있습니다.", Toast.LENGTH_SHORT).show();

            } else {
                switch (v.getId()) {
                    case R.id.touch_layout:
                        run = "게임시작";
                        Log.d("1", "손가락 뗌" + run);
                        coin_Anime();
                        TOUCH_TV.setText("선택 진행중");
                        break;
                }
            }
        }
        return true;
    }

    public void Button_Style_default() {
        //버튼 활성화 함수
        front_BT.setEnabled(true);
        back_BT.setEnabled(true);
        front_BT.setBackgroundResource(R.drawable.button_custom);
        back_BT.setBackgroundResource(R.drawable.button_custom);
    }

    public void Button_Style_no_click() {
        //버튼 비활성화 시키는 함수
        front_BT.setEnabled(false);
        back_BT.setEnabled(false);
        front_BT.setBackgroundResource(R.drawable.button_custom_dont_click);
        back_BT.setBackgroundResource(R.drawable.button_custom_dont_click);
    }

    @Override
    public void onBackPressed() {
        //뒤로 버튼 두 번 클릭해야 종료되는 로직
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            moveTaskToBack(true);
            finish();
            super.onBackPressed();
            toast.cancel();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            // 핸들러 종료
            mHandler.removeMessages(0);
        }

    }
}
