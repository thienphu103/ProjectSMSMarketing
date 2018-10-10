package com.example.a.projectcompanyhdexpertiser.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a.projectcompanyhdexpertiser.Class.MessageManager;
import com.example.a.projectcompanyhdexpertiser.Class.SMSMailer;
import com.example.a.projectcompanyhdexpertiser.Model.UserMessage;
import com.example.a.projectcompanyhdexpertiser.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ListViewExample";
    private ListView listView;
    private Button btnSend;
    private ImageButton btnReset;
    private ArrayList<UserMessage> arrayList;
    ArrayAdapter<UserMessage> arrayAdapter;
    MessageManager messageManager = new MessageManager();
    SMSMailer smsMailer=new SMSMailer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);
        initModel();
        initView();
        initController();

    }

    private void initController() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAllSMSMessage(view);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reset dữ liệu...", Toast.LENGTH_SHORT).show();
                messageManager.getData(getApplicationContext(), listView);
                messageManager.arrayAdapter.notifyDataSetChanged();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);

            }
        });

    }

    private void initView() {
        messageManager.getData(getApplicationContext(), listView);

    }


    private void initModel() {
        listView = (ListView) findViewById(R.id.listView);
        btnSend = (Button) findViewById(R.id.buttonSend);
        btnReset = (ImageButton) findViewById(R.id.buttonReset);
    }


    public void SendAllSMSMessage(View view) {
        arrayList=messageManager.arrayList;
        final StringBuilder[] sb = {new StringBuilder()};
        final String[] id = new String[1];
        final String[] users = new String[1];
        final String[] phone = new String[1];
        final String[] message = new String[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Gủi Tin Nhắn Hàng Loạt ?");
        builder.setMessage("Bạn có chắc không?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (i = 0; i < arrayList.size(); i++) {
                    UserMessage user = (UserMessage) listView.getItemAtPosition(i);
                    id[0] = user.getId();
                    users[0] = user.getUserName();
                    phone[0] = user.getUserPhone();
                    message[0] = user.getUserMessage();
                    smsMailer.sendSMS(getApplicationContext(),phone[0], message[0]);
                    messageManager.updateData(getApplicationContext(), id[0]);
                    sb[0] = sb[0].append("\n " + "ID: " + id[0] + "\nTên: " + users[0] + " - Phone: " + phone[0] + "\n------------\n");
                }
                new AlertDialog.Builder(MainActivity.this).setTitle("Thành Công").setMessage(" Gủi tin nhắn số lượng:" + i + "\n" + sb[0].toString()).
                        setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        overridePendingTransition(0, 0);
                                    }
                                }

                        ).show();
                messageManager.getData(getApplicationContext(), listView);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}


