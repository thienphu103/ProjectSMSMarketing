package com.example.a.projectcompanyhdexpertiser.Controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a.projectcompanyhdexpertiser.Model.UserMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnection.HOST;
import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnection.PATH;

public class MessageManagerSevice {
    public ArrayList<UserMessage> arrayList;
    public Context context;
    public void setContext(Context context) {
        this.context = context;
    }

    public void getData() {
        arrayList = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
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

}
