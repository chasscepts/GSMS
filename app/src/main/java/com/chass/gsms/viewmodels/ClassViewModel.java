package com.chass.gsms.viewmodels;

import android.content.Context;

import com.chass.gsms.IMain;

/**
 * This class binds to
 */
public class ClassViewModel {
  private final String className;

  public String getClassName() {
    return className;
  }

  public ClassViewModel(String className) {
    this.className = className;
  }

  public void select(Context context){
    IMain main = (IMain)context;
    if(main != null){
      main.selectClass(className);
    }
  }
}
