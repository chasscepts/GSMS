package com.chass.gsms.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chass.gsms.models.School;
import com.chass.gsms.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class SessionManager {
  private static SessionManager instance;
  private static final Object LOCK = new Object();

  public static SessionManager getInstance(){
    if(instance == null){
      synchronized (LOCK){
        if(instance == null){
          instance = new SessionManager();
        }
      }
    }
    return instance;
  }

  //We initialize cookie for managing session on the server
  //TODO: check if retrofit manages session
  private CookieManager cookieManager;

  private SessionManager(){
    cookieManager = new CookieManager();
    CookieHandler.setDefault(cookieManager);  //The system automatically handles session after this.
  }

  /**
   * We will observe this from both MainActivity and LoginActivity.
   * In MainActivity, when this is set to false ie user logged out, we launch LoginActivity and call finish()
   * In LoginActivity, when this is set to true ie user successfully logged in, we launch MainActivity and call finish()
   */
  private MutableLiveData<Boolean> loggedIn =  new MutableLiveData<>();
  public LiveData<Boolean> isLoggedIn(){
    return loggedIn;
  }

  public User user;

  public School school;

  private User getUser(){
    return user;
  }

  public School getSchool() {
    return school;
  }

  /**
   * This is called after success response from login or school registration attempts.
   * It parses the text into user and school and sets loggedIn if successful.
   * @param networkResponse response received from network request
   */
  public void login(String networkResponse){
    try {
      JSONObject obj = new JSONObject(networkResponse);
      user = User.parse(obj.getString("user"));
      school = School.parse(obj.getString("school"));
      loggedIn.setValue(user != null && school != null);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void logout(){
    user = null;
    school = null;
    loggedIn.setValue(false);
  }
}
