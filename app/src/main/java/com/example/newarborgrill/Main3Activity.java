package com.example.newarborgrill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main3Activity extends AppCompatActivity {

    Button shoppingCart;
    private Map<String, Integer> itemData = new HashMap<>();
    public static Map<String , Integer> cartData = new HashMap<>();
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        add = (Button)findViewById(R.id.addButton);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);


      //  shoppingCart = (Button) findViewById(R.id.cartButton);
       /* shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(i);
            }
        });*/

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ItemInterface.baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        ItemInterface itemInterface = retrofit.create(ItemInterface.class);
        Call<List<Item>> call = itemInterface.getMenu();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                List<Item> menuItems = response.body();
                ItemAdapter adp = new ItemAdapter(menuItems, R.layout.list_item_data,getApplicationContext());
                recyclerView.setAdapter(adp);

                for(Item item:menuItems){
                    itemData.put(item.getName(),Integer.parseInt(item.getPrice()));
                    Log.d("Name", "Item Name: "+item.getName());
                    Log.d("Name", "Item Des: "+item.getDescription());
                    Log.d("Name", "Item Price: "+item.getPrice());

                    itemData.put(item.getName(), Integer.parseInt(item.getPrice()));


                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

            cartData.put("Pepperoni Pizza (1): ",40);
            cartData.put("Mac and Cheese (1):",10);
            cartData.put("Curly Fries (1):",10);







    }
}
