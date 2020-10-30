package com.chass.gsms.ui.classdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chass.gsms.interfaces.IStudentSelectedListener;
import com.chass.gsms.models.Student;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class StudentSelectedListener {
  private MutableLiveData<Student> selectedStudent = new MutableLiveData<>();

  public LiveData<Student> getSelectedStudent(){
    return selectedStudent;
  }

  public void onStudentSelected(Student student) {
    selectedStudent.setValue(student);
  }

  public void clearSelection() {
    selectedStudent.setValue(null);
  }

  @Inject
  public StudentSelectedListener(){

  }
}
