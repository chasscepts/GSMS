package com.chass.gsms.interfaces;

import androidx.lifecycle.LiveData;

import com.chass.gsms.models.Student;

public interface IStudentSelectedListener {
  void onStudentSelected(Student student);
  void clearSelection();
  LiveData<Student> getSelectedStudent();
}
