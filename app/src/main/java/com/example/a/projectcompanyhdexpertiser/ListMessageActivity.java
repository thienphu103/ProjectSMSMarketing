package com.example.a.projectcompanyhdexpertiser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a.projectcompanyhdexpertiser.Class.UserMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnect.LINK;

public class ListMessageActivity extends AppCompatActivity {
    public static final String TAG = "ListViewExample";
    private ListView listView;
    private Button btnChoose;
    private ArrayList<UserMessage> arrayList;
    ArrayAdapter<UserMessage> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_message);

        listView = (ListView) findViewById(R.id.listView);
        btnChoose = (Button) findViewById(R.id.button);
        getData();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printSelectedItems(view);
            }
        });




    }


    public void printSelectedItems(View view) {

        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb = new StringBuilder();
        int j=0;
        for (int i = 0; i < sp.size(); i++) {
            if (sp.valueAt(i) == true) {
                j++;
                UserMessage user = (UserMessage) listView.getItemAtPosition(i);
                // Or:
                // String s = ((CheckedTextView) listView.getChildAt(i)).getText().toString();
                String users = user.getUserName();
                String phone = user.getUserPhone();
                String message=user.getUserMessage();
                sendSMSMessage(phone,message);
                sb = sb.append(" " +"Tên: "+users+" - Phone: "+phone+"\n");
            }
        }
        Toast.makeText(this, " Gủi tin nhắn số lượng:"+j+"\n" + sb.toString(), Toast.LENGTH_LONG).show();

    }
    protected void sendSMSMessage(String phone,String message) {

//        txtphoneNo.getText().toString();
//        txtMessage.getText().toString();
        try {
            Toast.makeText(this, "Đang gủi tin nhắn số điện thoại:" + phone + "\n" + "Nội dung tin nhắn:" + message, Toast.LENGTH_SHORT).show();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this, "Thành Công....", Toast.LENGTH_SHORT).show();
        }catch (Exception ex) {
            Toast.makeText(this,  "SMS thất bại, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
        }


    }
    private void getData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(ListMessageActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("response", response.toString());
                        try {

                            arrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String name;
                            String content;
                            String phone;

                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                name = object.getString("name");
                                content = object.getString("content");
                                phone = object.getString("mobilePhone");

                                arrayList.add(new UserMessage(name, content, phone,false));

                                if (getApplicationContext() != null) {
                                    arrayAdapter= new ArrayAdapter<UserMessage>(ListMessageActivity.this, android.R.layout.simple_list_item_multiple_choice, arrayList);

                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listView.setAdapter(arrayAdapter);
                        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        for(int i=0;i< arrayList.size(); i++ )  {
                            listView.setItemChecked(i,arrayList.get(i).isActive());
                        }
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.i(TAG, "onItemClick: " + position);
                                CheckedTextView v = (CheckedTextView) view;
                                boolean currentCheck = v.isChecked();
                                UserMessage user = (UserMessage) listView.getItemAtPosition(position);
                                user.setActive(!currentCheck);
                            }
                        });



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "" + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("response", error.toString());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer 123456789");
                return headers;
            }
        };
        requestQueue.add(stringRequest);

    }
}

