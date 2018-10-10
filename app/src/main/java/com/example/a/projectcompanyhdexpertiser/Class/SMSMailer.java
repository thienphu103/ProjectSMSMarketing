package com.example.a.projectcompanyhdexpertiser.Class;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SMSMailer  {
    public void sendSMS(final Context context, String phone, String message) {

        try {
            Toast.makeText(context, "Đang gủi tin nhắn số điện thoại:" + phone + "\n" + "Nội dung tin nhắn:" + message, Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(context, "Thành Công....", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context, "SMS thất bại, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }


    }
}
