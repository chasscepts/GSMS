package com.chass.gsms.models;

import com.chass.gsms.enums.Roles;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
  public static final String FIRSTNAME = "firstname";
  public static final String LASTNAME = "lastname";
  public static final String EMAIL = "email";
  public static final String PHONE_NUMBER = "phone_number";
  public static final String ROLE = "role";
  private String firstname, lastname, email, phoneNumber;
  private Roles role;

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

  public Roles getRole() {
    return role;
  }

  public User(String firstname, String lastname, String email, String phoneNumber, Roles role) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
  }

  public static User parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      String roleText = obj.getString(ROLE);
      if("admin".equals(roleText)){
        return new User(
            obj.getString(FIRSTNAME),
            obj.getString(LASTNAME),
            obj.getString(EMAIL),
            obj.getString(PHONE_NUMBER),
            Roles.ADMIN
        );
      }
      if("teacher".equals(roleText)){
        return Teacher.parse(text);
      }
      return Parent.parse(text);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
