package com.chass.gsms.networks.clients;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MultipartFormData implements IFormData {
  private Application application;
  private final List<Field> store = new ArrayList<>();
  private final String twoHyphens = "--";
  private final String boundary = "-----" + System.currentTimeMillis() + "-----";
  private final String lineEnd = "\r\n";
  private final String jobSignature = "Uploading File";

  public void clear(){
    store.clear();
  }

  public void append(String key, String value){
    store.add(new TextField(key, value));
  }

  public void append(String key, File value){
    store.add(new FileField(key, value));
  }

  public void append(String key, Uri value){
    store.add(new UriField(key, value));
  }

  @Inject
  public MultipartFormData(Application application){
    this.application = application;
  }

  private String getFilename(String path){
    if(TextUtils.isEmpty(path)) return "file";
    int idx = path.lastIndexOf('/');
    if(idx >= 0){
      path = path.substring(idx);
    }
    idx = path.indexOf(':');
    if(idx >= 0){
      path = path.substring(0, idx);
    }
    if(TextUtils.isEmpty(path)) return "file";
    return path;
  }

  @Override
  public void use(HttpURLConnection connection) throws IOException {
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Connection", "Keep-Alive");
    connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    connection.setDoInput(true);
    connection.setDoOutput(true);
    connection.setUseCaches(false);
    DataOutputStream stream = new DataOutputStream(connection.getOutputStream());

    for(Field field : store){
      field.write(stream);
    }
  }

  private interface Field{
    public abstract void write(DataOutputStream stream) throws IOException;
  }

  private class TextField implements Field{
    private final String key, value;

    private TextField(String key, String value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
      stream.writeBytes(twoHyphens + boundary + lineEnd);
      stream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
      stream.writeBytes("Content-Type: text/plain" + lineEnd);
      stream.writeBytes(lineEnd);
      stream.writeBytes(value);
      stream.writeBytes(lineEnd);
    }
  }

  private class FileField implements Field{
    private final String key;
    private final File file;

    private FileField(String key, File file) {
      this.key = key;
      this.file = file;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
      if(!file.exists()) throw new IOException("File does not exist @" + file.getAbsolutePath());
      FileInputStream fileInputStream = new FileInputStream(file);

      int bytesRead, bytesAvailable, bufferSize;
      byte[] buffer;
      int maxBufferSize = 1024 * 1024;
      String fileName = file.getName();

      stream.writeBytes(twoHyphens + boundary + lineEnd);
      stream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" +
          fileName + "\"" + lineEnd);
      stream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(fileName) +
          lineEnd);
      stream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

      stream.writeBytes(lineEnd);

      bytesAvailable = fileInputStream.available();
      bufferSize = Math.min(bytesAvailable, maxBufferSize);
      buffer = new byte[bufferSize];

      bytesRead = fileInputStream.read(buffer, 0, bufferSize);
      while (bytesRead > 0) {
        stream.write(buffer, 0, bufferSize);
        bytesAvailable = fileInputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
      }

      stream.writeBytes(lineEnd);
    }
  }

  private class UriField implements Field{
    private final String key;
    private final Uri uri;

    private UriField(String key, Uri uri) {
      this.key = key;
      this.uri = uri;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
      InputStream inputStream = application.getContentResolver().openInputStream(uri);
      if(inputStream == null){
        throw new IOException("Content Resolver returned null from Uri.");
      }

      int bytesRead, bytesAvailable, bufferSize;
      byte[] buffer;
      int maxBufferSize = 1024 * 1024;
      String fileName = getFilename(uri.getPath());
      String type = application.getContentResolver().getType(uri);

      stream.writeBytes(twoHyphens + boundary + lineEnd);
      stream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" +
          fileName + "\"" + lineEnd);
      stream.writeBytes("Content-Type: " + type + lineEnd);
      stream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);

      stream.writeBytes(lineEnd);

      bytesAvailable = inputStream.available();
      bufferSize = Math.min(bytesAvailable, maxBufferSize);
      buffer = new byte[bufferSize];

      bytesRead = inputStream.read(buffer, 0, bufferSize);
      while (bytesRead > 0) {
        stream.write(buffer, 0, bufferSize);
        bytesAvailable = inputStream.available();
        bufferSize = Math.min(bytesAvailable, maxBufferSize);
        bytesRead = inputStream.read(buffer, 0, bufferSize);
      }

      stream.writeBytes(lineEnd);
    }
  }
}
