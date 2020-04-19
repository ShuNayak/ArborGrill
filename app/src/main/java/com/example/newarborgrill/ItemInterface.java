package com.example.newarborgrill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemInterface {

    String baseUrl = "https://menuapi.azurewebsites.net/api/";

    @GET("menuitems")
    Call<List<Item>> getMenu();



}
