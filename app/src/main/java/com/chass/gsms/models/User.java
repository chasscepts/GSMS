package com.chass.gsms.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
  public static final String FIRSTNAME = "firstname";
  public static final String LASTNAME = "lastname";
  public static final String EMAIL = "email";
  public static final String PHONE_NUMBER = "phoneNumber";
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

  public User(String firstname, String lastname, String email, String phoneNumber) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public static User parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      return new User(
          obj.getString(FIRSTNAME),
          obj.getString(LASTNAME),
          obj.getString(EMAIL),
          obj.getString(PHONE_NUMBER)
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
