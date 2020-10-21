package com.chass.gsms.hilt;

import com.chass.gsms.networks.retrofit.ApiClient;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {
  private static final String BASE_URL = "http://chass.me.ht/";

  @Singleton
  @RetrofitRequestDefaultTimeout
  @Provides
  public ApiClient getApiClient(){
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient.class);
  }

  @Singleton
  @RetrofitRequestExtendedTimeout
  @Provides
  public ApiClient getExtendedApiClient(){
    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS).build();
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient.class);
  }
}
