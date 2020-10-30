package com.chass.gsms.models;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

/**
 * A container for server response after successful login or registration.
 * The purpose is for retrofit to parse the response for us.
 */
public class LoginResponse {
  private School school;
  private User user;

  public School getSchool() {
    return school;
  }

  public User getUser() {
    return user;
  }

  public LoginResponse(){

  }

  @Nullable
  public static LoginResponse parse(String response){
    try{
      JSONObject json = new JSONObject(response);
      School school = School.parse(json.getString("school"));
      User user = User.parse(json.getString("user"));
      if(school != null && user != null){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.school = school;
        loginResponse.user = user;
        return loginResponse;
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }
}
