package com.thoughtworks.craftsbeer.servicemanger;

import com.thoughtworks.craftsbeer.data.Beers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by prasad on 6/30/18.
 */

public interface ApiInterface {


        @GET("beercraft")
        Call<List<Beers>> getBeersList();



}
