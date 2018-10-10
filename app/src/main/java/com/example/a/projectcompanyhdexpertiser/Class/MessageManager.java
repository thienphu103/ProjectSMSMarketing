package com.example.a.projectcompanyhdexpertiser.Class;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

import static com.example.a.projectcompanyhdexpertiser.APIConnect.APIConnect.LINK;

public class MessageManager {

    public ArrayList<UserMessage> arrayList;
    public ArrayAdapter<UserMessage> arrayAdapter;

    public void getData(final Context context, final ListView listView) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, LINK + "message",
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

                                if (context != null) {
                                    arrayAdapter = new ArrayAdapter<UserMessage>(context, android.R.layout.simple_list_item_1, arrayList);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listView.setAdapter(arrayAdapter);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Server Timeout, Xui lòng thử lại!", Toast.LENGTH_SHORT).show();
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

    public void updateData(final Context context, final String id) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LINK + "update?uuid=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  Toast.makeText(getApplicationContext(), "Server: " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Error: " + error, Toast.LENGTH_SHORT).show();
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
}
