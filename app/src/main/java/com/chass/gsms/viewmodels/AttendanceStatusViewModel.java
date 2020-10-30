package com.chass.gsms.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;

import com.chass.gsms.models.Student;

public class AttendanceStatusViewModel {
  private final Student student;

  public Student getStudent(){
    return student;
  }

  public AttendanceStatusViewModel(Student student){
    this.student = student;
  }

  public final ObservableBoolean status = new ObservableBoolean();

  public void toggleCheck(){
    status.set(!status.get());
  }

  @NonNull
  public String toJson(){
    StringBuilder sb = new StringBuilder();
    sb.append("{\"studentId\":");
    sb.append(student.getId());
    sb.append(",\"status\":\"");
    sb.append(status.get()? 'P' : 'A');
    sb.append("\"}");
    return sb.toString();
  }

  @Nullable
  public static String getJsonString(AttendanceStatusViewModel[] statuses){
    int length = statuses.length;
    if(length == 0) return null;
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(statuses[0].toJson());
    for(int i = 1; i < statuses.length; i++){
      sb.append(',');
      sb.append(statuses[i].toJson());
    }
    sb.append(']');
    return sb.toString();
  }

}
