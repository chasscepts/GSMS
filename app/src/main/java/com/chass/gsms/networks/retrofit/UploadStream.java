package com.chass.gsms.networks.retrofit;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class UploadStream extends RequestBody {
  MediaType mediaType;
  private Uri uri;
  private Application application;

  @Inject
  public UploadStream(Application application){
    this.application = application;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public void setMediaType(String mediaType) {
    this.mediaType = MediaType.parse(mediaType);
  }

  @Nullable
  @Override
  public MediaType contentType() {
    return mediaType;
  }

  @Override
  public void writeTo(@NonNull BufferedSink sink) throws IOException {
    if(uri == null) return;
    Source source = null;
    try {
      InputStream inputStream = application.getContentResolver().openInputStream(uri);
      if(inputStream == null) return;
      source = Okio.source(inputStream);
      sink.writeAll(source);
    } finally {
      Util.closeQuietly(source);
    }
  }
}
