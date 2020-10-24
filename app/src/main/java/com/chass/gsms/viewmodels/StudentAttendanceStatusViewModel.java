package com.chass.gsms.viewmodels;

import androidx.databinding.ObservableBoolean;

import com.chass.gsms.models.Student;
import com.chass.gsms.models.StudentAttendanceStatus;
import com.google.gson.JsonObject;

public class StudentAttendanceStatusViewModel {
  private final Student student;

  public Student getStudent(){
    return student;
  }

  public StudentAttendanceStatusViewModel(Student student){
    this.student = student;
  }

  public final ObservableBoolean present = new ObservableBoolean();

  public void toggleCheck(){
    present.set(!present.get());
  }

  public String toString(){
    JsonObject object = new JsonObject();
    object.addProperty("studentId", student.getId());
    object.addProperty("isPresent", present.get());
    return object.toString();
  }
}
