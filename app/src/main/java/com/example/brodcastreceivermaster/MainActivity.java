package com.example.brodcastreceivermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
*브로드 캐스트 리시버(Brodcase Receiver, 방송 수신자)
-브로드 캐스팅 : 메시지를 여러 객체에 전달함
-글로벌 이벤트 : 단말 전체에 메시지를 전달함

-앱 구성 요소이므로 매니페스트 파일에 등록해야 시스템이 알 수 있는데,
매니페스트 등록 방식이 아닌 소스 코드에서 registerReceiver() 메소드로 시스템에 등록할 수 있음
-> 소스 코드로 등록하면 액티비티 안에서 브로드 캐스트 메시지를 전달 받아 바로 다른 작업을 수행하도록 만들 수 있는 장점이 있음
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}