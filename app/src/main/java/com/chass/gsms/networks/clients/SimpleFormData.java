package com.chass.gsms.networks.clients;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

public class SimpleFormData implements IFormData {
  private StringBuilder sb;

  @Inject
  public SimpleFormData(){

  }

  public void clear(){
    sb = null;
  }

  public void append(String key, String value){
    if(sb == null){
      sb = new StringBuilder();
    }
    else {
      sb.append("&");
    }
    sb.append(key)
        .append('=')
        .append(value);
  }

  @Override
  public void use(HttpURLConnection connection) throws IOException {
    if(sb == null) return;
    connection.setRequestMethod("POST");
    OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());

    BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
    writer.write(sb.toString());
    writer.flush();
    writer.close();
    outputStream.close();
  }
}
