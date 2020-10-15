package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class School {
  public static final String CLASSES = "classes";
  public static final String ID = "id";
  public static final String NAME = "name";
  public static final String ADDRESS = "address";
  public static final String EMAIL = "email";
  public static final String PHONE_NUMBER = "phone_number";
  public static final String PICTURE = "picture";
  private String id, name, address, email, phoneNumber, picture;
  private String[] classes;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getPicture() {
    return picture;
  }

  public String[] getClasses() {
    return classes;
  }

  public School(String id, String name, String address, String email, String phoneNumber, String picture, String[] classes) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.picture = picture;
    this.classes = classes;
  }

  public static School parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      String[] classes;
      JSONArray classesArray = obj.getJSONArray(CLASSES);
      int length = classesArray.length();
      if(length > 0){
        classes = new String[length];
        for(int i = 0; i < length; i++){
          classes[i] = classesArray.getString(i);
        }
      }
      else {
        classes = new String[]{};
      }
      return new School(
          obj.getString(ID),
          obj.getString(NAME),
          obj.getString(ADDRESS),
          obj.getString(EMAIL),
          obj.getString(PHONE_NUMBER),
          obj.getString(PICTURE),
          classes
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
