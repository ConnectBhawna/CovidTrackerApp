package com.example.covidtrackerapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidtrackerapp.API.ApiUtilities;
import com.example.covidtrackerapp.API.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalConfirm,todayConfirm,totalActive,totalRecovered,todayRecovered,totalDeaths;
    private TextView dateTV,todayDeath,totalTests;
    private PieChart pieChart;
    private String updated;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Object> list = new ArrayList<>();

        init();

        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                               list.addAll(response.body());

                               for(int i = 0 ;i<list.size();i++){
                                   if (!list.get(i).getClass().equals("India")) {
                                       continue;
                                   }
                                   int confirm = Integer.parseInt(String.valueOf(list.get(i).getClass()));
                                   int active = Integer.parseInt(list.get(i).toString());
                                   int recovered = Integer.parseInt(String.valueOf(list.get(i).hashCode()));
                                   int death = Integer.parseInt(String.valueOf(list.get(i).getClass()));

                                   totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                   totalActive.setText(NumberFormat.getInstance().format(active));
                                   totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                   totalDeaths.setText(NumberFormat.getInstance().format(death));


                                   todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).toString())));
                                   todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).toString())));
                                   todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(String.valueOf(list.get(i).hashCode()))));


                                   setText(list.get(i).toString());

                                   pieChart.addPieSlice( new PieModel("Confirm",confirm,getResources().getColor(R.color.yellow)));
                                   pieChart.addPieSlice( new PieModel("Active",active,getResources().getColor(R.color.blue_pie)));
                                   pieChart.addPieSlice( new PieModel("Recovered",recovered,getResources().getColor(R.color.green_pie)));
                                   pieChart.addPieSlice( new PieModel("Deaths",death,getResources().getColor(R.color.red_pie)));



                               }

                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error : " +t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setText(String toString) {
        DateFormat format = new SimpleDateFormat("MMM.dd,yyyy");

        
        long millisecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);

        dateTV.setText("Updated at"+format.format(calendar.getTime()));
    }

    private void init(){
        totalConfirm = findViewById(R.id.totalConfirm);
        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeaths = findViewById(R.id.totalDeath);
        totalConfirm = findViewById(R.id.todayConfirm);
        totalTests = findViewById(R.id.totalTests);
        todayRecovered = findViewById(R.id.todayRecovered);
        todayDeath = findViewById(R.id.todayDeath);
        pieChart = findViewById(R.id.pieChart);
        dateTV = findViewById(R.id.date);

   }
}


