package com.example.a.projectcompanyhdexpertiser.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a.projectcompanyhdexpertiser.Controller.WriteAndReadFile;
import com.example.a.projectcompanyhdexpertiser.Model.UserMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnection.HOST;
import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnection.PATH;

public class SMSService extends Service {

    private ArrayList<UserMessage> arrayList;
    WriteAndReadFile writeAndReadFile;
    private Timer timer = new Timer();
    private String MILLISECOND_TIME;


    public SMSService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initModel();
        initEvent();
    }

    private void initModel() {

    }

    private void initEvent() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SMSService.this);
        MILLISECOND_TIME = prefs.getString("pref_edittext", "");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        }, 0, Integer.parseInt(MILLISECOND_TIME));//5 Minutes
    }

    public void getData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(SMSService.this);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, HOST + PATH + "message",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        arrayList = new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = null;
                            String id;
                            String name;
                            String content;
                            String phone;
                            String status;
                            boolean inactive;
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);
                                id = object.getString("id");
                                name = object.getString("name");
                                content = object.getString("content");
                                phone = object.getString("mobilePhone");
                                inactive = object.getBoolean("inactive");
                                if (inactive == true) {
                                    status = "Đã Gủi";
                                } else {
                                    status = "Chưa Gủi";
                                }
                                arrayList.add(new UserMessage(id, name, content, phone, false, status));
                            }
                            final StringBuilder[] sb = {new StringBuilder()};
                            String id1;
                            String users1;
                            String phone1;
                            String message1;

                            for (int i = 0; i < arrayList.size(); i++) {

                                id1 = arrayList.get(i).getId();
                                users1 = arrayList.get(i).getUserName();
                                phone1 = arrayList.get(i).getUserPhone();
                                message1 = arrayList.get(i).getUserMessage();
                                // smsMailer.sendSMS(getApplicationContext(), phone[0], message[0]);
                                updateData(id1);
                                sb[0] = sb[0].append("\n " + "ID: " + id1 + "\nTên: " + users1 + " - Phone: " + phone1 + "\n------------\n");
                                if (i == (arrayList.size() - 1)) {
                                    Log.d("Process", DateFormat.getDateTimeInstance().format(new Date()) + ": SMS Send Service");
                                    writeAndReadFile = new WriteAndReadFile();
                                    writeAndReadFile.writeToFile("log.txt", "\n" + DateFormat.getDateTimeInstance().format(new Date()) + ": SMS Send Service\n", SMSService.this);
                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(context, "Server Timeout, Xui lòng thử lại!", Toast.LENGTH_SHORT).show();
                Log.d("response", error.toString());
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer 123456789");
                return headers;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void updateData(final String id) {


        RequestQueue requestQueue = Volley.newRequestQueue(SMSService.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HOST + PATH + "update?uuid=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Server: " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(SMSService.this, "Server Error: " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer 123456789");
                return headers;
            }
        };
        requestQueue.add(stringRequest);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
        timer.cancel();

    }


}
