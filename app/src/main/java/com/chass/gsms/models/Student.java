package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Student {
  public static final String ID = "id";
  public static final String FIRSTNAME = "firstname";
  public static final String LASTNAME = "lastname";
  public static final String CLASS_NAME = "class_name";
  private final int id;
  private final String firstname, lastname, className;
  private final User parent1, parent2;

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getClassName() {
    return className;
  }

  public User getParent1() {
    return parent1;
  }

  public User getParent2() {
    return parent2;
  }

  public Student(int id, String firstname, String lastname, String className, User parent1, User parent2) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.className = className;
    this.parent1 = parent1;
    this.parent2 = parent2;
  }

  public static Student parse(String text){
    try {
      JSONObject obj = new JSONObject(text);
      User parent1 = null, parent2 = null;
      JSONArray parentsArray = obj.getJSONArray("parents");
      int length = parentsArray.length(), pos = 0;
      if(pos < length){
        parent1 = User.parse(parentsArray.getString(pos++));
        if(pos < length){
          parent2 = User.parse(parentsArray.getString(pos));
        }
      }
      if(parent1 == null && parent2 == null){
        return null;
      }
      return new Student(
        obj.getInt(ID),
        obj.getString(FIRSTNAME),
        obj.getString(LASTNAME),
        obj.getString(CLASS_NAME),
        parent1,
        parent2
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}