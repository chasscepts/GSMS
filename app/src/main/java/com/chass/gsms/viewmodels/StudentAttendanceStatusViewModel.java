package com.chass.gsms.viewmodels;

import androidx.databinding.ObservableBoolean;

import com.chass.gsms.models.Student;
import com.chass.gsms.models.StudentAttendanceStatus;

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
}
