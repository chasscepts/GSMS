package com.chass.gsms.networks.clients;

import com.chass.gsms.helpers.ThreadsManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

public class PostHttpClient extends BaseNetworkClient {
  IFormData data;

  public void setData(IFormData data){
    this.data = data;
  }

  @Inject
  public PostHttpClient(ThreadsManager threadsManager) {
    super(threadsManager);
  }

  @Override
  protected HttpURLConnection openConnection(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    if(data != null){
      data.use(connection);
    }
    return connection;
  }
}
