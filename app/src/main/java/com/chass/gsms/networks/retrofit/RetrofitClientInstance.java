package com.chass.gsms.networks.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {
  private static Retrofit retrofit;
  private static final Object LOCK = new Object();
  public static final String BASE_URL = "http://chass.me.ht/";

  public static Retrofit getRetrofitInstance() {
    if (retrofit == null) {
      synchronized (LOCK){
        if (retrofit == null) {
          //https://medium.com/@thanhtungvo/upload-file-in-android-jave-with-retrofit-2-ae4822224e94
          //Using custom client to increase connection timeout
          OkHttpClient client = new OkHttpClient.Builder()
              .connectTimeout(60, TimeUnit.SECONDS)
              .readTimeout(60, TimeUnit.SECONDS).build();
          retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL).client(client)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
        }
      }
    }
    return retrofit;
  }
}
