package com.example.testvideoview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= findViewById(R.id.textView);

//        testJsonSever();
//        testWeatherApi();

        getWeatherData();


    }

    private void testJsonSever(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://localhost:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONApi jsonApi= retrofit.create(JSONApi.class);

        Call<List<Employees>> call= jsonApi.getEmployees();
        call.enqueue(new Callback<List<Employees>>() {
            @Override
            public void onResponse(Call<List<Employees>> call, Response<List<Employees>> response) {
                if(!response.isSuccessful()){
                    textView.setText("code:" +response.code());
                    return;
                }

                List<Employees> employeess= response.body();
                for(Employees employees: employeess){
                    String detail= "";
                    detail += employees.getId() + "\n";
                    detail += employees.getFirstName() + "\n";
                    detail += employees.getLastName() + "\n";
                    detail += employees.getAddress() + "\n";
                    textView.append(detail);
                }


            }

            @Override
            public void onFailure(Call<List<Employees>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void testWeatherApi(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi= retrofit.create(WeatherApi.class);

        final String[] cityid = new String[1];
        final WeatherModel[] weatherModel = new WeatherModel[1];

        Call<WeatherModel> call= weatherApi.getWeatherData("1581130","2dd080c4941c08ae6b785aa3436059ab");
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"loi roi", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(MainActivity.this,"Thanh cong", Toast.LENGTH_LONG).show();
                weatherModel[0] = response.body();
                if(weatherModel[0]==null){
                    Toast.makeText(MainActivity.this,"khong co ket qua", Toast.LENGTH_LONG).show();
                }else {
                    cityid[0] = weatherModel[0].getCity().getId()+"";
                    textView.setText(cityid[0]);
                }

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }

    private void getWeatherData() {
        String path= "http://api.openweathermap.org/data/2.5/forecast/daily?id=1581130&appid=2dd080c4941c08ae6b785aa3436059ab";
        String[] abc = new String[1];
        NetworkTask networkTask= new NetworkTask(path, new NetworkTask.Callback() {
            @Override
            public void returnString(String s) {
                textView.setText(s);
                abc[0] =s;
            }
        });
        Toast.makeText(MainActivity.this, abc[0]+"abc",Toast.LENGTH_LONG).show();
    }



}//end