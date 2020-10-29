package com.chass.gsms.ui.classdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.models.Student;

public class StudentSelectedListener implements IStudentSelectedListener {
  private MutableLiveData<Student> selectedStudent = new MutableLiveData<>();

  public LiveData<Student> getSelectedStudent(){
    return selectedStudent;
  }

  @Override
  public void onStudentSelected(Student student) {
    selectedStudent.setValue(student);
  }

  @Override
  public void clearSelection() {
    selectedStudent.setValue(null);
  }
}
