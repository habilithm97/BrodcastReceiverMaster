package com.example.brodcastreceivermaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
*브로드 캐스트 리시버(Brodcase Receiver, 방송 수신자)
-브로드 캐스팅 : 메시지를 여러 객체에 전달함
-글로벌 이벤트 : 단말 전체에 메시지를 전달함

-앱 구성 요소이므로 매니페스트 파일에 등록해야 시스템이 알 수 있는데,
매니페스트 등록 방식이 아닌 소스 코드에서 registerReceiver() 메소드로 시스템에 등록할 수 있음
-> 소스 코드로 등록하면 액티비티 안에서 브로드 캐스트 메시지를 전달 받아 바로 다른 작업을 수행하도록 만들 수 있는 장점이 있음
-> 매니페스트에 등록시키면 배터리를 빠르게 소진시킴

-브로드 캐스트 메시지가 도착하면 호출되는 콜백 메소드인 onReceive()를 정의해야함

-모든 메시지는 인텐트 안에 넣어 전달되므로 메시지는 매니페스트 파일의 인텐트 필터를 사용해서 시스템에 등록하면됨
 */

public class SmsReceiver extends BroadcastReceiver {

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TAG = "SmsReceiver";

    @Override // SMS를 받으면 자동 호출됨
    public void onReceive(Context context, Intent intent) { // SMS 데이터가 들어가있음 -> 시스템이 Telephony 모듈에서 넣어줌
        Log.d(TAG, "onReceive() 호출됨 ");

        Bundle bundle = intent.getExtras(); // 인텐트에서 번들 객체(Sms 데이터가 들어가 있음) 가져오기
        SmsMessage[] messages = parseMessage(bundle); // SMS 메시지 객체 생성

        if(messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress(); // 발신번호
            Log.i(TAG, "SMS 발신번호 : " + sender);

            String contents = messages[0].getMessageBody(); // 메시지 내용
            Log.i(TAG, "SMS 내용 : " + contents);

            Date date = new Date(messages[0].getTimestampMillis()); // 발신시각
            Log.i(TAG, "SMS 발신시각 : " + date);

            sendToActivity(context, sender, contents, date);
        }
    }

    private void sendToActivity(Context context, String sender, String contents, Date date) {
        Intent intent = new Intent(context, SmsActivity.class); // Sms가 오면 화면 전환함
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("date", dateFormat.format(date)); // 날짜값은 문자열 형식대로 변환

        context.startActivity(intent); // context를 붙히면 방송 수신자에서 사용 가능
    }

    private SmsMessage[] parseMessage(Bundle bundle) { // Sms 데이터를 확인할 수 있는 Sms 메시지 객체 생성(이 메서드는 다른 앱에서도 재사용 쌉가능)
        // 번들 객체에 들어가 있는 부가 데이터 중에서 pdus 가져오기
        Object[] objects = (Object[]) bundle.get("pdus"); // pdus에는 SMS 데이터와 관련된 내용들이 들어가 있음
        SmsMessage[] messages = new SmsMessage[objects.length];

        int smsCount = objects.length;
        for(int i = 0; i < smsCount; i++) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            }
        }
        return messages;
    }
}
