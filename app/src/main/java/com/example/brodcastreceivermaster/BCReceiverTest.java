package com.example.brodcastreceivermaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.sql.Date;

public class BCReceiverTest extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) { // 원하는 브로드 캐스트 메시지가 도착하면 자동으로 호출됨, 인텐트에는 SMS 데이터가 들어가있음(Telepony 모듈에서 넣어줌)

        Bundle bundle = intent.getExtras(); // 인텐트에서 번들 객체 가져오기
        SmsMessage[] messages = parseSmsMessage(bundle); // SMS 메시지 객체 생성

        if(messages != null && messages.length > 0) {
            String num  = messages[0].getOriginatingAddress(); // 발신번호
            String content = messages[0].getMessageBody(); // 메시지 내용
            Date time = new Date(messages[0].getTimestampMillis()); //  발신시각
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] obj = (Object[]) bundle.get("pdus"); // pdus에는 SMS 데이터와 관련된 내용들이 들어가있음
        SmsMessage[] messages = new SmsMessage[obj.length]; // 번들 객체에 들어가 있는 부가 데이터 중에서 pdus 가져오기
        return messages;
    }
}
