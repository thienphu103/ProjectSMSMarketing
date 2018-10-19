package com.example.a.projectcompanyhdexpertiser.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.projectcompanyhdexpertiser.Controller.FileStreamManager;
import com.example.a.projectcompanyhdexpertiser.Model.LogMessage;
import com.example.a.projectcompanyhdexpertiser.R;

import java.util.ArrayList;
import java.util.Timer;

public class LogActivity extends AppCompatActivity {

    TextView txt_logcat;
    private Timer myTimer;
    ImageButton btnDel;
    ScrollView scrollView;
    FileStreamManager writeAndReadFile = new FileStreamManager();
    private ListView listView;
    private ArrayList<LogMessage> arrayList;
    private ArrayAdapter<LogMessage> arrayAdapter;
    private TextView txt_status;
    SharedPreferences pre;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initModel();
        initView();
        initController();


    }

    private void initController() {

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(LogActivity.this, "Remove", Toast.LENGTH_SHORT).show();
                writeAndReadFile.writeToFileOverride("log.txt", "");
                txt_logcat.setText(writeAndReadFile.readFromFile("log.txt"));
            }
        });
    }

    private void initView() {
        writeAndReadFile.setContext(LogActivity.this);
        pre = getSharedPreferences("STATUS", Context.MODE_PRIVATE);
        editor = pre.edit();
        if (pre.getString("status", "").equals("Stand"))  {
            txt_status.setTextColor(Color.RED);
            txt_status.setText("Không hoạt động...");

        } else if (pre.getString("status", "").equals("Running")) {
            txt_status.setTextColor(Color.parseColor("#ff99cc00"));
            txt_status.setText("Đang hoạt động...");
        }
        final Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pre.getString("status", "").equals("Stand"))  {
                                    txt_status.setTextColor(Color.RED);
                                    txt_status.setText("Không hoạt động...");

                                } else if (pre.getString("status", "").equals("Running")) {
                                    txt_status.setTextColor(Color.parseColor("#ff99cc00"));
                                    txt_status.setText("Đang hoạt động...");
                                }
                                 txt_logcat.setText(writeAndReadFile.readFromFile("log.txt"));

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

    }

    private void initModel() {
        txt_logcat = findViewById(R.id.txt_log);
        txt_status=findViewById(R.id.status);
        btnDel = (ImageButton) findViewById(R.id.buttonRemove);



    }


}
