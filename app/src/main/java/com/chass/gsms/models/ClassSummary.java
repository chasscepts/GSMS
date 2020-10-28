package com.chass.gsms.models;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassSummary {
  private int id, numberOfStudents;
  private String name;

  public int getId() {
    return id;
  }

  public int getNumberOfStudents() {
    return numberOfStudents;
  }

  public String getName() {
    return name;
  }

  public ClassSummary(){}

  public ClassSummary(int id, String name, int numberOfStudents) {
    this.id = id;
    this.numberOfStudents = numberOfStudents;
    this.name = name;
  }

  @Nullable
  public static ClassSummary parse(String json){
    try {
      JSONObject obj = new JSONObject(json);
      return new ClassSummary(
          obj.getInt("id"),
          obj.getString("name"),
          obj.getInt("numberOfStudents")
      );
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
