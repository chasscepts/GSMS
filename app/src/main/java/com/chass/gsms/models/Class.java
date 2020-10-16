package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Class {
  private String name;
  private User teacher;
  private List<Student> students;

  public String getName(){
    return name;
  }

  public User getTeacher(){
    return teacher;
  }

  public Student[] getStudents(){
    return (Student[]) students.toArray();
  }

  public void addStudent(Student student){
    students.add(student);
  }

  public Class(String name, User teacher){
    this.name = name;
    this.teacher = teacher;
    this.students = new ArrayList<>();
  }

  public static Class parse(String text){
    Class instance = null;
    try {
      JSONObject obj = new JSONObject(text);
      User teacher = User.parse(obj.getString("teacher"));
      if(teacher == null){
        return null;
      }
      instance = new Class(obj.getString("name"), teacher);

      JSONArray jsonArray = obj.getJSONArray("students");
      int length = jsonArray.length();
      Student student;
      for(int i = 0; i < length; i++){
        student = Student.parse(jsonArray.getString(i));
        if(student != null){
          instance.addStudent(student);
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return instance;
  }
}
