package com.chass.gsms.ui.classdetails;

import androidx.lifecycle.ViewModel;

public class ClassDetailsViewModel extends ViewModel {
  private String className;

  public String getClassName() {
    return className;
  }

  /**
   * When a class_item_view is clicked, we set the name of the class so it will be loaded and available when we
   * navigate to ClassDetailsFragment
   * @param className the name of the class we are navigating to
   */
  public void setClassName(String className) {
    this.className = className;
  }


}
