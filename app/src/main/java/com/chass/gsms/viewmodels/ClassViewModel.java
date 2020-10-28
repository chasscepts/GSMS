package com.chass.gsms.viewmodels;

import android.content.Context;

import com.chass.gsms.interfaces.IMain;
import com.chass.gsms.models.ClassSummary;
import com.chass.gsms.ui.classlist.ClassSelectedListener;

/**
 * This class binds to
 */
public class ClassViewModel {
  private final ClassSummary classSummary;
  private final ClassSelectedListener listener;

  public ClassSummary getClassSummary() {
    return classSummary;
  }

  public ClassViewModel(ClassSummary classSummary, ClassSelectedListener listener) {
    this.classSummary = classSummary;
    this.listener = listener;
  }

  public void select(){
    listener.onClassSelected(classSummary);
  }
}
