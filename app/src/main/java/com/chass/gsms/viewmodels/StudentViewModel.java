package com.chass.gsms.viewmodels;

import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.models.Student;

public class StudentViewModel {
  private final Student student;
  private final IStudentSelectedListener listener;

  public Student getStudent() {
    return student;
  }

  public StudentViewModel(Student student, IStudentSelectedListener listener) {
    this.student = student;
    this.listener = listener;
  }

  public void select(){
    listener.onStudentSelected(student);
  }
}
