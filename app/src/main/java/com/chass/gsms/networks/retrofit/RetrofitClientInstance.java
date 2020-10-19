package com.chass.gsms.networks.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClientInstance {
  private static Retrofit retrofit;
  private static Object LOCK = new Object();
  private static final String BASE_URL = "http://chass.me.ht/";

  public static Retrofit getRetrofitInstance() {
    if (retrofit == null) {
      synchronized (LOCK){
        if (retrofit == null) {
          retrofit = new Retrofit.Builder()
              .baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
        }
      }
    }
    return retrofit;
  }
}
