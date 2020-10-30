package com.chass.gsms.networks.clients;

import com.chass.gsms.helpers.ThreadsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseNetworkClient {
  protected ThreadsManager threadsManager;

  protected boolean successful;

  private INetworkListener listener;
  private boolean returnOnMainThread;

  protected BaseNetworkClient(ThreadsManager threadsManager){
    this.threadsManager = threadsManager;
  }

  public boolean isSuccessful(){
    return successful;
  }

  protected abstract HttpURLConnection openConnection(URL url) throws IOException;

  public void request(URL url, INetworkListener listener){
    this.listener = listener;
    this.returnOnMainThread = true;
    threadsManager.execute(()->{
      doInBackground(url);
    });
  }

  public void request(URL url, INetworkListener listener, boolean returnOnMainThread){
    this.listener = listener;
    this.returnOnMainThread = returnOnMainThread;
    threadsManager.execute(()->{
      doInBackground(url);
    });
  }

  private void doInBackground(URL url){
    HttpURLConnection conn = null;
    try{
      conn = openConnection(url);
      conn.connect();

      StringBuilder sb = new StringBuilder();
      int responseCode = conn.getResponseCode();
      if (responseCode >= 200 && responseCode < 300) {
        successful = true;
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
      }
      else{
        successful = false;
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
      }
      respond(responseCode, sb.toString());
    } catch (Exception ex){
      fail(ex);
    } finally {
      if(conn != null){
        conn.disconnect();
      }
    }
  }

  private void respond(int responseCode, String response) {
    if(returnOnMainThread){
      threadsManager.post(() -> listener.onResponse(responseCode, response));
    }
    else {
      listener.onResponse(responseCode, response);
    }
  }

  private void fail(Throwable t){
    if(returnOnMainThread){
      threadsManager.post(() -> listener.onFailure(t));
    }
    else {
      listener.onFailure(t);
    }
  }
}
