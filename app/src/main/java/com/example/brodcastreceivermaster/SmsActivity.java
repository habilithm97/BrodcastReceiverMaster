package com.example.brodcastreceivermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    TextView titleTv, contentTv, timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        titleTv = (TextView)findViewById(R.id.titleTv);
        contentTv = (TextView)findViewById(R.id.contentTv);
        timeTv = (TextView)findViewById(R.id.timeTv);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 전달 받은 인텐트 처리하기
        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            String sender = intent.getStringExtra("sender");
            String contents = intent.getStringExtra("contents");
            String date = intent.getStringExtra("date");

            titleTv.setText("발신 번호 : " + sender);
            contentTv.setText("내용 : " + contents);
            timeTv.setText("발신 시각 :  " + date);
        }
    }
}