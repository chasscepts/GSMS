package com.chass.gsms.viewmodels;

import android.content.Context;

import com.chass.gsms.IMain;
import com.chass.gsms.models.Student;

public class StudentViewModel {
  private final Student student;

  public Student getStudent() {
    return student;
  }

  public StudentViewModel(Student student) {
    this.student = student;
  }

  public void select(Context context){
    IMain main = (IMain)context;
    if(main != null){
      main.selectStudent(student);
    }
  }
}
