package com.example.newarborgrill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Main4Activity extends AppCompatActivity {

    Button backToPlaceOrder;
    TextView textView;
    int putCartId = Main2Activity.cartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        textView = (TextView) findViewById(R.id.displayOrderView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        backToPlaceOrder = (Button) findViewById(R.id.backButton);
        backToPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main4Activity.this, Main2Activity.class);
                startActivity(i);
            }
        });


        for (Map.Entry<String, Integer> mappy : Main3Activity.cartData.entrySet()) {
            textView = (TextView) findViewById(R.id.displayOrderView);
            textView.append(mappy.getKey() + "-" + mappy.getValue() + "\n");

        }


        //adding items to cart
        // first we make post request for each item we make a post request
        for (String keys : Main3Activity.cartData.keySet()) {
            final String item = keys;
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("status", keys);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            final String postData = jsonBody.toString();
            String url = "https://cartitems.azurewebsites.net/api/IteminCarts/ADDITEMTOCART/"+putCartId;
            RequestQueue postrequestQueue = Volley.newRequestQueue(getApplicationContext());

            Log.i("CART CART ID ^^^^^",""+putCartId);

            StringRequest s = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jso = new JSONObject(response);
                        Log.i("CART SUCCESS","Added "+ item+ " item to CART");
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
                        responseString = new String (response.data);
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


        }
    }
}
