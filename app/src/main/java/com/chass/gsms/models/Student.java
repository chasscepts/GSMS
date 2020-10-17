package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Student {
  private int id;
  private String firstname, lastname, className;
  private User parent1, parent2;

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

  public Student() {

  }

  public static Student parse(String text){
    String ID = "id";
    String FIRSTNAME = "firstname";
    String LASTNAME = "lastname";
    String CLASS_NAME = "className";
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
      Student student = new Student();
      student.id = obj.getInt(ID);
      student.firstname = obj.getString(FIRSTNAME);
      student.lastname = obj.getString(LASTNAME);
      student.className = obj.getString(CLASS_NAME);
      student.parent1 = parent1;
      student.parent2 = parent2;

      return student;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}