package com.chass.gsms.helpers;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chass.gsms.models.Class;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityRetainedScoped;

/**
 * Manages the size of classes that we want to keep in memory at a particular time.
 * We use a cyclic array that removes the oldest class cached when the maximum size has been reached.
 * Todo: A better but more complex design will be to use Room to store local cache of classes
 */
@ActivityRetainedScoped
public class ClassesCache {
  private int MAX_NUMBER_OF_CLASSES = 10; //Todo: We should be reading this value from settings.
  private int pointer = 0;
  private Class[] store = new Class[MAX_NUMBER_OF_CLASSES];

  @Inject
  public ClassesCache(){

  }

  public void save(Class aClass){
    if(pointer >= MAX_NUMBER_OF_CLASSES){
      pointer = 0;
    }
    store[pointer++] = aClass;
  }

  @Nullable
  public Class get(String name){
    for (Class aClass : store) {
      if (TextUtils.equals(name, aClass.getName())) return aClass;
    }
    return null;
  }

}
