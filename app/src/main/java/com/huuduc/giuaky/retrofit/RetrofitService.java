package com.huuduc.giuaky.retrofit;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Thời gian chờ kết nối
            .readTimeout(30, TimeUnit.SECONDS) // Thời gian chờ khi đọc dữ liệu
            .writeTimeout(30, TimeUnit.SECONDS) // Thời gian chờ khi ghi dữ liệu
            .build();

    public RetrofitService(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:8080")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit(){
        return this.retrofit;
    }
}
