package com.chass.gsms.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class School {
  private int id;
  private String name, address, email, phoneNumber, picture, adminEmail;
  private ClassSummary[] classSummaries;

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

  public ClassSummary[] getClassSummaries() {
    return classSummaries;
  }

  /**
   * Retrofit converts JSONArray to java Arrays and not List.
   * We have to provide our own add method. It is not efficient but I figured we are not going to be adding a hell many classes to a class anyway.
   * @param newClass class to add to school
   */
  public void addClass(Class newClass){
    if(this.classSummaries == null || this.classSummaries.length == 0){
      this.classSummaries = new ClassSummary[]{new ClassSummary(newClass.getId(), newClass.getName(), newClass.getStudents().length)};
      return;
    }
    int length = this.classSummaries.length;
    ClassSummary[] temp = new ClassSummary[length + 1];
    System.arraycopy(this.classSummaries, 0, temp, 0, length);
    temp[length] = new ClassSummary(newClass.getId(), newClass.getName(), newClass.getStudents().length);
    this.classSummaries = temp;
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
      List<ClassSummary> classSummaries = new ArrayList<>();
      JSONArray classesArray = obj.getJSONArray(CLASSES);
      int length = classesArray.length();
      if(length > 0){
        for(int i = 0; i < length; i++){
          ClassSummary classSummary = ClassSummary.parse(classesArray.getString(i));
          if(classSummary != null){
            classSummaries.add(classSummary);
          }
        }
      }
      School school = new School();
      school.id = obj.getInt(ID);
      school.name = obj.getString(NAME);
      school.address = obj.getString(ADDRESS);
      school.email = obj.getString(EMAIL);
      school.phoneNumber = obj.getString(PHONE_NUMBER);
      school.picture = obj.getString(PICTURE);
      school.adminEmail = obj.getString(ADMIN_EMAIL);
      school.classSummaries = (ClassSummary[])classSummaries.toArray();

      return school;
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String toJson(){
    return new  GsonBuilder().create().toJson(this);
  }

}
