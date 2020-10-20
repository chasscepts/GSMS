package com.chass.gsms.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
  private String firstname, lastname, email, phoneNumber;

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public User() {
  }

  public static User parse(String text){
    String FIRSTNAME = "firstname";
    String LASTNAME = "lastname";
    String EMAIL = "email";
    String PHONE_NUMBER = "phoneNumber";

    try {
      JSONObject obj = new JSONObject(text);
      User user = new User();
      user.firstname = obj.getString(FIRSTNAME);
      user.lastname = obj.getString(LASTNAME);
      user.email = obj.getString(EMAIL);
      user.phoneNumber = obj.getString(PHONE_NUMBER);
      return user;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
