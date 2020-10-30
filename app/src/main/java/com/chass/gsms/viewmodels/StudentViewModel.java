package com.chass.gsms.viewmodels;

import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.models.Student;
import com.chass.gsms.ui.classdetails.StudentSelectedListener;

public class StudentViewModel {
  private final Student student;
  private final StudentSelectedListener listener;

  public Student getStudent() {
    return student;
  }

  public StudentViewModel(Student student, StudentSelectedListener listener) {
    this.student = student;
    this.listener = listener;
  }

  public void select(){
    listener.onStudentSelected(student);
  }
}
