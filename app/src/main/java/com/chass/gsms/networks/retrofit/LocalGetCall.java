package com.chass.gsms.networks.retrofit;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalGetCall<T> implements Call<T> {
  private boolean canceled = false;
  private Request request;
  private T response;

  private LocalGetCall(Request request, T response){
    this.request = request;
    this.response = response;
  }

  @NonNull
  @Override
  public Response<T> execute() throws IOException {
    throw new IOException("Not supported");
  }

  @Override
  public void enqueue(Callback<T> callback) {
    callback.onResponse(this, Response.success(response));
  }

  @Override
  public boolean isExecuted() {
    return false;
  }

  @Override
  public void cancel() {
    canceled = true;
  }

  @Override
  public boolean isCanceled() {
    return canceled;
  }

  @NonNull
  @Override
  public Call<T> clone() {
    return new LocalGetCall<T>(request, response);
  }

  @NonNull
  @Override
  public Request request() {
    return request;
  }

  /**
   * Exposes a few methods of okhttp3 Request
   */
  public static class Builder<T>{
    private T _response;
    private Request request;
    Request.Builder builder;

    public Builder(){
      builder = new Request.Builder();
      builder.get();
    }

    public LocalGetCall<T> build(){
      return new LocalGetCall<>(builder.build(), _response);
    }

    public Builder response(T response){
      _response = response;
      return this;
    }

    public Builder addHeader(String key, String value){
      builder.addHeader(key, value);
      return this;
    }

    public Builder url(String url){
      builder.url(url);
      return this;
    }
  }
}
