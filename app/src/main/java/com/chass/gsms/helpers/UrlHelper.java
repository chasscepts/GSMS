package com.chass.gsms.helpers;

import android.net.Uri;

import java.net.URL;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UrlHelper {
  private final String BASE_URL = "http://chass.me.ht/";

  private URL registerUlr, loginUrl;

  @Inject
  public UrlHelper(){}

  private URL getUrl(String path){
    URL url = null;
    Uri uri = Uri.parse(BASE_URL + path);
    try{
      url = new URL(uri.toString());
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return url;
  }

  public URL getRegisterUrl(){
    if(registerUlr == null){
      registerUlr = getUrl("school/register");
    }
    return registerUlr;
  }

  public URL getLoginUrl(){
    if(loginUrl == null){
      loginUrl = getUrl("school/user");
    }
    return loginUrl;
  }

}
