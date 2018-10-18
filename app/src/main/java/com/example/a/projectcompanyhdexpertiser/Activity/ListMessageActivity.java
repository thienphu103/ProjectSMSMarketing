package com.example.a.projectcompanyhdexpertiser.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a.projectcompanyhdexpertiser.Controller.MessageManager;
import com.example.a.projectcompanyhdexpertiser.Controller.SMSMailer;
import com.example.a.projectcompanyhdexpertiser.Controller.WriteAndReadFile;
import com.example.a.projectcompanyhdexpertiser.Model.UserMessage;
import com.example.a.projectcompanyhdexpertiser.R;
import com.example.a.projectcompanyhdexpertiser.Service.SMSService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ListMessageActivity extends AppCompatActivity {
    public static final String TAG = "ListViewExample";
    private ListView listView;
    private Button btnSend;
    private ImageButton btnReset;
    private ArrayList<UserMessage> arrayList;
    ArrayAdapter<UserMessage> arrayAdapter;
    MessageManager messageManager = new MessageManager();
    SMSMailer smsMailer = new SMSMailer();
    SharedPreferences pre;
    SharedPreferences.Editor editor;
    Timer myTimer;
    private String MILLISECOND_TIME;
    private String STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);
        initModel();
        initView();
        initController();

    }

    private void initController() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ListMessageActivity.this);
        MILLISECOND_TIME = prefs.getString("pref_edittext", "");
        pre = getSharedPreferences("STATUS", Context.MODE_PRIVATE);
        editor = pre.edit();

        if (pre.getString("status", "").equals("Stand"))  {
            btnSend.setBackgroundColor(Color.parseColor("#ff33b5e5"));
            btnSend.setText("ACTION");
        } else if (pre.getString("status", "").equals("Running")) {
            btnSend.setBackgroundColor(Color.RED);
            btnSend.setText("STOP");
        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pre = getSharedPreferences("STATUS", Context.MODE_PRIVATE);
                editor = pre.edit();
                if (pre.getString("status", "").equals("Stand")) {
                    AlertDialogChoose();
                } else if (pre.getString("status", "").equals("Running")) {
                    AlertDialogClose();
                }else {
                    AlertDialogChoose();
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ListMessageActivity.this, "Reset dữ liệu...", Toast.LENGTH_SHORT).show();
                messageManager.getData();

            }
        });

    }

    private void initView() {
        messageManager.setContext(ListMessageActivity.this);
        messageManager.setListView(listView);
        messageManager.getData();

    }


    private void initModel() {
        listView = (ListView) findViewById(R.id.listView);
        btnSend = (Button) findViewById(R.id.buttonSend);
        btnReset = (ImageButton) findViewById(R.id.buttonReset);

    }


    public void SendAllSMSMessage() {
        arrayList = messageManager.arrayList;
        final StringBuilder[] sb = {new StringBuilder()};
        final String[] id = new String[1];
        final String[] users = new String[1];
        final String[] phone = new String[1];
        final String[] message = new String[1];
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Log", MODE_PRIVATE);
        for (int i = 0; i < arrayList.size(); i++) {
            UserMessage user = (UserMessage) listView.getItemAtPosition(i);
            id[0] = user.getId();
            users[0] = user.getUserName();
            phone[0] = user.getUserPhone();
            message[0] = user.getUserMessage();
            // smsMailer.sendSMS(getApplicationContext(), phone[0], message[0]);
            messageManager.updateData(id[0]);
            sb[0] = sb[0].append("\n " + "ID: " + id[0] + "\nTên: " + users[0] + " - Phone: " + phone[0] + "\n------------\n");
            Log.d("Process", DateFormat.getDateTimeInstance().format(new Date()) + ": SMS Send Activity");
            WriteAndReadFile writeAndReadFile = new WriteAndReadFile();
            writeAndReadFile.writeToFile("log.txt", "\n" + DateFormat.getDateTimeInstance().format(new Date()) + ": SMS Send Activity\n", ListMessageActivity.this);


        }

    }

    public void AlertDialogChoose() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ListMessageActivity.this);
        builder.setTitle("Gủi Tin Nhắn Hàng Loạt với " + MILLISECOND_TIME + "s ?\n" + "Trạng Thái: " + pre.getString("status", ""));
        builder.setMessage("Bạn có chắc không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Chạy Bình Thường", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btnSend.setBackgroundColor(Color.RED);
                btnSend.setText("STOP");
                editor.clear();
                editor.putString("status", "Running");
                editor.commit();
                myTimer = new Timer();
                myTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        SendAllSMSMessage();
                    }
                }, 0, Integer.parseInt(MILLISECOND_TIME));
                messageManager.getData();
            }
        });
        builder.setNegativeButton("Chạy Nền", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btnSend.setBackgroundColor(Color.RED);
                btnSend.setText("STOP");
                editor.clear();
                editor.putString("status", "Running");
                editor.commit();
                startService(new Intent(getApplicationContext(), SMSService.class));

            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void AlertDialogClose() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ListMessageActivity.this);
        builder.setTitle("Dừng Tin Nhắn Hàng Loạt với " + MILLISECOND_TIME + "s ?\n" + "Trạng Thái: " + pre.getString("status", ""));
        builder.setMessage("Bạn có chắc không?");
        builder.setCancelable(false);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.setNeutralButton("Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btnSend.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                btnSend.setText("ACTION");
                editor.clear();
                editor.putString("status", "Stand");
                editor.commit();
                if (!(myTimer == null)) {
                    myTimer.cancel();
                }
                stopService(new Intent(getApplicationContext(), SMSService.class));
                dialogInterface.dismiss();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


