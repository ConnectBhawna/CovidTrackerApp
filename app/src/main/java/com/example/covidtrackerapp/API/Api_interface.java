package com.example.covidtrackerapp.API;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api_interface {

    static final String BASE_URL = "https://jsonformatter.org/";

    @GET("countries")
    Call<List<CountryData>> getCountryData();
}
