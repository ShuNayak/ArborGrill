package com.example.newarborgrill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    Button menu;
    Button check;
    TextView statusView;
    Button shoppingCart;
    public static int cartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        menu = (Button) findViewById(R.id.OrderButton);
        check = (Button) findViewById(R.id.statusButton);
        statusView = (TextView) findViewById(R.id.displayStatus);
        shoppingCart = (Button) findViewById(R.id.cartButton);
        final RequestQueue requestQ = Volley.newRequestQueue(this);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(i);
            }
        });

        final JsonArrayRequest jor = new JsonArrayRequest(Request.Method.GET, "https://orders23.azurewebsites.net/api/orderstatus/CSORD/9805314", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jarr) {
                try {

                   for(int i=0;i<jarr.length();i++){
                       JSONObject jo = jarr.getJSONObject(i);
                       String status =jo.getString("status");
                       Log.i("BUTTON STATUS BUTTON",""+status);
                       statusView.setText(status);
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("BUTTON STATUS BUTTON",error.getMessage());
                Log.i("BUTTON STATUS BUTTON","Error when check status");
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTON STATUS BUTTON","button clicked");
                requestQ.add(jor);

            }
        });

        //adding fixed items to cart
        // first we make a cart for MainActivity CSUNLoginId
        //final String postData = "{"+"\"CSUNID\""+ String.valueOf(MainActivity.CSUNLoginId)+"}";
        JSONObject jsonBody = new JSONObject();
        try {
        jsonBody.put("CSUNID", Integer.parseInt(MainActivity.CSUNLoginId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String postData = jsonBody.toString();
        String url = "https://cartitems.azurewebsites.net/api/cart/MAKECART";
        RequestQueue postrequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest s = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("JSON response",""+response.toString());
                    JSONObject jso = new JSONObject(response);
                  //  Log.i("JSON obj",""+jso.toString());
                   cartId = (int)jso.get("id");
                } catch (JSONException e) {

                        e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return postData == null ? null : postData.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", postData, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                  //  responseString = String.valueOf(response.statusCode);
                    responseString = new String(response.data);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        postrequestQueue.add(s);

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, Main4Activity.class);
                startActivity(i);
            }
        });
    }


}

/*
{
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("CSUNID", MainActivity.CSUNLoginId);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
 */
