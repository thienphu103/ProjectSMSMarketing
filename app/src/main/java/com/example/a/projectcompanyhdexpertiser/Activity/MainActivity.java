package com.example.a.projectcompanyhdexpertiser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a.projectcompanyhdexpertiser.R;

public class MainActivity extends AppCompatActivity {
Button btnListMessage, btnSetting, btnLog;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initModel();
        initController();
        
    }

    private void initController() {
        btnListMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),ListMessageActivity.class);
                startActivity(intent);
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getApplicationContext(),LogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initModel() {
        btnListMessage=findViewById(R.id.btnListMessage);
        btnSetting=findViewById(R.id.btnSetting);
        btnLog=findViewById(R.id.btnLog);
    }
    
}
