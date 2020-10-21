package com.chass.gsms.helpers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.School;
import com.chass.gsms.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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

  @Inject
  public SessionManager(){
    initializeCookie();
  }

  /**
   * We initialize cookie for managing session with the server
   * TODO: check if retrofit manages session automatically
   */
  private void initializeCookie() {
    CookieManager cookieManager = new CookieManager();
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

  private User user;

  private School school;

  public User getUser(){
    return user;
  }

  public School getSchool() {
    return school;
  }

  /**
   * Retrofit is handling conversion for us
   * @param response converted network response
   */
  public void login(LoginResponse response){
    user = response.getUser();
    school = response.getSchool();
    loggedIn.setValue(user != null && school != null);
  }

  public void logout(){
    user = null;
    school = null;
    loggedIn.setValue(false);
  }
}
