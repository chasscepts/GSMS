package com.chass.gsms.helpers;

import com.chass.gsms.models.Class;
import com.chass.gsms.models.ClassSummary;
import com.chass.gsms.models.Student;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * This is a transit store for holding data that we want to pass between ViewModels.
 * The GOTCHA is that we may accumulate too many large objects that don't really need to be in memory any longer.
 * An alternative is to use an actual ViewModel that is shared between the Fragments (ie. scoped to the Activity) but the ViewModels are better served being lean and it does not solve our data accumulation problem.
 * Another alternative is to use the navigation component but this is simpler as we are already using Hilt that will inject this anywhere we require it. And even with this approach you still have to remember not to pass large objects.
 * For large object we just need to remember to set it to null once we have retrieved it.
 * A good approach will be to make all variables private and set them to null in their getter accessor, but there is no way to enforce it. We just have to remember;
 */
@ActivityRetainedScoped
public class SharedDataStore {
  @Inject
  public SharedDataStore(){}

  private ClassSummary selectedClassSummary;

  private Class currentClass;

  private Student currentStudent;

  public ClassSummary getSelectedClassSummary() {
    //String temp = selectedClassSummary;
    //selectedClassSummary = null;
    //return temp;
    return selectedClassSummary;
  }

  public void setSelectedClassSummary(ClassSummary selectedClassSummary) {
    this.selectedClassSummary = selectedClassSummary;
  }

  public Class getCurrentClass() {
    return currentClass;
  }

  public void setCurrentClass(Class currentClass) {
    this.currentClass = currentClass;
  }

  public Student getCurrentStudent() {
    return currentStudent;
  }

  public void setCurrentStudent(Student currentStudent) {
    this.currentStudent = currentStudent;
  }
}
