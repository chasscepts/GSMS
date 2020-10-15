package com.chass.gsms.models;

import com.chass.gsms.enums.Roles;

import org.json.JSONException;
import org.json.JSONObject;

public class Teacher extends User {
  public static final String CLASS_NAME = "class_name";
  private String className;

  public String getClassName() {
    return className;
  }

  public Teacher(String firstname, String lastname, String email, String phoneNumber, String className) {
    super(firstname, lastname, email, phoneNumber, Roles.TEACHER);
    this.className = className;
  }

  public static Teacher parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      return new Teacher(
          obj.getString(FIRSTNAME),
          obj.getString(LASTNAME),
          obj.getString(EMAIL),
          obj.getString(PHONE_NUMBER),
          obj.getString(CLASS_NAME)
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
