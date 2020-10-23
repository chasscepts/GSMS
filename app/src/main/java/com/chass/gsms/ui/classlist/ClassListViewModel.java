package com.chass.gsms.ui.classlist;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.chass.gsms.viewmodels.ViewStateViewModel;

public class ClassListViewModel extends ViewModel {
  private final SavedStateHandle savedStateHandle;
  private final ClassListAdapter adapter;

  public ClassListAdapter getAdapter(){
    return adapter;
  }

  @ViewModelInject
  public ClassListViewModel(@Assisted SavedStateHandle savedStateHandle, ClassListAdapter adapter){
    this.savedStateHandle = savedStateHandle;
    this.adapter = adapter;
  }

  /**
   * Save the state of this ViewModel
   */
  @Override
  protected void onCleared() {
    super.onCleared();
  }
}
