package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class School {
  private int id;
  private String name, address, email, phoneNumber, picture, adminEmail;
  private String[] classes;

  public int getId() {
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

  public String getAdminEmail() {
    return adminEmail;
  }

  public String[] getClasses() {
    return classes;
  }

  /**
   * Retrofit converts JSONArray to java Arrays and not List.
   * We have to provide our own add method. It is not efficient but I figured we are not going to be adding a hell many classes to a class anyway.
   * @param newClass class to add to school
   */
  public void addClass(Class newClass){
    if(this.classes == null || this.classes.length == 0){
      this.classes = new String[]{newClass.getName()};
      return;
    }
    int length = this.classes.length;
    String[] temp = new String[length + 1];
    System.arraycopy(this.classes, 0, temp, 0, length);
    temp[length] = newClass.getName();
    this.classes = temp;
  }

  public School() {

  }

  public static School parse(String text){
    String CLASSES = "classes";
    String ID = "id";
    String NAME = "name";
    String ADDRESS = "address";
    String EMAIL = "email";
    String PHONE_NUMBER = "phoneNumber";
    String PICTURE = "picture";
    String ADMIN_EMAIL = "adminEmail";
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
      School school = new School();
      school.id = obj.getInt(ID);
      school.name = obj.getString(NAME);
      school.address = obj.getString(ADDRESS);
      school.email = obj.getString(EMAIL);
      school.phoneNumber = obj.getString(PHONE_NUMBER);
      school.picture = obj.getString(PICTURE);
      school.adminEmail = obj.getString(ADMIN_EMAIL);
      school.classes = classes;

      return school;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

}
