package com.chass.gsms.ui.classlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chass.gsms.models.ClassSummary;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

@ActivityRetainedScoped
public class ClassSelectedListener {
  private MutableLiveData<ClassSummary> selectedClassSummary = new MutableLiveData<ClassSummary>();

  public LiveData<ClassSummary> getSelectedClassSummary(){
    return selectedClassSummary;
  }

  public void onClassSelected(ClassSummary classSummary){
    selectedClassSummary.setValue(classSummary);
  }

  @Inject
  public ClassSelectedListener(){

  }
}
