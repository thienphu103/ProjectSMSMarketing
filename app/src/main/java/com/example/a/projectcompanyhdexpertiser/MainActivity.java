package com.example.a.projectcompanyhdexpertiser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    Button sendBtn;
    EditText txtphoneNo;
    EditText txtMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphoneNo = (EditText) findViewById(R.id.edtPhone);
        txtMessage = (EditText) findViewById(R.id.edtMessage);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMSMessage();
                //testSMS();
            }
        });
    }

    protected void sendSMSMessage() {

//        txtphoneNo.getText().toString();
//        txtMessage.getText().toString();
        try {
            Toast.makeText(this, "Đang gủi tin nhắn số điện thoại:" + txtphoneNo.getText().toString() + "\n" + "Nội dung tin nhắn:" + txtMessage.getText().toString(), Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(txtphoneNo.getText().toString(), null, txtMessage.getText().toString(), null, null);
            Toast.makeText(this, "Thành Công....", Toast.LENGTH_SHORT).show();
        }catch (Exception ex) {
            Toast.makeText(this,  "SMS thất bại, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }


    }


}



