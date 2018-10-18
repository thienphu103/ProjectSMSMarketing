package com.example.a.projectcompanyhdexpertiser.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.projectcompanyhdexpertiser.Controller.WriteAndReadFile;
import com.example.a.projectcompanyhdexpertiser.R;

import java.util.Timer;

public class LogActivity extends AppCompatActivity {

    TextView txt_logcat;
    private Timer myTimer;
    ImageButton btnDel;
    WriteAndReadFile writeAndReadFile=new WriteAndReadFile();

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
                writeAndReadFile.writeToFileOverride("log.txt", "", LogActivity.this);
                txt_logcat.setText(writeAndReadFile.readFromFile("log.txt", LogActivity.this));
            }
        });
    }

    private void initView() {
        final Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                txt_logcat.setText(writeAndReadFile.readFromFile("log.txt", LogActivity.this));
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
        btnDel = (ImageButton) findViewById(R.id.buttonRemove);

    }


}
