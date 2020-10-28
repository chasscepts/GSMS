package com.chass.gsms.hilt;

import com.chass.gsms.helpers.SessionManager;
import com.chass.gsms.networks.retrofit.ApiClient;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {
  private static final String BASE_URL = "http://chass.me.ht/";
  private static final CookieManager cookieManager = new CookieManager();

  public static CookieManager getCookieManager(){
    return cookieManager;
  }

  @Singleton
  @RetrofitRequestDefaultTimeout
  @Provides
  public ApiClient getApiClient(){
    CookieHandler cookieHandler = getCookieManager();
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .cookieJar(new JavaNetCookieJar(cookieHandler))
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build();
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient.class);
  }

  @Singleton
  @RetrofitRequestExtendedTimeout
  @Provides
  public ApiClient getExtendedApiClient(){
    CookieHandler cookieHandler = getCookieManager();
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .cookieJar(new JavaNetCookieJar(cookieHandler))
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build();
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient.class);
  }
}
