package com.chass.gsms.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Class {
  private int id;
  private String name;
  private User teacher;
  private Student[] students;

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public User getTeacher(){
    return teacher;
  }

  public Student[] getStudents(){
    return students;
  }

  /**
   * There must be a more efficient way to do this but retrofit by converts JSON array to java Array and not ArrayList.
   * @param student the student to add to this class
   */
  public void addStudent(Student student){
    int length = students.length;
    Student[] temp = new Student[length + 1];
    System.arraycopy(students, 0, temp, 0, length);
    temp[length] = student;
    students = temp;
  }

  public Class(){

  }

  /**
   * This method is not useful if retrofit is handling our JSONs
   * @param text
   * @return
   */
  public static Class parse(String text){
    Class instance = null;
    try {
      JSONObject obj = new JSONObject(text);
      User teacher = User.parse(obj.getString("teacher"));
      if(teacher == null){
        return null;
      }
      instance = new Class();
      instance.name = obj.getString("name");
      instance.teacher = teacher;

      JSONArray jsonArray = obj.getJSONArray("students");
      int length = jsonArray.length();
      if(length > 0){
        List<Student> students = new ArrayList<>();
        Student student;
        for(int i = 0; i < length; i++){
          student = Student.parse(jsonArray.getString(i));
          if(student != null){
            students.add(student);
          }
        }
        instance.students = (Student[]) students.toArray();
      }
      else{
        instance.students = new Student[0];
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return instance;
  }
}
